package com.krzysztof.jobseeker.service.business.crawler;

import com.krzysztof.jobseeker.model.enumeration.WebsiteName;
import com.krzysztof.jobseeker.model.websitedetails.Praca;
import com.krzysztof.jobseeker.model.websitedetails.Pracuj;
import com.krzysztof.jobseeker.model.websitedetails.WebsiteDetails;
import org.springframework.stereotype.Service;

@Service
class WebsiteDetailsFactory {

  WebsiteDetails getWebsiteDetails(WebsiteName websiteName) {
    if (websiteName == null) {
      return null;
    }
    if (websiteName.equals(WebsiteName.PRACUJ)) {
      return new Pracuj();
    }
    if (websiteName.equals(WebsiteName.PRACA)) {
      return new Praca();
    }
    return null;
  }
}
