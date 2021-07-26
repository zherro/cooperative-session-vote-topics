package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.dto.session.RequestSessionFilter;
import br.com.southsystem.cooperative.model.Session;
import br.com.southsystem.cooperative.model.types.VoteSummary;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SessionService extends SpecService<Session> {
    Session hasActiveSessionForTopic(String topic);
    List<VoteSummary> getLastSessionResultByTopic(String topic);
    Page<Session> list(Pageable pageable, RequestSessionFilter filter);
}
