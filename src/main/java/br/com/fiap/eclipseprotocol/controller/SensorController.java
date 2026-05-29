package br.com.fiap.eclipseprotocol.controller;

import br.com.fiap.eclipseprotocol.dto.request.SensorRequest;
import br.com.fiap.eclipseprotocol.dto.response.SensorResponse;
import br.com.fiap.eclipseprotocol.model.Plantacao;
import br.com.fiap.eclipseprotocol.model.Sensor;
import br.com.fiap.eclipseprotocol.service.PlantacaoService;
import br.com.fiap.eclipseprotocol.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensores")
public class SensorController {

    private final SensorService sensorService;
    private final PlantacaoService plantacaoService;

    public SensorController(
            SensorService sensorService,
            PlantacaoService plantacaoService
    ) {
        this.sensorService = sensorService;
        this.plantacaoService = plantacaoService;
    }

    @GetMapping
    public ResponseEntity<List<SensorResponse>> listarTodos() {
        List<SensorResponse> response = sensorService.listarTodos()
                .stream()
                .map(SensorResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorResponse> buscarPorId(@PathVariable Long id) {
        Sensor sensor = sensorService.buscarPorId(id);
        return ResponseEntity.ok(SensorResponse.from(sensor));
    }

    @PostMapping
    public ResponseEntity<SensorResponse> criar(@RequestBody @Valid SensorRequest request) {

        Plantacao plantacao = plantacaoService.buscarPorId(request.idPlantacao());

        Sensor sensor = Sensor.builder()
                .nome(request.nome())
                .tipo(request.tipo())
                .plantacao(plantacao)
                .build();

        Sensor salvo = sensorService.salvar(sensor);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SensorResponse.from(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid SensorRequest request
    ) {

        Plantacao plantacao = plantacaoService.buscarPorId(request.idPlantacao());

        Sensor sensorAtualizado = Sensor.builder()
                .nome(request.nome())
                .tipo(request.tipo())
                .plantacao(plantacao)
                .build();

        Sensor atualizado = sensorService.atualizar(id, sensorAtualizado);

        return ResponseEntity.ok(SensorResponse.from(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sensorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}