package br.com.southsystem.usersapi.doc.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntityUUID extends BaseEntity {

    protected String uuid;

    @Override
    @PrePersist
    protected void prePersist() {
        createdAt = LocalDateTime.now();
        this.uuid = UUID.randomUUID().toString();
    }
}
