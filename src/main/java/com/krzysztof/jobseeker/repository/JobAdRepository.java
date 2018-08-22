package com.krzysztof.jobseeker.repository;

import com.krzysztof.jobseeker.domain.JobAd;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the JobAd entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobAdRepository extends JpaRepository<JobAd, Long> {

    List<JobAd> findAllBySearchQueryId(Long searchQueryId);
}
