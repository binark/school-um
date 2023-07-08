package com.binark.school.usermanagement.entity;

import com.binark.school.usermanagement.entity.listener.AuditEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public abstract class AbsctractBaseEntity {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(unique = true, nullable = false, updatable = false)
    protected String slug;

    @CreatedDate
    protected LocalDateTime createAt;

    @LastModifiedDate
    protected LocalDateTime modifiedDate;

    protected Boolean enabled = Boolean.TRUE;

    protected Boolean deleted = Boolean.FALSE;
}
