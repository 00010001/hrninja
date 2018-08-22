package com.krzysztof.jobseeker.repository;

import com.krzysztof.jobseeker.domain.SearchQuery;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SearchQuery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SearchQueryRepository extends JpaRepository<SearchQuery, Long> {
    Optional<SearchQuery> findOneByPositionAndLocationAndCompany(String position, String location,
        String company);
}
