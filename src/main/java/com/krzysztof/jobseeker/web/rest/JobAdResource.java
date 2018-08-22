package com.krzysztof.jobseeker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.krzysztof.jobseeker.domain.JobAd;
import com.krzysztof.jobseeker.repository.JobAdRepository;
import com.krzysztof.jobseeker.web.rest.errors.BadRequestAlertException;
import com.krzysztof.jobseeker.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JobAd.
 */
@RestController
@RequestMapping("/api")
public class JobAdResource {

    private final Logger log = LoggerFactory.getLogger(JobAdResource.class);

    private static final String ENTITY_NAME = "jobAd";

    private final JobAdRepository jobAdRepository;

    public JobAdResource(JobAdRepository jobAdRepository) {
        this.jobAdRepository = jobAdRepository;
    }

    /**
     * POST  /job-ads : Create a new jobAd.
     *
     * @param jobAd the jobAd to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobAd, or with status 400 (Bad Request) if the jobAd has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-ads")
    @Timed
    public ResponseEntity<JobAd> createJobAd(@RequestBody JobAd jobAd) throws URISyntaxException {
        log.debug("REST request to save JobAd : {}", jobAd);
        if (jobAd.getId() != null) {
            throw new BadRequestAlertException("A new jobAd cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobAd result = jobAdRepository.save(jobAd);
        return ResponseEntity.created(new URI("/api/job-ads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-ads : Updates an existing jobAd.
     *
     * @param jobAd the jobAd to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobAd,
     * or with status 400 (Bad Request) if the jobAd is not valid,
     * or with status 500 (Internal Server Error) if the jobAd couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-ads")
    @Timed
    public ResponseEntity<JobAd> updateJobAd(@RequestBody JobAd jobAd) throws URISyntaxException {
        log.debug("REST request to update JobAd : {}", jobAd);
        if (jobAd.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JobAd result = jobAdRepository.save(jobAd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobAd.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-ads : get all the jobAds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobAds in body
     */
    @GetMapping("/job-ads")
    @Timed
    public List<JobAd> getAllJobAds() {
        log.debug("REST request to get all JobAds");
        return jobAdRepository.findAll();
    }

    /**
     * GET  /job-ads/:id : get the "id" jobAd.
     *
     * @param id the id of the jobAd to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobAd, or with status 404 (Not Found)
     */
    @GetMapping("/job-ads/{id}")
    @Timed
    public ResponseEntity<JobAd> getJobAd(@PathVariable Long id) {
        log.debug("REST request to get JobAd : {}", id);
        Optional<JobAd> jobAd = jobAdRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(jobAd);
    }

    /**
     * DELETE  /job-ads/:id : delete the "id" jobAd.
     *
     * @param id the id of the jobAd to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-ads/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobAd(@PathVariable Long id) {
        log.debug("REST request to delete JobAd : {}", id);

        jobAdRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
