package br.com.southsystem.cooperative.controller.doc;

import br.com.southsystem.cooperative.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryData extends CrudRepository<User, Long> {
    Optional<User> findFirstUserByPersonDoc(String doc);
}
