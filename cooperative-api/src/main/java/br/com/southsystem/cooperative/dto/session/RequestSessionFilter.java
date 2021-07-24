package br.com.southsystem.cooperative.dto.session;

import br.com.southsystem.cooperative.dto.pageable.RequestFilter;
import lombok.Data;

@Data
public class RequestSessionFilter extends RequestFilter {
    private String active;
}
