package com.krzysztof.jobseeker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobAd {
  String position;
  String url;
}
