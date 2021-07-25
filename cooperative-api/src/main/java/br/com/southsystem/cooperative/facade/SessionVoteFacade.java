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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_BLOCKED_USER;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_INVALID_VOTE_TYPE;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_SESSION_CLOSED;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_SESSION_NOT_STARTED;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_USER_HAS_VOTED;

@Component
public class SessionVoteFacade {

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
        var user = userService.getByUuid(vote.getUser());
        var type = user.getPerson().getDoc().length() > 11 ? "cnpj" : "cpf";
        var validation = restApiUserService.userAbleToVote(type, user.getPerson().getDoc());

        if(validation.getStatus().equals(UserStatus.UNABLE_TO_VOTE)) {
            MessageService.createBusinessException(messageSource, MSG_EXCEPTION_BLOCKED_USER);
        }
        vote(vote, user);
    }

    public void toVote(final VoteDTO vote) {
        var user = userService.getByUuid(vote.getUser());
        vote(vote, user);
    }

    private void vote(VoteDTO vote, User user) {
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

        sessionVoteService.create(userVote);
    }

    private void checkIfUserHasVoted(VoteDTO vote) {
        if(sessionVoteService.userHasVoted(vote.getSession(), vote.getUser())) {
            MessageService.createBusinessException(messageSource,MSG_EXCEPTION_USER_HAS_VOTED);
        }
    }

    protected void checkIfSessionIsAbleToVote(Session session) {
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
