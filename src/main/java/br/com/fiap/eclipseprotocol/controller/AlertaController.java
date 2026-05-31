package br.com.fiap.eclipseprotocol.controller;

import br.com.fiap.eclipseprotocol.dto.request.AlertaRequest;
import br.com.fiap.eclipseprotocol.dto.response.AlertaResponse;
import br.com.fiap.eclipseprotocol.model.Alerta;
import br.com.fiap.eclipseprotocol.model.Leitura;
import br.com.fiap.eclipseprotocol.service.AlertaService;
import br.com.fiap.eclipseprotocol.service.LeituraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Alertas", description = "Gerenciamento dos alertas gerados")
@RestController
@RequestMapping("/alertas")
public class AlertaController {

    private final AlertaService alertaService;
    private final LeituraService leituraService;

    public AlertaController(AlertaService alertaService, LeituraService leituraService) {
        this.alertaService = alertaService;
        this.leituraService = leituraService;
    }

    @GetMapping
    @Operation(summary = "Listar registros", description = "Retorna todos os registros cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum registro cadastrado")
    })
    public ResponseEntity<List<AlertaResponse>> listarTodos() {
        List<AlertaResponse> response = alertaService.listarTodos()
                .stream()
                .map(AlertaResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna um registro específico pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<AlertaResponse> buscarPorId(@PathVariable Long id) {
        Alerta alerta = alertaService.buscarPorId(id);
        return ResponseEntity.ok(AlertaResponse.from(alerta));
    }

    @PostMapping
    @Operation(summary = "Criar registro", description = "Cadastra um novo registro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public ResponseEntity<AlertaResponse> criar(@RequestBody @Valid AlertaRequest request) {
        Leitura leitura = leituraService.buscarPorId(request.idLeitura());

        Alerta alerta = Alerta.builder()
                .tipoAlerta(Alerta.TipoAlerta.valueOf(request.tipoAlerta()))
                .severidade(Alerta.Severidade.valueOf(request.severidade()))
                .mensagem(request.mensagem())
                .status(Alerta.StatusAlerta.ABERTO)
                .dataCriacao(LocalDateTime.now())
                .leitura(leitura)
                .build();

        Alerta salvo = alertaService.salvar(alerta);

        return ResponseEntity.status(HttpStatus.CREATED).body(AlertaResponse.from(salvo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro", description = "Atualiza um registro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<AlertaResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AlertaRequest request
    ) {
        Leitura leitura = leituraService.buscarPorId(request.idLeitura());

        Alerta alertaAtualizado = Alerta.builder()
                .tipoAlerta(Alerta.TipoAlerta.valueOf(request.tipoAlerta()))
                .severidade(Alerta.Severidade.valueOf(request.severidade()))
                .mensagem(request.mensagem())
                .status(Alerta.StatusAlerta.ABERTO)
                .dataCriacao(LocalDateTime.now())
                .leitura(leitura)
                .build();

        Alerta atualizado = alertaService.atualizar(id, alertaAtualizado);

        return ResponseEntity.ok(AlertaResponse.from(atualizado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar registro", description = "Remove um registro pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Registro possui vínculo e não pode ser deletado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alertaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}