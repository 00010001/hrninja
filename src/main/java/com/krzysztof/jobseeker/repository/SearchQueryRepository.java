package com.krzysztof.jobseeker.repository;

import com.krzysztof.jobseeker.model.SearchQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchQueryRepository extends JpaRepository<SearchQuery, Long> {

}
