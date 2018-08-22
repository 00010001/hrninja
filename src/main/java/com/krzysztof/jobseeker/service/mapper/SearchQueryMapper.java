package com.krzysztof.jobseeker.service.mapper;

import com.krzysztof.jobseeker.domain.SearchQuery;
import com.krzysztof.jobseeker.service.dto.SearchQueryDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SearchQueryMapper {

    SearchQueryMapper MAPPER = Mappers.getMapper(SearchQueryMapper.class);

    SearchQuery toSearchQuery(SearchQueryDTO searchQueryDTO);

    @InheritInverseConfiguration
    SearchQueryDTO fromSearchQuery(SearchQuery searchQuery);
}
