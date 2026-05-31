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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    @Operation(summary = "Listar registros", description = "Retorna todos os registros cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum registro cadastrado")
    })
    public ResponseEntity<List<LocalizacaoResponse>> listarTodos() {
        List<LocalizacaoResponse> response = service.listarTodos()
                .stream()
                .map(LocalizacaoResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna um registro específico pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<LocalizacaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(LocalizacaoResponse.from(service.buscarPorId(id)));
    }

    @PostMapping
    @Operation(summary = "Criar registro", description = "Cadastra um novo registro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
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
    @Operation(summary = "Atualizar registro", description = "Atualiza um registro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
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
    @Operation(summary = "Deletar registro", description = "Remove um registro pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Registro possui vínculo e não pode ser deletado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}