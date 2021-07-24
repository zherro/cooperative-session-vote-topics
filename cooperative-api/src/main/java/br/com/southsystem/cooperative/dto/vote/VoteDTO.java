package br.com.southsystem.cooperative.dto.vote;

import lombok.Data;

@Data
public class VoteDTO {
    private String session;
    private String user;
    private String vote;
}
