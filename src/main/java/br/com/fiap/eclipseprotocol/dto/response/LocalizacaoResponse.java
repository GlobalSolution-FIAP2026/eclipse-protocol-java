package br.com.fiap.eclipseprotocol.dto.response;

import br.com.fiap.eclipseprotocol.model.Localizacao;

public record LocalizacaoResponse(
        Long id,
        String cidade,
        String estado,
        String pais,
        Double latitude,
        Double longitude,
        String cep
) {
    public static LocalizacaoResponse from(Localizacao l) {
        return new LocalizacaoResponse(
                l.getId(),
                l.getCidade(),
                l.getEstado(),
                l.getPais(),
                l.getLatitude(),
                l.getLongitude(),
                l.getCep()
        );
    }
}