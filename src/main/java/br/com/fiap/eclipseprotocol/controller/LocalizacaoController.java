package br.com.fiap.eclipseprotocol.controller;

import br.com.fiap.eclipseprotocol.dto.request.LocalizacaoRequest;
import br.com.fiap.eclipseprotocol.dto.response.LocalizacaoResponse;
import br.com.fiap.eclipseprotocol.model.Localizacao;
import br.com.fiap.eclipseprotocol.service.LocalizacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Localizações", description = "Gerenciamento das localizações")
@RestController
@RequestMapping("/localizacoes")
public class LocalizacaoController {

    private final LocalizacaoService service;

    public LocalizacaoController(LocalizacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LocalizacaoResponse>> listarTodos() {
        List<LocalizacaoResponse> response = service.listarTodos()
                .stream()
                .map(LocalizacaoResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalizacaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(LocalizacaoResponse.from(service.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<LocalizacaoResponse> criar(@RequestBody @Valid LocalizacaoRequest request) {
        Localizacao localizacao = Localizacao.builder()
                .cidade(request.cidade())
                .estado(request.estado())
                .pais(request.pais())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .cep(request.cep())
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(LocalizacaoResponse.from(service.salvar(localizacao)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocalizacaoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid LocalizacaoRequest request
    ) {
        Localizacao localizacao = Localizacao.builder()
                .cidade(request.cidade())
                .estado(request.estado())
                .pais(request.pais())
                .latitude(request.latitude())
                .longitude(request.longitude())
                .cep(request.cep())
                .build();

        return ResponseEntity.ok(LocalizacaoResponse.from(service.atualizar(id, localizacao)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}