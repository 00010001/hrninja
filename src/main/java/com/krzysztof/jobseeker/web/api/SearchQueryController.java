package com.krzysztof.jobseeker.web.api;

import com.codahale.metrics.annotation.Timed;
import com.krzysztof.jobseeker.domain.JobAd;
import com.krzysztof.jobseeker.service.business.SearchService;
import com.krzysztof.jobseeker.service.dto.SearchQueryDTO;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api")
@CrossOrigin
public class SearchQueryController {

    private final Logger log = LoggerFactory.getLogger(SearchQueryController.class);

    private SearchService searchService;

    @Autowired
    public SearchQueryController(
        SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/search")
    @Timed
    public ResponseEntity<List<JobAd>> createSearchQuery(@Valid @RequestBody SearchQueryDTO
        searchQueryDTO) {
        if(searchQueryDTO != null){
            log.debug("REST request to create search query : {}", searchQueryDTO);
            List<JobAd> jobAds = searchService.getJobAdsForSearchQuery(searchQueryDTO);
            return new ResponseEntity<>(jobAds, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }

    }

}
