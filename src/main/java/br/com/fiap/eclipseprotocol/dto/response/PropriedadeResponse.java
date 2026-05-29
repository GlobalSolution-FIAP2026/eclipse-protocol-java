package br.com.fiap.eclipseprotocol.dto.response;

import br.com.fiap.eclipseprotocol.model.Propriedade;

public record PropriedadeResponse(
        Long id,
        String nome,
        String proprietario,
        Double areaTotal,
        String tipoSolo,
        LocalizacaoResponse localizacao,
        UsuarioResponse usuario
) {
    public static PropriedadeResponse from(Propriedade p) {
        return new PropriedadeResponse(
                p.getId(),
                p.getNome(),
                p.getProprietario(),
                p.getAreaTotal(),
                p.getTipoSolo(),
                LocalizacaoResponse.from(p.getLocalizacao()),
                UsuarioResponse.from(p.getUsuario())
        );
    }
}