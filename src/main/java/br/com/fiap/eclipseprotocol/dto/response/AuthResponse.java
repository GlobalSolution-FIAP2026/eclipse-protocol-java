package br.com.fiap.eclipseprotocol.dto.response;

public record AuthResponse(
        String token,
        String tipo
) {
}