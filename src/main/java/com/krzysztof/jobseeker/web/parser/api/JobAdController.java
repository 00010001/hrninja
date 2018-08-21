package com.krzysztof.jobseeker.web.parser.api;

import com.krzysztof.jobseeker.model.JobAd;
import com.krzysztof.jobseeker.model.enumeration.WebsiteName;
import com.krzysztof.jobseeker.service.parser.JobParser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
public class JobAdController {


    private JobParser jobParser;

    @Autowired
    public JobAdController(JobParser jobParser) {
        this.jobParser = jobParser;
    }

    @GetMapping(
        value = "/jobAd",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    List<JobAd> getJobs(@RequestParam String position, @RequestParam String location) {

        List<JobAd> jobAds = new ArrayList<>();
        jobAds.addAll(jobParser.parseJobs(WebsiteName.PRACUJ, position, location));
        jobAds.addAll(jobParser.parseJobs(WebsiteName.PRACA, position, location));
        return jobAds;
    }
}
