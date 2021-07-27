package br.com.southsystem.cooperative.dto.session;

import br.com.southsystem.cooperative.dto.pageable.RequestFilter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSessionFilter extends RequestFilter {
    private String active;
}
