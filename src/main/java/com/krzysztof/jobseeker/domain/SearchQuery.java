package com.krzysztof.jobseeker.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A SearchQuery.
 */
@Entity
@Table(name = "search_query")
public class SearchQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "position")
    private String position;

    @Column(name = "location")
    private String location;

    @Column(name = "company")
    private String company;

    @Column(name = "query_date")
    private LocalDate queryDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public SearchQuery position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLocation() {
        return location;
    }

    public SearchQuery location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public SearchQuery company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getQueryDate() {
        return queryDate;
    }

    public SearchQuery queryDate(LocalDate queryDate) {
        this.queryDate = queryDate;
        return this;
    }

    public void setQueryDate(LocalDate queryDate) {
        this.queryDate = queryDate;
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
        SearchQuery searchQuery = (SearchQuery) o;
        if (searchQuery.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), searchQuery.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SearchQuery{" +
            "id=" + getId() +
            ", position='" + getPosition() + "'" +
            ", location='" + getLocation() + "'" +
            ", company='" + getCompany() + "'" +
            ", queryDate='" + getQueryDate() + "'" +
            "}";
    }
}
