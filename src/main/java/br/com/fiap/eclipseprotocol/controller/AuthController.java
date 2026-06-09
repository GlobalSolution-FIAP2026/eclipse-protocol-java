package br.com.fiap.eclipseprotocol.controller;

import br.com.fiap.eclipseprotocol.dto.request.AuthRequest;
import br.com.fiap.eclipseprotocol.dto.request.GithubAuthRequest;
import br.com.fiap.eclipseprotocol.dto.response.AuthResponse;
import br.com.fiap.eclipseprotocol.exception.ResourceNotFoundException;
import br.com.fiap.eclipseprotocol.model.Usuario;
import br.com.fiap.eclipseprotocol.repository.UsuarioRepository;
import br.com.fiap.eclipseprotocol.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Geração de token JWT para acesso à API")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String githubClientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String githubClientSecret;

    public AuthController(UsuarioRepository usuarioRepository,
                          TokenService tokenService,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(request.senha(), usuario.getSenha())) {
            throw new ResourceNotFoundException("Email ou senha inválidos");
        }

        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }

    @PostMapping("/github")
    public ResponseEntity<AuthResponse> githubLogin(@RequestBody GithubAuthRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.set("Accept", "application/json");

        Map<String, String> tokenBody = Map.of(
                "client_id", githubClientId,
                "client_secret", githubClientSecret,
                "code", request.code(),
                "redirect_uri", request.redirectUri()
        );

        Map<?, ?> tokenResponse = restTemplate.postForObject(
                "https://github.com/login/oauth/access_token",
                new HttpEntity<>(tokenBody, tokenHeaders),
                Map.class
        );

        String accessToken = (String) tokenResponse.get("access_token");

        HttpHeaders emailHeaders = new HttpHeaders();
        emailHeaders.set("Authorization", "Bearer " + accessToken);
        emailHeaders.set("Accept", "application/json");

        List<?> emails = restTemplate.exchange(
                "https://api.github.com/user/emails",
                HttpMethod.GET,
                new HttpEntity<>(emailHeaders),
                List.class
        ).getBody();

        String email = emails.stream()
                .map(e -> (Map<?, ?>) e)
                .filter(e -> Boolean.TRUE.equals(e.get("primary")))
                .map(e -> (String) e.get("email"))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Email do GitHub não encontrado"));

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não cadastrado: " + email));

        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}