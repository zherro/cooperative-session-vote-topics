package br.com.southsystem.cooperative.repository;

import br.com.southsystem.cooperative.model.User;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends SpecRepository<User> {

    Optional<User> getByUsername(String username);
    Optional<User> getByPersonDoc(String doc);
}
