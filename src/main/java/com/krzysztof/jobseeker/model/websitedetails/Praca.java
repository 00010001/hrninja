package com.krzysztof.jobseeker.model.websitedetails;

import org.jsoup.nodes.Element;

public class Praca extends WebsiteDetails {

  private static final String JOB_POSTING_CSS_QUERY = ".announcement-title";
  private static final String SEARCH_URL = "https://www.praca.pl/s-%1$s,%2$s.html?p=%1$s&m=%2$s";

  public Praca() {
    super(JOB_POSTING_CSS_QUERY,SEARCH_URL);
  }

  @Override
  public String getCrawlUrl(String position, String location) {
    return String.format(super.getSearchUrl(),position,location);
  }

  @Override
  public String getJobTitle(Element element) {
    return element.select(".title").html();
  }

  @Override
  public String getJobUrl(Element element) {
    return element.select("a.title").attr("abs:href");
  }
}
