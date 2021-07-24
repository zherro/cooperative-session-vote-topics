package br.com.southsystem.cooperative.service.impl.v1;

import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_HAS_SESSION_ACTIVE;
import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_SESSION_STARTED;

import br.com.southsystem.cooperative.dto.session.RequestSessionFilter;
import br.com.southsystem.cooperative.exceptions.AppMessages;
import br.com.southsystem.cooperative.exceptions.BusinessException;
import br.com.southsystem.cooperative.exceptions.MessageService;
import br.com.southsystem.cooperative.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.model.types.VoteSummary;
import br.com.southsystem.cooperative.repository.SessionRepository;
import br.com.southsystem.cooperative.repository.SpecRepository;
import br.com.southsystem.cooperative.service.SessionService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final MessageSource messageSource;

    public SessionServiceImpl(SessionRepository sessionRepository, MessageSource messageSource) {
        this.sessionRepository = sessionRepository;
        this.messageSource = messageSource;
    }

    @Override
    public SpecRepository<Session> getRepository() {
        return sessionRepository;
    }

    @Override
    public RuntimeException notFoundException(String key) {
        return new ResourceNotFoundException(messageSource, Session.class.getSimpleName() , key);
    }


    public Page<Session> list(Pageable pageable, RequestSessionFilter filter) {
        var getAll = filter.getActive() == null || filter.getActive().isEmpty();
        var activeFilter = Boolean.parseBoolean(filter.getActive());
        return sessionRepository.findAll(getAll, activeFilter, LocalDateTime.now(), pageable);
    }

    @Override
    @Transactional
    public Session create(Session data) {
        defineEndTime(data);
        if(!sessionRepository.hasActiveSessionForTopic(data.getTopic().getUuid(), LocalDateTime.now()).isEmpty()) {
            throwBusinessException(MSG_EXCEPTION_HAS_SESSION_ACTIVE);
        }
        return SessionService.super.create(data);
    }

    @Override
    public Session hasActiveSessionForTopic(String topic) {
        var sessions =  sessionRepository.hasActiveSessionForTopic(topic, LocalDateTime.now());
        if(sessions.isEmpty()) {
            return null;
        }
        return sessions.stream().findFirst().orElse(null);
    }

    private void defineEndTime(Session data) {
        if(data.getStartTime() == null) {
            data.setStartTime(LocalDateTime.now());
        }
        var endTime = data.getStartTime()
                .plusMinutes(data.getDurationMinutes());
        data.setEndTime(endTime);
    }

    @Override
    public Session update(Session data) {
        if(data.getStartTime().isBefore(LocalDateTime.now())) {
            throwBusinessException(MSG_EXCEPTION_SESSION_STARTED);
        }
        defineEndTime(data);
        return SessionService.super.update(data);
    }

    @Override
    public Session getByUuid(String uuid) {
        var session = SessionService.super.getByUuid(uuid);
        var result = this.getSessionResult(uuid);
        session.setSessionResult(result);
        return session;
    }

    @Override
    @Transactional
    public void remove(String uuid) {
        var session = SessionService.super.getByUuid(uuid);
        session.setEndTime(LocalDateTime.now());
        session.setActive(false);
        SessionService.super.update(session);
    }

    private void throwBusinessException(AppMessages msgExceptionSessionStarted) {
        var msg = MessageService.getMessage(messageSource, msgExceptionSessionStarted.getMsgKey());
        throw new BusinessException(msg);
    }

    public List<VoteSummary> getLastSessionResultByTopic(String topicUuid) {
        var lastSessionClosed = sessionRepository.findFirstByTopicUuidOrderByEndTimeDesc(topicUuid);
        if(lastSessionClosed.isEmpty()) {
            return new ArrayList<>();
        }

        return getVoteSummaries(lastSessionClosed.get().getUuid());
    }

    public List<VoteSummary> getSessionResult(String sessionUuid) {
        SessionService.super.getByUuid(sessionUuid);
        return getVoteSummaries(sessionUuid);
    }

    private List<VoteSummary> getVoteSummaries(String sessionUuid) {
        return sessionRepository.summarizeVotes(sessionUuid);
    }
}
