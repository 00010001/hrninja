package com.krzysztof.jobseeker.service.parser;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import com.krzysztof.jobseeker.model.JobAd;
import com.krzysztof.jobseeker.model.enumeration.WebsiteName;
import com.krzysztof.jobseeker.model.websitedetails.WebsiteDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobParser {

  private final Logger logger = LogManager.getLogger(JobParser.class);

  private WebsiteDetailsFactory websiteDetailsFactory;
  private JobDetailsParser jobDetailsParser;

  @Autowired
  public JobParser(WebsiteDetailsFactory websiteDetailsFactory,
      JobDetailsParser jobDetailsParser) {
    this.websiteDetailsFactory = websiteDetailsFactory;
    this.jobDetailsParser = jobDetailsParser;
  }

  public List<JobAd> parseJobs(WebsiteName websiteName, String position, String location){
    WebsiteDetails websiteDetails = websiteDetailsFactory.getWebsiteDetails(websiteName);
    String crawlUrl = websiteDetails.getCrawlUrl(position,location);
    Document document = getDocument(crawlUrl);
    List<JobAd> jobAds = getJobAdsFromDocument(document,websiteDetails);
    if(jobAds.isEmpty()){
      logger.warn("no results found on " + crawlUrl);
    }
    return jobAds;
  }

  private List<JobAd> getJobAdsFromDocument(Document document, WebsiteDetails websiteDetails) {
    Elements jobPostings = jobDetailsParser
        .getJobPostings(document,websiteDetails.getJobPostingCssQuery());
    List<JobAd> jobAdList = new ArrayList<>();
    for (Element jobPosting : jobPostings) {
      String jobTitle = websiteDetails.getJobTitle(jobPosting);
      String jobUrl = websiteDetails.getJobUrl(jobPosting);
      jobAdList.add(new JobAd(jobTitle,jobUrl));
    }
    return jobAdList;
  }

  private Document getDocument(String url){
    Document document = null;
    try {
      logger.info("parsing from: " + url);
      document = Jsoup.connect(url).get();
    } catch (IOException e) {
      logger.error("error while parsing from: " + url);
    }
    return document;
  }
}
