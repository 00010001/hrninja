package com.krzysztof.jobseeker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.krzysztof.jobseeker.domain.SearchQuery;
import com.krzysztof.jobseeker.repository.SearchQueryRepository;
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
 * REST controller for managing SearchQuery.
 */
@RestController
@RequestMapping("/api")
public class SearchQueryResource {

    private final Logger log = LoggerFactory.getLogger(SearchQueryResource.class);

    private static final String ENTITY_NAME = "searchQuery";

    private final SearchQueryRepository searchQueryRepository;

    public SearchQueryResource(SearchQueryRepository searchQueryRepository) {
        this.searchQueryRepository = searchQueryRepository;
    }

    /**
     * POST  /search-queries : Create a new searchQuery.
     *
     * @param searchQuery the searchQuery to create
     * @return the ResponseEntity with status 201 (Created) and with body the new searchQuery, or with status 400 (Bad Request) if the searchQuery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/search-queries")
    @Timed
    public ResponseEntity<SearchQuery> createSearchQuery(@RequestBody SearchQuery searchQuery) throws URISyntaxException {
        log.debug("REST request to save SearchQuery : {}", searchQuery);
        if (searchQuery.getId() != null) {
            throw new BadRequestAlertException("A new searchQuery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SearchQuery result = searchQueryRepository.save(searchQuery);
        return ResponseEntity.created(new URI("/api/search-queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /search-queries : Updates an existing searchQuery.
     *
     * @param searchQuery the searchQuery to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated searchQuery,
     * or with status 400 (Bad Request) if the searchQuery is not valid,
     * or with status 500 (Internal Server Error) if the searchQuery couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/search-queries")
    @Timed
    public ResponseEntity<SearchQuery> updateSearchQuery(@RequestBody SearchQuery searchQuery) throws URISyntaxException {
        log.debug("REST request to update SearchQuery : {}", searchQuery);
        if (searchQuery.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SearchQuery result = searchQueryRepository.save(searchQuery);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, searchQuery.getId().toString()))
            .body(result);
    }

    /**
     * GET  /search-queries : get all the searchQueries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of searchQueries in body
     */
    @GetMapping("/search-queries")
    @Timed
    public List<SearchQuery> getAllSearchQueries() {
        log.debug("REST request to get all SearchQueries");
        return searchQueryRepository.findAll();
    }

    /**
     * GET  /search-queries/:id : get the "id" searchQuery.
     *
     * @param id the id of the searchQuery to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the searchQuery, or with status 404 (Not Found)
     */
    @GetMapping("/search-queries/{id}")
    @Timed
    public ResponseEntity<SearchQuery> getSearchQuery(@PathVariable Long id) {
        log.debug("REST request to get SearchQuery : {}", id);
        Optional<SearchQuery> searchQuery = searchQueryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(searchQuery);
    }

    /**
     * DELETE  /search-queries/:id : delete the "id" searchQuery.
     *
     * @param id the id of the searchQuery to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/search-queries/{id}")
    @Timed
    public ResponseEntity<Void> deleteSearchQuery(@PathVariable Long id) {
        log.debug("REST request to delete SearchQuery : {}", id);

        searchQueryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
