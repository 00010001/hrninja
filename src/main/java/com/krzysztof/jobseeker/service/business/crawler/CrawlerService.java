package com.krzysztof.jobseeker.service.business.crawler;

import com.krzysztof.jobseeker.domain.JobAd;
import com.krzysztof.jobseeker.domain.SearchQuery;
import com.krzysztof.jobseeker.model.enumeration.WebsiteName;
import com.krzysztof.jobseeker.model.websitedetails.WebsiteDetails;
import com.krzysztof.jobseeker.repository.JobAdRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CrawlerService {

    private final Logger logger = LogManager.getLogger(CrawlerService.class);

    private WebsiteDetailsFactory websiteDetailsFactory;
    private JobAdRepository jobAdRepository;

    @Autowired
    public CrawlerService(WebsiteDetailsFactory websiteDetailsFactory,
        JobAdRepository jobAdRepository) {
        this.websiteDetailsFactory = websiteDetailsFactory;
        this.jobAdRepository = jobAdRepository;
    }

    @Async
    public void crawl(SearchQuery searchQuery, WebsiteName websiteName) {
        List<JobAd> jobAdList = parseJobs(searchQuery, websiteName);
        jobAdRepository.saveAll(jobAdList);
    }

    private List<JobAd> parseJobs(SearchQuery searchQuery, WebsiteName websiteName) {
        WebsiteDetails websiteDetails = websiteDetailsFactory.getWebsiteDetails(websiteName);
        String position = searchQuery.getPosition();
        String location = searchQuery.getLocation();
        String crawlUrl = websiteDetails.getCrawlUrl(position, location);
        Long searchQueryId = searchQuery.getId();
        Document document = getDocument(crawlUrl);

        List<JobAd> jobAds = getJobAdsFromDocument(document, websiteDetails, searchQueryId);
        if (jobAds.isEmpty()) {
            logger.warn("no results found on " + crawlUrl);
        }
        return jobAds;
    }

    private List<JobAd> getJobAdsFromDocument(Document document, WebsiteDetails websiteDetails,
        Long searchQueryId) {
        Elements jobPostings = getJobPostings(document, websiteDetails.getJobPostingCssQuery());
        List<JobAd> jobAdList = new ArrayList<>();
        for (Element jobPosting : jobPostings) {
            String jobTitle = websiteDetails.getJobTitle(jobPosting);
            String jobUrl = websiteDetails.getJobUrl(jobPosting);
            jobAdList.add(new JobAd(jobTitle, jobUrl, searchQueryId));
        }
        return jobAdList;
    }

    private Document getDocument(String url) {
        Document document = null;
        try {
            logger.info("parsing from: " + url);
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.error("error while parsing from: " + url);
        }
        return document;
    }

    private Elements getJobPostings(Document document, String jobPostingCssQuery) {
        return document.select(jobPostingCssQuery);
    }
}
