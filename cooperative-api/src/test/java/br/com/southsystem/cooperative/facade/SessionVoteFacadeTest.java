package br.com.southsystem.cooperative.facade;

import br.com.southsystem.cooperative.exceptions.BusinessException;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import br.com.southsystem.cooperative.service.impl.v1.SessionVoteServiceImpl;
import br.com.southsystem.cooperative.service.impl.v1.UserServiceImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

class SessionVoteFacadeTest {

    private MessageSource messageSource;
    private final SessionVoteServiceImpl sessionVoteService = Mockito.mock(SessionVoteServiceImpl.class);
    private final SessionServiceImpl sessionService = Mockito.mock(SessionServiceImpl.class);
    private final UserServiceImpl userService = Mockito.mock(UserServiceImpl.class);
    @BeforeEach
    void beforeEach() {
        var resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:messages");
        resource.setDefaultEncoding("UTF-8");
        this.messageSource = resource;
    }

    @Test
    void ifSessionHasClosedThenShouldReturnBusinessException() {
        var facade = new SessionVoteFacade(sessionVoteService,sessionService, userService, messageSource);
        var session = new Session();
        session.setActive(false);

        Assertions.assertThrows(BusinessException.class, () -> facade.checkIfSessionIsAbleToVote(session));

        session.setActive(true);
        session.setEndTime(LocalDateTime.now().minusHours(1));
        Assertions.assertThrows(BusinessException.class, () -> facade.checkIfSessionIsAbleToVote(session));

    }


    @Test
    void ifSessionHasNotStartedThenShouldReturnBusinessException() {
        var facade = new SessionVoteFacade(sessionVoteService,sessionService, userService, messageSource);
        var session = new Session();
        session.setStartTime(LocalDateTime.now().plusHours(1));
        session.setEndTime(LocalDateTime.now().plusHours(2));
        Assertions.assertThrows(BusinessException.class, () -> facade.checkIfSessionIsAbleToVote(session));
    }

    @Test
    void ifSessionIsAbleToVoteThenDontThrowException() {
        var facade = new SessionVoteFacade(sessionVoteService,sessionService, userService, messageSource);
        var session = new Session();
        session.setActive(true);
        session.setStartTime(LocalDateTime.now().minusHours(1));
        session.setEndTime(LocalDateTime.now().plusHours(2));
        Assertions.assertDoesNotThrow(() -> facade.checkIfSessionIsAbleToVote(session));
    }

}