
package com.kabasakalis.springifyapi.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class BaseEntity implements Identifiable<Long> {


    public static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_date")
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    protected Calendar createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    protected Calendar updatedDate;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        Calendar now = Calendar.getInstance();
        if (createdDate == null) {
            createdDate = now;
        }
        updatedDate = now;
    }

    protected BaseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar created) {
        this.createdDate = created;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updated) {
        this.updatedDate = updated;
    }


    @JsonProperty("updated_date")
    public String getFormattedUpdatedDate() {
        return DATE_FORMAT.format(getUpdatedDate().getTime());
    }

    @JsonProperty("created_date")
    public String getFormattedCreatedDate() {
        return DATE_FORMAT.format(getCreatedDate().getTime());
    }
}
