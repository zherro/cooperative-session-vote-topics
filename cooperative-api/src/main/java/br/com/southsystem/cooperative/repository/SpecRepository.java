package br.com.southsystem.cooperative.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.data.repository.NoRepositoryBean
public interface SpecRepository<T> extends JpaRepository<T, Long>
{
    Optional<T> getByUuid(String uuid);
    Page<T> findAll(Pageable pageable);
}
