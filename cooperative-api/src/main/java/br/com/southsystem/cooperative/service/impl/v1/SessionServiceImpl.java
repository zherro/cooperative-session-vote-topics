package br.com.southsystem.cooperative.service.impl.v1;

import static br.com.southsystem.cooperative.exceptions.AppMessages.MSG_EXCEPTION_SESSION_STARTED;
import br.com.southsystem.cooperative.exceptions.BusinessException;
import br.com.southsystem.cooperative.exceptions.MessageService;
import br.com.southsystem.cooperative.exceptions.ResourceNotFoundException;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.repository.SessionRepository;
import br.com.southsystem.cooperative.repository.SpecRepository;
import br.com.southsystem.cooperative.service.SessionService;
import java.time.LocalDateTime;
import org.springframework.context.MessageSource;
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

    @Override
    public Session create(Session data) {
        defineEndTime(data);
        return SessionService.super.create(data);
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
            var msg = MessageService.getMessage(messageSource, MSG_EXCEPTION_SESSION_STARTED.getMsgKey());
            throw new BusinessException(msg);
        }
        defineEndTime(data);
        return SessionService.super.update(data);
    }
}
