package com.krzysztof.jobseeker.web.rest;

import com.krzysztof.jobseeker.HrninjaApp;

import com.krzysztof.jobseeker.domain.JobAd;
import com.krzysztof.jobseeker.repository.JobAdRepository;
import com.krzysztof.jobseeker.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static com.krzysztof.jobseeker.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JobAdResource REST controller.
 *
 * @see JobAdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HrninjaApp.class)
public class JobAdResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_SEARCH_QUERY_ID = 1L;
    private static final Long UPDATED_SEARCH_QUERY_ID = 2L;

    @Autowired
    private JobAdRepository jobAdRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobAdMockMvc;

    private JobAd jobAd;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobAdResource jobAdResource = new JobAdResource(jobAdRepository);
        this.restJobAdMockMvc = MockMvcBuilders.standaloneSetup(jobAdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobAd createEntity(EntityManager em) {
        JobAd jobAd = new JobAd()
            .title(DEFAULT_TITLE)
            .url(DEFAULT_URL)
            .searchQueryId(DEFAULT_SEARCH_QUERY_ID);
        return jobAd;
    }

    @Before
    public void initTest() {
        jobAd = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobAd() throws Exception {
        int databaseSizeBeforeCreate = jobAdRepository.findAll().size();

        // Create the JobAd
        restJobAdMockMvc.perform(post("/api/job-ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobAd)))
            .andExpect(status().isCreated());

        // Validate the JobAd in the database
        List<JobAd> jobAdList = jobAdRepository.findAll();
        assertThat(jobAdList).hasSize(databaseSizeBeforeCreate + 1);
        JobAd testJobAd = jobAdList.get(jobAdList.size() - 1);
        assertThat(testJobAd.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testJobAd.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testJobAd.getSearchQueryId()).isEqualTo(DEFAULT_SEARCH_QUERY_ID);
    }

    @Test
    @Transactional
    public void createJobAdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobAdRepository.findAll().size();

        // Create the JobAd with an existing ID
        jobAd.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobAdMockMvc.perform(post("/api/job-ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobAd)))
            .andExpect(status().isBadRequest());

        // Validate the JobAd in the database
        List<JobAd> jobAdList = jobAdRepository.findAll();
        assertThat(jobAdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobAds() throws Exception {
        // Initialize the database
        jobAdRepository.saveAndFlush(jobAd);

        // Get all the jobAdList
        restJobAdMockMvc.perform(get("/api/job-ads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobAd.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].searchQueryId").value(hasItem(DEFAULT_SEARCH_QUERY_ID.intValue())));
    }
    

    @Test
    @Transactional
    public void getJobAd() throws Exception {
        // Initialize the database
        jobAdRepository.saveAndFlush(jobAd);

        // Get the jobAd
        restJobAdMockMvc.perform(get("/api/job-ads/{id}", jobAd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobAd.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.searchQueryId").value(DEFAULT_SEARCH_QUERY_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingJobAd() throws Exception {
        // Get the jobAd
        restJobAdMockMvc.perform(get("/api/job-ads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobAd() throws Exception {
        // Initialize the database
        jobAdRepository.saveAndFlush(jobAd);

        int databaseSizeBeforeUpdate = jobAdRepository.findAll().size();

        // Update the jobAd
        JobAd updatedJobAd = jobAdRepository.findById(jobAd.getId()).get();
        // Disconnect from session so that the updates on updatedJobAd are not directly saved in db
        em.detach(updatedJobAd);
        updatedJobAd
            .title(UPDATED_TITLE)
            .url(UPDATED_URL)
            .searchQueryId(UPDATED_SEARCH_QUERY_ID);

        restJobAdMockMvc.perform(put("/api/job-ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJobAd)))
            .andExpect(status().isOk());

        // Validate the JobAd in the database
        List<JobAd> jobAdList = jobAdRepository.findAll();
        assertThat(jobAdList).hasSize(databaseSizeBeforeUpdate);
        JobAd testJobAd = jobAdList.get(jobAdList.size() - 1);
        assertThat(testJobAd.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testJobAd.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testJobAd.getSearchQueryId()).isEqualTo(UPDATED_SEARCH_QUERY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingJobAd() throws Exception {
        int databaseSizeBeforeUpdate = jobAdRepository.findAll().size();

        // Create the JobAd

        // If the entity doesn't have an ID, it will throw BadRequestAlertException 
        restJobAdMockMvc.perform(put("/api/job-ads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobAd)))
            .andExpect(status().isBadRequest());

        // Validate the JobAd in the database
        List<JobAd> jobAdList = jobAdRepository.findAll();
        assertThat(jobAdList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJobAd() throws Exception {
        // Initialize the database
        jobAdRepository.saveAndFlush(jobAd);

        int databaseSizeBeforeDelete = jobAdRepository.findAll().size();

        // Get the jobAd
        restJobAdMockMvc.perform(delete("/api/job-ads/{id}", jobAd.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<JobAd> jobAdList = jobAdRepository.findAll();
        assertThat(jobAdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobAd.class);
        JobAd jobAd1 = new JobAd();
        jobAd1.setId(1L);
        JobAd jobAd2 = new JobAd();
        jobAd2.setId(jobAd1.getId());
        assertThat(jobAd1).isEqualTo(jobAd2);
        jobAd2.setId(2L);
        assertThat(jobAd1).isNotEqualTo(jobAd2);
        jobAd1.setId(null);
        assertThat(jobAd1).isNotEqualTo(jobAd2);
    }
}
