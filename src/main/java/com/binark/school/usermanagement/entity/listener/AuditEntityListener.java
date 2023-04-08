package com.binark.school.usermanagement.entity.listener;

import com.binark.school.usermanagement.entity.AbsctractBaseEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuditEntityListener {

    @PrePersist
    private void beforeSave(AbsctractBaseEntity entity) {
        entity.setSlug(UUID.randomUUID().toString());
        entity.setCreateAt(LocalDateTime.now());
        entity.setDeleted(false);
    }

    @PreUpdate
    private void beforeUpdate(AbsctractBaseEntity entity) {
        entity.setModifiedDate(LocalDateTime.now());
    }
}
