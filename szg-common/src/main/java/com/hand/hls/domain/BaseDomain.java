package com.hand.hls.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zy
 * @version 1.0
 * @date 2020/4/15 11:01 AM
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseDomain implements Serializable {

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "CREATED_DATE")
    protected Date createdDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    protected Date lastModifiedDate = new Date();
}
