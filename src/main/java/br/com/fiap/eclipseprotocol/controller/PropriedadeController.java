package br.com.fiap.eclipseprotocol.controller;

import br.com.fiap.eclipseprotocol.dto.request.PropriedadeRequest;
import br.com.fiap.eclipseprotocol.dto.response.PropriedadeResponse;
import br.com.fiap.eclipseprotocol.model.Localizacao;
import br.com.fiap.eclipseprotocol.model.Propriedade;
import br.com.fiap.eclipseprotocol.model.Usuario;
import br.com.fiap.eclipseprotocol.service.LocalizacaoService;
import br.com.fiap.eclipseprotocol.service.PropriedadeService;
import br.com.fiap.eclipseprotocol.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<PropriedadeResponse>> listarTodos() {
        List<PropriedadeResponse> response = propriedadeService.listarTodos()
                .stream()
                .map(PropriedadeResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropriedadeResponse> buscarPorId(@PathVariable Long id) {
        Propriedade propriedade = propriedadeService.buscarPorId(id);
        return ResponseEntity.ok(PropriedadeResponse.from(propriedade));
    }

    @PostMapping
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
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        propriedadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}