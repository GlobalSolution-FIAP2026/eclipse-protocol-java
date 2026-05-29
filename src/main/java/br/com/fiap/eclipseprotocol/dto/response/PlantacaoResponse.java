package br.com.fiap.eclipseprotocol.dto.response;

import br.com.fiap.eclipseprotocol.model.Plantacao;

public record PlantacaoResponse(
        Long id,
        String nome,
        String cultura,
        Double areaHectares,
        String status,
        Long idPropriedade
) {
    public static PlantacaoResponse from(Plantacao p) {
        return new PlantacaoResponse(
                p.getId(),
                p.getNome(),
                p.getCultura(),
                p.getAreaHectares(),
                p.getStatus(),
                p.getPropriedade() != null ? p.getPropriedade().getId() : null
        );
    }
}