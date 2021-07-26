package br.com.southsystem.usersapi.doc;

import br.com.southsystem.usersapi.doc.model.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryData extends CrudRepository<User, Long> {
    Optional<User> findFirstUserByPersonDoc(String doc);
}
