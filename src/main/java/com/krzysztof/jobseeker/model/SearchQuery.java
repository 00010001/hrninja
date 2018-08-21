package com.krzysztof.jobseeker.model;

import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "search_query")
public class SearchQuery {

    public SearchQuery(@Size(min = 3, max = 50) String position,
        @Size(min = 3, max = 50) String location,
        @Size(min = 3, max = 50) String company) {
        this.position = position;
        this.location = location;
        this.company = company;
        this.queryDate = Instant.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "reset_date")
    private Instant queryDate;

    @Size(min = 3, max = 50)
    @Column(length = 50)
    private String position;

    @Size(min = 3, max = 50)
    @Column(length = 50)
    private String location;

    @Size(min = 3, max = 50)
    @Column(length = 50)
    private String company;

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SearchQuery searchQuery = (SearchQuery) o;

        boolean identicalPosition = Objects.equals(getPosition(), searchQuery.getPosition());
        boolean identicalLocation = Objects.equals(getLocation(), searchQuery.getLocation());
        boolean identicalCompany = Objects.equals(getCompany(), searchQuery.getCompany());

        return !(searchQuery.getId() == null || getId() == null) && identicalPosition &&
            identicalLocation && identicalCompany;
    }

    @Override
    public String toString() {
        return "SearchQuery{" +
            "id=" + id +
            ", queryDate=" + queryDate +
            ", position='" + position + '\'' +
            ", location='" + location + '\'' +
            ", company='" + company + '\'' +
            '}';
    }
}
