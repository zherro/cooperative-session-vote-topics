package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.model.SessionVote;

public interface SessionVoteService extends SpecService<SessionVote> {
    boolean userHasVoted(String session, String userUuid);
}
