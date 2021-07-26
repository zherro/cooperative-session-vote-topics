package br.com.southsystem.cooperative.controller.v1;

import br.com.southsystem.cooperative.controller.SessionVoteController;
import br.com.southsystem.cooperative.facade.SessionVoteFacade;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v1/voto")
public class SessionVoteControllerV1Impl implements SessionVoteController {
    private static final Logger log = LoggerFactory.getLogger(SessionVoteControllerV1Impl.class);

    private final SessionVoteFacade voteFacade;

    public SessionVoteControllerV1Impl(SessionVoteFacade voteFacade) {
        this.voteFacade = voteFacade;
    }

    @Override
    public Logger log()  {
        return log;
    }

    @Override
    public SessionVoteFacade getService() {
        return voteFacade;
    }
}
