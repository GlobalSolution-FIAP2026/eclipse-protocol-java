package br.com.fiap.eclipseprotocol.controller;

import br.com.fiap.eclipseprotocol.dto.request.LeituraRequest;
import br.com.fiap.eclipseprotocol.dto.response.LeituraResponse;
import br.com.fiap.eclipseprotocol.model.Leitura;
import br.com.fiap.eclipseprotocol.model.Sensor;
import br.com.fiap.eclipseprotocol.service.LeituraService;
import br.com.fiap.eclipseprotocol.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leituras")
public class LeituraController {

    private final LeituraService leituraService;
    private final SensorService sensorService;

    public LeituraController(LeituraService leituraService, SensorService sensorService) {
        this.leituraService = leituraService;
        this.sensorService = sensorService;
    }

    @GetMapping
    public ResponseEntity<List<LeituraResponse>> listarTodos() {
        List<LeituraResponse> response = leituraService.listarTodos()
                .stream()
                .map(LeituraResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeituraResponse> buscarPorId(@PathVariable Long id) {
        Leitura leitura = leituraService.buscarPorId(id);
        return ResponseEntity.ok(LeituraResponse.from(leitura));
    }

    @PostMapping
    public ResponseEntity<LeituraResponse> criar(@RequestBody @Valid LeituraRequest request) {
        Sensor sensor = sensorService.buscarPorId(request.sensor());

        Leitura leitura = Leitura.builder()
                .temperatura(request.temperatura())
                .umidade(request.umidade())
                .precipitacao(request.precipitacao())
                .ndvi(request.ndvi())
                .sensor(sensor)
                .build();

        Leitura salva = leituraService.salvar(leitura);

        return ResponseEntity.status(HttpStatus.CREATED).body(LeituraResponse.from(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeituraResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid LeituraRequest request
    ) {
        Sensor sensor = sensorService.buscarPorId(request.sensor());

        Leitura leituraAtualizada = Leitura.builder()
                .temperatura(request.temperatura())
                .umidade(request.umidade())
                .precipitacao(request.precipitacao())
                .ndvi(request.ndvi())
                .sensor(sensor)
                .build();

        Leitura atualizada = leituraService.atualizar(id, leituraAtualizada);

        return ResponseEntity.ok(LeituraResponse.from(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        leituraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}