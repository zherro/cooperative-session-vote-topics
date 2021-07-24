package br.com.southsystem.cooperative.controller.v1;

import br.com.southsystem.cooperative.dto.vote.VoteDTO;
import br.com.southsystem.cooperative.facade.SessionVoteFacade;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Cooperative API v1")
@RestController
@RequestMapping("/api/v1/vote")
public class SessionVoteController {

    private final SessionVoteFacade voteFacade;

    public SessionVoteController(SessionVoteFacade voteFacade) {
        this.voteFacade = voteFacade;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getVote(@PathVariable String uuid) {
        var vote = voteFacade.getByUuid(uuid);
        return ResponseEntity.ok().body(vote);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void toVote(@RequestBody VoteDTO dto) {
        voteFacade.toVote(dto);
    }

}
