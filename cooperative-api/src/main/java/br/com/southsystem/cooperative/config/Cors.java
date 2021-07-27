package br.com.southsystem.cooperative.config;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {
        "http://localhost:3000", "http://localhost:3001",
        "http://cooperative-app:3001", "https://cooperative-mvp-app.herokuapp.com" })
public interface Cors {
}