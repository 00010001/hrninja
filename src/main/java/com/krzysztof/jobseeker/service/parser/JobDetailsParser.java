package com.krzysztof.jobseeker.service.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class JobDetailsParser {

  public String getJobTitle(Element jobPosting, String cssQuery) {
    return jobPosting.select(cssQuery).text();
  }

  public String getJobUrl(Element jobPosting, String cssQuery) {
    return jobPosting.select(cssQuery).attr("abs:href");
  }

  public Elements getJobPostings(Document document, String jobPostingCssQuery){
    return document.select(jobPostingCssQuery);
  }

}
