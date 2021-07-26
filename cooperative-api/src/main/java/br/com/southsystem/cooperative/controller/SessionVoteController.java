package br.com.southsystem.cooperative.controller;

import br.com.southsystem.cooperative.config.Cors;
import br.com.southsystem.cooperative.dto.vote.VoteDTO;
import br.com.southsystem.cooperative.facade.SessionVoteFacade;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface SessionVoteController extends Cors {

    Logger log();
    SessionVoteFacade getService();

    @GetMapping("/{uuid}")
    default ResponseEntity getVote(@PathVariable String uuid) {
        log().info("m=getVote, retrieving vote: {}", uuid);
        var vote = getService().getByUuid(uuid);
        return ResponseEntity.ok().body(vote);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    default void toVote(@RequestBody VoteDTO dto) {
        log().info("m=toVote, registering vote for user: {} and session: {}", dto.getUser(), dto.getSession());
        getService().toVote(dto);
    }

}
