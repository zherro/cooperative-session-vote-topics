package br.com.southsystem.cooperative.config;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = { "http://localhost:3000", "https://teste.herokuapp.com" })
public interface Cors {
}