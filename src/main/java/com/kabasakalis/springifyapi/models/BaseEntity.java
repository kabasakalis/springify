
package com.kabasakalis.springifyapi.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.hateoas.Identifiable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Calendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;


  @MappedSuperclass
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
  public abstract class BaseEntity implements  Identifiable<Long>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_date")
      @CreatedDate
      @Temporal(TemporalType.TIMESTAMP)
      protected Calendar createdDate;

    @Column(name = "updated_date")
      @LastModifiedDate
      @Temporal(TemporalType.TIMESTAMP)
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
  }
