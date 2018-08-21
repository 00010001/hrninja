package com.krzysztof.jobseeker.model.websitedetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebsiteDetails implements Crawlable{

  protected String jobPostingCssQuery;
  protected String searchUrl;

  @Override
  public String getCrawlUrl(String position, String location) {
    return null;
  }

  @Override
  public String getJobTitle(Element element) {
    return null;
  }

  @Override
  public String getJobUrl(Element element) {
    return null;
  }
}
