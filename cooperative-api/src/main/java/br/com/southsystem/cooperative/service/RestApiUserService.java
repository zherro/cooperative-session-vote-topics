package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.dto.ValidatorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestApiUserService
{

    private final RestTemplate restTemplate;
    private final String uri;

    public RestApiUserService(RestTemplate restTemplate, @Value("${api.validation-api}") String uri) {
        this.restTemplate = restTemplate;
        this.uri = uri;
    }

    public ValidatorResponse userAbleToVote(final String type, final String doc) {
        var uri = this.uri.replace("{type}", type)
                .replace("{doc}", doc);
       return restTemplate.getForEntity(uri, ValidatorResponse.class)
               .getBody();
    }
}
