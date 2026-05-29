package br.com.fiap.eclipseprotocol.controller;

import br.com.fiap.eclipseprotocol.dto.request.PlantacaoRequest;
import br.com.fiap.eclipseprotocol.dto.response.PlantacaoResponse;
import br.com.fiap.eclipseprotocol.model.Plantacao;
import br.com.fiap.eclipseprotocol.model.Propriedade;
import br.com.fiap.eclipseprotocol.service.PlantacaoService;
import br.com.fiap.eclipseprotocol.service.PropriedadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plantacoes")
public class PlantacaoController {

    private final PlantacaoService plantacaoService;
    private final PropriedadeService propriedadeService;

    public PlantacaoController(PlantacaoService plantacaoService, PropriedadeService propriedadeService) {
        this.plantacaoService = plantacaoService;
        this.propriedadeService = propriedadeService;
    }

    @GetMapping
    public ResponseEntity<List<PlantacaoResponse>> listarTodos() {
        List<PlantacaoResponse> response = plantacaoService.listarTodos()
                .stream()
                .map(PlantacaoResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantacaoResponse> buscarPorId(@PathVariable Long id) {
        Plantacao plantacao = plantacaoService.buscarPorId(id);
        return ResponseEntity.ok(PlantacaoResponse.from(plantacao));
    }

    @PostMapping
    public ResponseEntity<PlantacaoResponse> criar(@RequestBody @Valid PlantacaoRequest request) {
        Propriedade propriedade = propriedadeService.buscarPorId(request.idPropriedade());

        Plantacao plantacao = Plantacao.builder()
                .nome(request.nome())
                .cultura(request.cultura())
                .areaHectares(request.areaHectares())
                .status(request.status())
                .propriedade(propriedade)
                .build();

        Plantacao salva = plantacaoService.salvar(plantacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(PlantacaoResponse.from(salva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlantacaoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PlantacaoRequest request
    ) {
        Propriedade propriedade = propriedadeService.buscarPorId(request.idPropriedade());

        Plantacao plantacaoAtualizada = Plantacao.builder()
                .nome(request.nome())
                .cultura(request.cultura())
                .areaHectares(request.areaHectares())
                .status(request.status())
                .propriedade(propriedade)
                .build();

        Plantacao atualizada = plantacaoService.atualizar(id, plantacaoAtualizada);

        return ResponseEntity.ok(PlantacaoResponse.from(atualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        plantacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}