package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.model.BaseEntity;
import br.com.southsystem.cooperative.repository.SpecRepository;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecService<T extends BaseEntity> {

    SpecRepository<T> getRepository();
    RuntimeException notFoundException(String key);

    default Page<T> list(Pageable pageable) {
        return getRepository().findAll(pageable);
    }

    default T create(T data) {
        return getRepository().save(data);
    }

    default T getByUuid(String uuid) {
        return getRepository().getByUuid(uuid)
                .orElseThrow(() -> notFoundException(uuid));
    }

    default T update(T data) {
        return getRepository().save(data);
    }

    @Transactional
    default void remove(String uuid) {
        var entity = getByUuid(uuid);
        entity.setActive(false);
        getRepository().save(entity);
    }
}
