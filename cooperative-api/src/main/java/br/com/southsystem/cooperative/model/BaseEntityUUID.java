package br.com.southsystem.cooperative.model;

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

    @PrePersist
    private void prePersist() {
        this.uuid = UUID.randomUUID().toString();
    }
}
