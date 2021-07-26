package br.com.southsystem.cooperative.service.impl.v1;

import br.com.southsystem.cooperative.exceptions.BusinessException;
import br.com.southsystem.cooperative.model.Person;
import br.com.southsystem.cooperative.model.User;
import br.com.southsystem.cooperative.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceImplIntegrationTest {

    @Autowired
    @Qualifier("userServiceV1")
    private UserService userService;

    @Test
    void createUserTest() {
        var user = new User();
        user.setUsername("test");
        user.setPassword("q1w2e3");

        var person = new Person();
        person.setName("test");
        person.setDoc("00000000000");
        user.setPerson(person);

        Assertions.assertDoesNotThrow(() -> userService.create(user));
        Assertions.assertThrows(BusinessException.class, () -> userService.create(user));

        user.setUsername("test4");
        Assertions.assertThrows(BusinessException.class, () -> userService.create(user));

        person.setDoc("00000000001");
        user.setUsername("test1");
        Assertions.assertDoesNotThrow(() -> userService.create(user));

        Assertions.assertTrue(user.getId() != null && user.getId() > 0);
        Assertions.assertTrue(user.getUuid() != null && user.getUuid().length() > 0);
        Assertions.assertTrue(person.getId() != null && person.getId() > 0);
        Assertions.assertEquals(person.getUser().getId(), user.getId());
    }

    @Test
    void updateUserTest() {
        var created = userService.create(getUser(2));

        Assertions.assertThrows(BusinessException.class, () -> userService.create(getUser(2)));
        Assertions.assertDoesNotThrow(() -> userService.update(created));
    }

    private User getUser(int n) {
        var user = new User();
        user.setUsername("test-" + n );
        user.setPassword("q1w2e3");

        var person = new Person();
        person.setName("test");
        person.setDoc("0000000000" + n);
        user.setPerson(person);
        return user;
    }
}
