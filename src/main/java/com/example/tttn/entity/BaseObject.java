package com.example.tttn.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @CreatedBy
    private String createdBy;
    @Column
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Column
    @LastModifiedBy
    private String modifiedBy;

    public BaseObject() {
    }

    public BaseObject(BaseObject entity) {
        if (entity != null) {
            this.createDate = entity.createDate;
            this.createdBy = entity.createdBy;
            this.modifiedDate = entity.modifiedDate;
            this.modifiedBy = entity.modifiedBy;
        }
    }

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
