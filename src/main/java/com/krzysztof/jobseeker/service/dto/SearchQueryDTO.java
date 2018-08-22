package com.krzysztof.jobseeker.service.dto;

import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class SearchQueryDTO {

    @Size(min = 1, max = 50)
    private String position;

    @Size(min = 1, max = 50)
    private String location;

    @Size(min = 1, max = 50)
    private String company;

}
