package br.com.southsystem.cooperative.service.impl.v1;

import br.com.southsystem.cooperative.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperative.model.SessionVote;
import br.com.southsystem.cooperative.repository.SessionVoteRepository;
import br.com.southsystem.cooperative.repository.SpecRepository;
import br.com.southsystem.cooperative.service.SessionVoteService;
import javax.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;


@Service
public class SessionVoteServiceImpl implements SessionVoteService {

    private final SessionVoteRepository sessionVoteRepository;
    private final MessageSource messageSource;

    public SessionVoteServiceImpl(SessionVoteRepository sessionVoteRepository, MessageSource messageSource) {
        this.sessionVoteRepository = sessionVoteRepository;
        this.messageSource = messageSource;
    }

    @Override
    public SpecRepository<SessionVote> getRepository() {
        return sessionVoteRepository;
    }

    @Override
    public RuntimeException notFoundException(String key) {
        return new ResourceNotFoundException(messageSource, SessionVote.class.getSimpleName() , key);
    }

    @Override
    @Transactional
    public SessionVote create(SessionVote data) {
        return SessionVoteService.super.create(data);
    }

    @Override
    public boolean userHasVoted(String session, String user) {
        return !sessionVoteRepository.userHasVoted(session, user).isEmpty();
    }
}
