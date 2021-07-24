package br.com.southsystem.cooperative.facade;

import br.com.southsystem.cooperative.dto.vote.VoteDTO;
import br.com.southsystem.cooperative.exceptions.MessageService;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.model.SessionVote;
import br.com.southsystem.cooperative.model.types.Vote;
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

    public SessionVoteFacade(
            SessionVoteServiceImpl sessionVoteService,
            SessionServiceImpl sessionService,
            UserServiceImpl userService,
            MessageSource messageSource
    ) {
        this.sessionVoteService = sessionVoteService;
        this.sessionService = sessionService;
        this.userService = userService;
        this.messageSource = messageSource;
    }

    public void toVote(final VoteDTO vote) {

        var session = sessionService.getByUuid(vote.getSession());
        checkIfSessionIsAbleToVote(session);
        checkIfUserHasVoted(vote);

        var user = userService.getByUuid(vote.getUser());
        if(!user.isActive()) {
            MessageService.createBusinessException(messageSource,MSG_EXCEPTION_BLOCKED_USER);
        }

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
