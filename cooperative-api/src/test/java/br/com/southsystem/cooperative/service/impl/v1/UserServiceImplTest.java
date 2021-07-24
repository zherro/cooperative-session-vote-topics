package br.com.southsystem.cooperative.service.impl.v1;

import br.com.southsystem.cooperative.exceptions.BusinessException;
import br.com.southsystem.cooperative.model.Person;
import br.com.southsystem.cooperative.model.User;
import br.com.southsystem.cooperative.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

class UserServiceImplTest {

    private MessageSource messageSource;
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    @BeforeEach
    void beforeEach() {
        var resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:messages");
        resource.setDefaultEncoding("UTF-8");
        this.messageSource = resource;
    }

    @Test
    void notShouldReturnExceptionIfBasicUserDataIsValidTest() {
        var user = new User();
        user.setUsername("test");
        user.setPassword("q1w2e3");

        var person = new Person();
        person.setName("test");
        person.setDoc("00000000000");
        user.setPerson(person);

        UserServiceImpl service = new UserServiceImpl(userRepository, messageSource);

        Assertions.assertDoesNotThrow(() -> service.validateUser(user));
    }

    @Test
    void shouldReturnExceptionIfUserDataIsInvalid() {
        var user = new User();
        user.setUsername("te");
        user.setPassword("q1w2e3");

        var person = new Person();
        person.setName("test");
        person.setDoc("00000000000");
        user.setPerson(person);

        UserServiceImpl service = new UserServiceImpl(userRepository, messageSource);

        Assertions.assertThrows(BusinessException.class, () -> service.validateUser(user));

        user.setUsername("test");
        user.setPassword("");
        Assertions.assertThrows(BusinessException.class, () -> service.validateUser(user));

        user.setPassword("aqswdefr");
        person.setName(null);
        Assertions.assertThrows(BusinessException.class, () -> service.validateUser(user));

        user.setPerson(null);
        Assertions.assertThrows(BusinessException.class, () -> service.validateUser(user));

        user.setPerson(person);
        person.setName("test");
        person.setDoc("098611526");
        Assertions.assertThrows(BusinessException.class, () -> service.validateUser(user));

        person.setDoc("01234567895");
        Assertions.assertDoesNotThrow(() -> service.validateUser(user));
    }

}