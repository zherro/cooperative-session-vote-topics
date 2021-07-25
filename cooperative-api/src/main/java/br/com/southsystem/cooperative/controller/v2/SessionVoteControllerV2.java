package br.com.southsystem.cooperative.controller.v2;

import br.com.southsystem.cooperative.dto.vote.VoteDTO;
import br.com.southsystem.cooperative.facade.SessionVoteFacade;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v2/vote")
public class SessionVoteControllerV2
    extends br.com.southsystem.cooperative.controller.v1.SessionVoteController {

    private final SessionVoteFacade voteFacade;

    public SessionVoteControllerV2(SessionVoteFacade voteFacade) {
        super(voteFacade);
        this.voteFacade = voteFacade;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void toVote(@RequestBody VoteDTO dto) {
        voteFacade.toVoteValidateByApi(dto);
    }

}
