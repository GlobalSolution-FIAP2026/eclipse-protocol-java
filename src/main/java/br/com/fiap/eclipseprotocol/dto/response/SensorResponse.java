package br.com.fiap.eclipseprotocol.dto.response;

import br.com.fiap.eclipseprotocol.model.Sensor;

public record SensorResponse(
        Long id,
        String nome,
        String tipo,
        Long plantacao

) {
    public static SensorResponse from(Sensor s) {
        return new SensorResponse(
                s.getId(),
                s.getNome(),
                s.getTipo(),
                s.getPlantacao().getId()
        );
    }
}