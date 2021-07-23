package br.com.southsystem.cooperative.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

@org.springframework.data.repository.NoRepositoryBean
public interface SpecRepository<T> extends CrudRepository<T, Long> {
    Optional<T> getByUuid(String uuid);
    Page<T> findAll(Pageable pageable);
}
