package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.controller.SessionVoteController;
import br.com.southsystem.cooperative.controller.v1.SessionVoteControllerV1Impl;
import br.com.southsystem.cooperative.dto.vote.VoteDTO;
import br.com.southsystem.cooperative.facade.SessionVoteFacade;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v2")
@RestController
@RequestMapping("/api/v2/voto")
public class SessionVoteControllerV2Impl implements SessionVoteController {

    private static final Logger log = LoggerFactory.getLogger(SessionVoteControllerV2Impl.class);
    private final SessionVoteFacade voteFacade;

    public SessionVoteControllerV2Impl(SessionVoteFacade voteFacade) {
        this.voteFacade = voteFacade;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void toVote(@RequestBody VoteDTO dto) {
        voteFacade.toVoteValidateByApi(dto);
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
