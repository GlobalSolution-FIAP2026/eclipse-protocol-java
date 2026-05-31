package br.com.fiap.eclipseprotocol.controller;

import br.com.fiap.eclipseprotocol.dto.request.PropriedadeRequest;
import br.com.fiap.eclipseprotocol.dto.response.PropriedadeResponse;
import br.com.fiap.eclipseprotocol.model.Localizacao;
import br.com.fiap.eclipseprotocol.model.Propriedade;
import br.com.fiap.eclipseprotocol.model.Usuario;
import br.com.fiap.eclipseprotocol.service.LocalizacaoService;
import br.com.fiap.eclipseprotocol.service.PropriedadeService;
import br.com.fiap.eclipseprotocol.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Tag(name = "Propriedades", description = "Gerenciamento das propriedades rurais")
@RestController
@RequestMapping("/propriedades")
public class PropriedadeController {

    private final PropriedadeService propriedadeService;
    private final LocalizacaoService localizacaoService;
    private final UsuarioService usuarioService;

    public PropriedadeController(
            PropriedadeService propriedadeService,
            LocalizacaoService localizacaoService,
            UsuarioService usuarioService
    ) {
        this.propriedadeService = propriedadeService;
        this.localizacaoService = localizacaoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Listar registros", description = "Retorna todos os registros cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registros encontrados com sucesso"),
            @ApiResponse(responseCode = "204", description = "Nenhum registro cadastrado")
    })
    public ResponseEntity<List<PropriedadeResponse>> listarTodos() {
        List<PropriedadeResponse> response = propriedadeService.listarTodos()
                .stream()
                .map(PropriedadeResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar por ID", description = "Retorna um registro específico pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<PropriedadeResponse> buscarPorId(@PathVariable Long id) {
        Propriedade propriedade = propriedadeService.buscarPorId(id);
        return ResponseEntity.ok(PropriedadeResponse.from(propriedade));
    }

    @PostMapping
    @Operation(summary = "Criar registro", description = "Cadastra um novo registro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public ResponseEntity<PropriedadeResponse> criar(@RequestBody @Valid PropriedadeRequest request) {
        Localizacao localizacao = localizacaoService.buscarPorId(request.idLocalizacao());
        Usuario usuario = usuarioService.buscarPorId(request.idUsuario());

        Propriedade propriedade = Propriedade.builder()
                .nome(request.nome())
                .proprietario(request.proprietario())
                .areaTotal(request.areaTotal())
                .tipoSolo(request.tipoSolo())
                .localizacao(localizacao)
                .usuario(usuario)
                .build();

        Propriedade salva = propriedadeService.salvar(propriedade);

        return ResponseEntity.status(HttpStatus.CREATED).body(PropriedadeResponse.from(salva));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar registro", description = "Atualiza um registro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<PropriedadeResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid PropriedadeRequest request
    ) {
        Localizacao localizacao = localizacaoService.buscarPorId(request.idLocalizacao());
        Usuario usuario = usuarioService.buscarPorId(request.idUsuario());

        Propriedade propriedadeAtualizada = Propriedade.builder()
                .nome(request.nome())
                .proprietario(request.proprietario())
                .areaTotal(request.areaTotal())
                .tipoSolo(request.tipoSolo())
                .localizacao(localizacao)
                .usuario(usuario)
                .build();

        Propriedade atualizada = propriedadeService.atualizar(id, propriedadeAtualizada);

        return ResponseEntity.ok(PropriedadeResponse.from(atualizada));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar registro", description = "Remove um registro pelo ID informado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Registro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "409", description = "Registro possui vínculo e não pode ser deletado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        propriedadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}