package br.com.fiap.eclipseprotocol.dto.response;

import br.com.fiap.eclipseprotocol.model.Leitura;
import java.time.LocalDateTime;

public record LeituraResponse(
        Long id,
        Double temperatura,
        Double umidade,
        Double precipitacao,
        Double ndvi,
        LocalDateTime dataLeitura,
        Long sensor
) {
    public static LeituraResponse from(Leitura l) {
        return new LeituraResponse(
                l.getId(),
                l.getTemperatura(),
                l.getUmidade(),
                l.getPrecipitacao(),
                l.getNdvi(),
                l.getDataLeitura(),
                l.getSensor().getId()
        );
    }
}