package com.idg.idgcore.domain.entity;

import com.idg.idgcore.domain.AbstractDomainObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity  extends AbstractDomainObject {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @CreatedDate
    @Column(name = "creation_time", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @LastModifiedBy
    @Column(name = "last_updated_by", insertable = false)
    private String lastUpdatedBy;

    @LastModifiedDate
    @Column(name = "last_updated_time", insertable = false )
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedTime;
}
