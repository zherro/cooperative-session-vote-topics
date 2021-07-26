package br.com.southsystem.cooperative.facade;

import br.com.southsystem.cooperative.mock.userapi.doc.model.UserStatus;
import br.com.southsystem.cooperative.dto.vote.VoteDTO;
import br.com.southsystem.cooperative.exceptions.MessageService;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.model.SessionVote;
import br.com.southsystem.cooperative.model.User;
import br.com.southsystem.cooperative.model.types.Vote;
import br.com.southsystem.cooperative.service.RestApiUserService;
import br.com.southsystem.cooperative.service.impl.v1.SessionServiceImpl;
import br.com.southsystem.cooperative.service.impl.v1.SessionVoteServiceImpl;
import br.com.southsystem.cooperative.service.impl.v1.UserServiceImpl;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_BLOCKED_USER;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_INVALID_VOTE_TYPE;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_SESSION_CLOSED;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_SESSION_NOT_STARTED;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_USER_HAS_VOTED;

@Component
public class SessionVoteFacade {
    private static final Logger log = LoggerFactory.getLogger(SessionVoteFacade.class);

    private final SessionVoteServiceImpl sessionVoteService;
    private final SessionServiceImpl sessionService;
    private final UserServiceImpl userService;
    private final MessageSource messageSource;
    private final RestApiUserService restApiUserService;

    public SessionVoteFacade(SessionVoteServiceImpl sessionVoteService, SessionServiceImpl sessionService,
            UserServiceImpl userService, MessageSource messageSource,
            RestApiUserService restApiUserService) {
        this.sessionVoteService = sessionVoteService;
        this.sessionService = sessionService;
        this.userService = userService;
        this.messageSource = messageSource;
        this.restApiUserService = restApiUserService;
    }

    public void toVoteValidateByApi(final VoteDTO vote) {
        log.info("m=toVoteValidateByApi, validate user by users api, for user: {}, in session: {}", vote.getUser(), vote.getSession());
        var user = userService.getByUuid(vote.getUser());
        var type = user.getPerson().getDoc().replaceAll("[^0-9]*", "").length() > 11 ? "cnpj" : "cpf";

        try {
            var validation = restApiUserService.userAbleToVote(type, user.getPerson().getDoc());

            if (validation.getStatus().equals(UserStatus.UNABLE_TO_VOTE)) {
                MessageService.createBusinessException(messageSource, MSG_EXCEPTION_BLOCKED_USER);
            }
        } catch (HttpClientErrorException ex) {
            if(ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                MessageService.createBusinessException(messageSource, MSG_EXCEPTION_BLOCKED_USER);
            }
            throw ex;
        }
        vote(vote, user);
    }

    public void toVote(final VoteDTO vote) {
        var user = userService.getByUuid(vote.getUser());
        vote(vote, user);
    }

    private void vote(VoteDTO vote, User user) {
        log.info("m=vote, registering user vote for user: {}, in session: {}", vote.getUser(), vote.getSession());
        if(!user.isActive()) {
            MessageService.createBusinessException(messageSource,MSG_EXCEPTION_BLOCKED_USER);
        }

        var session = sessionService.getByUuid(vote.getSession());
        checkIfSessionIsAbleToVote(session);
        checkIfUserHasVoted(vote);

        var userVote = new SessionVote();
        userVote.setUser(user);
        userVote.setSession(session);
        try {
            userVote.setVote( Vote.valueOf(vote.getVote()) );
        } catch (Exception ex) {
            MessageService.createBusinessException(messageSource,MSG_EXCEPTION_INVALID_VOTE_TYPE);
        }

        log.info("m=vote, persisting user vote for user: {}, in session: {}", vote.getUser(), vote.getSession());
        sessionVoteService.create(userVote);
    }

    private void checkIfUserHasVoted(VoteDTO vote) {
        log.info("m=checkIfUserHasVoted, check if user has voted for user: {}, in session: {}", vote.getUser(), vote.getSession());
        if(sessionVoteService.userHasVoted(vote.getSession(), vote.getUser())) {
            MessageService.createBusinessException(messageSource,MSG_EXCEPTION_USER_HAS_VOTED);
        }
    }

    protected void checkIfSessionIsAbleToVote(Session session) {
        log.info("m=checkIfSessionIsAbleToVote, check if session is valid and started, session: {}", session.getUuid());
        var now = LocalDateTime.now();
        if(!session.isActive()
                || session.getEndTime().isBefore(now)) {
            MessageService.createBusinessException(messageSource, MSG_EXCEPTION_SESSION_CLOSED);
        } else if(session.getStartTime().isAfter(now)) {
            MessageService.createBusinessException(messageSource, MSG_EXCEPTION_SESSION_NOT_STARTED);
        }
    }

    public SessionVote getByUuid(String uuid) {
        return sessionVoteService.getByUuid(uuid);
    }
}
