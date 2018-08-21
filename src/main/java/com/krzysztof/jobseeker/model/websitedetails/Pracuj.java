package com.krzysztof.jobseeker.model.websitedetails;

import org.jsoup.nodes.Element;

public class Pracuj extends WebsiteDetails {

  private static final String JOB_POSTING_CSS_QUERY = ".o-list_item";
  private static final String SEARCH_URL = "https://www.pracuj.pl/praca/%1$s;kw/%2$s;wp";

  public Pracuj() {
    super(JOB_POSTING_CSS_QUERY, SEARCH_URL);
  }

  @Override
  public String getCrawlUrl(String position, String location) {
    return String.format(super.getSearchUrl(),position,location);
  }

  @Override
  public String getJobTitle(Element element) {
    return element.select(".o-list_item_link_name").text();
  }

  @Override
  public String getJobUrl(Element element) {
    return element.select(".o-list_item_link_name").attr("abs:href");
  }
}
