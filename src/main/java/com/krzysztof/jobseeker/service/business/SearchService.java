package com.krzysztof.jobseeker.service.business;

import com.krzysztof.jobseeker.domain.JobAd;
import com.krzysztof.jobseeker.domain.SearchQuery;
import com.krzysztof.jobseeker.model.enumeration.WebsiteName;
import com.krzysztof.jobseeker.repository.JobAdRepository;
import com.krzysztof.jobseeker.repository.SearchQueryRepository;
import com.krzysztof.jobseeker.service.business.crawler.CrawlerService;
import com.krzysztof.jobseeker.service.dto.SearchQueryDTO;
import com.krzysztof.jobseeker.service.mapper.SearchQueryMapper;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final Logger log = LoggerFactory.getLogger(SearchService.class);

    private static final int ATTEMPT_INTERVAL = 500;
    private static final int MAX_ATTEMPTS = 3;

    private final SearchQueryMapper searchQueryMapper = Mappers.getMapper(SearchQueryMapper.class);

    private JobAdRepository jobAdRepository;
    private SearchQueryRepository searchQueryRepository;
    private CrawlerService crawlerService;

    @Autowired
    public SearchService(JobAdRepository jobAdRepository,
        SearchQueryRepository searchQueryRepository,
        CrawlerService crawlerService) {
        this.jobAdRepository = jobAdRepository;
        this.searchQueryRepository = searchQueryRepository;
        this.crawlerService = crawlerService;
    }

    private void crawlAllWebsites(SearchQuery searchQuery) {
        crawlerService.crawl(searchQuery, WebsiteName.PRACA);
        crawlerService.crawl(searchQuery, WebsiteName.PRACUJ);
    }

    public List<JobAd> getJobAdsForSearchQuery(SearchQueryDTO searchQueryDTO) {

        SearchQuery searchQuery = searchQueryMapper.toSearchQuery(searchQueryDTO);

        Optional<SearchQuery> optionalSearchQuery = searchQueryRepository
            .findOneByPositionAndLocationAndCompany(searchQuery.getPosition(), searchQuery
                .getLocation(), searchQuery.getCompany());

        if (optionalSearchQuery.isPresent()) {
            return jobAdRepository.findAllBySearchQueryId(optionalSearchQuery
                .get().getId());
        } else {
            searchQuery.setQueryDate(LocalDate.now());
            searchQueryRepository.save(searchQuery);
            crawlAllWebsites(searchQuery);
            return tryToGetJobAds(searchQuery, 1);
        }
    }

    private List<JobAd> tryToGetJobAds(SearchQuery searchQuery, int attempt) {
        if (attempt > MAX_ATTEMPTS) {
            return jobAdRepository.findAllBySearchQueryId(searchQuery.getId());
        } else {

            String msg = String.format("attempting to get jobs just parsed, %dattempt", attempt);
            log.info(msg);
            List<JobAd> jobAdList = jobAdRepository.findAllBySearchQueryId(searchQuery.getId());

            if (jobAdList.isEmpty()) {
                try {
                    Thread.sleep(ATTEMPT_INTERVAL);
                    attempt++;
                    return tryToGetJobAds(searchQuery, attempt);
                } catch (InterruptedException e) {
                    log.error("interrupted!");
                    Thread.currentThread().interrupt();
                    return new ArrayList<>();
                }
            } else {
                return jobAdList;
            }
        }
    }


}
