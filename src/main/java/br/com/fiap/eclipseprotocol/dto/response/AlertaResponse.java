package br.com.fiap.eclipseprotocol.dto.response;

import br.com.fiap.eclipseprotocol.model.Alerta;
import java.time.LocalDateTime;

public record AlertaResponse(
        Long id,
        String tipoAlerta,
        String severidade,
        String mensagem,
        String status,
        LocalDateTime dataCriacao,
        Long idLeitura,
        Long idPlantacao,
        String cultura
) {
    public static AlertaResponse from(Alerta a) {
        return new AlertaResponse(
                a.getId(),
                a.getTipoAlerta() != null ? a.getTipoAlerta().name() : null,
                a.getSeveridade() != null ? a.getSeveridade().name() : null,
                a.getMensagem(),
                a.getStatus() != null ? a.getStatus().name() : null,
                a.getDataCriacao(),
                a.getLeitura() != null ? a.getLeitura().getId() : null,
                a.getLeitura() != null && a.getLeitura().getSensor() != null && a.getLeitura().getSensor().getPlantacao() != null
                        ? a.getLeitura().getSensor().getPlantacao().getId()
                        : null,
                a.getLeitura() != null && a.getLeitura().getSensor() != null && a.getLeitura().getSensor().getPlantacao() != null
                        ? a.getLeitura().getSensor().getPlantacao().getCultura()
                        : null
        );
    }
}