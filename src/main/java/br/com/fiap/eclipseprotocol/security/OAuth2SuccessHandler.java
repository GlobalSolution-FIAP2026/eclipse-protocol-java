package br.com.fiap.eclipseprotocol.security;

import br.com.fiap.eclipseprotocol.model.Usuario;
import br.com.fiap.eclipseprotocol.repository.UsuarioRepository;
import br.com.fiap.eclipseprotocol.service.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final TokenService tokenService;

    public OAuth2SuccessHandler(UsuarioRepository usuarioRepository, TokenService tokenService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String nome = oauthUser.getAttribute("name");

        if (email == null) {
            email = oauthUser.getAttribute("login") + "@github.com";
        }

        if (nome == null) {
            nome = oauthUser.getAttribute("login");
        }

        String finalEmail = email;
        String finalNome = nome;

        Usuario usuario = usuarioRepository.findByEmail(finalEmail)
                .orElseGet(() -> usuarioRepository.save(
                        Usuario.builder()
                                .nome(finalNome)
                                .email(finalEmail)
                                .senha("OAUTH2_GITHUB")
                                .build()
                ));

        String token = tokenService.gerarToken(usuario);
        response.sendRedirect("eclipseprotocolmobile://token=" + token);
    }
}