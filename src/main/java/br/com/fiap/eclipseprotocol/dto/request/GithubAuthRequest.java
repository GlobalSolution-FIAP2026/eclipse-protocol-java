package br.com.fiap.eclipseprotocol.dto.request;

public record GithubAuthRequest(String code, String redirectUri) {}