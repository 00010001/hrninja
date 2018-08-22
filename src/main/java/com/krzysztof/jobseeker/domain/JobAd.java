package com.krzysztof.jobseeker.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.NoArgsConstructor;

/**
 * A JobAd.
 */
@Entity
@Table(name = "job_ad")
@NoArgsConstructor
public class JobAd implements Serializable {

    private static final long serialVersionUID = 1L;

    public JobAd(String title, String url, Long searchQueryId) {
        this.title = title;
        this.url = url;
        this.searchQueryId = searchQueryId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @JsonIgnore
    @Column(name = "search_query_id")
    private Long searchQueryId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public JobAd title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public JobAd url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSearchQueryId() {
        return searchQueryId;
    }

    public JobAd searchQueryId(Long searchQueryId) {
        this.searchQueryId = searchQueryId;
        return this;
    }

    public void setSearchQueryId(Long searchQueryId) {
        this.searchQueryId = searchQueryId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobAd jobAd = (JobAd) o;
        if (jobAd.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobAd.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobAd{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", url='" + getUrl() + "'" +
            ", searchQueryId=" + getSearchQueryId() +
            "}";
    }
}
