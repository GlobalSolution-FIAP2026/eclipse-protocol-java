package br.com.fiap.eclipseprotocol.service;

import br.com.fiap.eclipseprotocol.model.Localizacao;
import br.com.fiap.eclipseprotocol.repository.LocalizacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalizacaoService {

    private final LocalizacaoRepository repository;

    public LocalizacaoService(LocalizacaoRepository repository) {
        this.repository = repository;
    }

    public List<Localizacao> listarTodos(){
        return repository.findAll();
    }

    public Localizacao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Localização não encontrada"));
    }

    public Localizacao salvar(Localizacao localizacao) {
        return repository.save(localizacao);
    }

    public Localizacao atualizar(Long id, Localizacao localizacaoAtualizada) {
        Localizacao localizacao = buscarPorId(id);

        localizacao.setCidade(localizacaoAtualizada.getCidade());
        localizacao.setEstado(localizacaoAtualizada.getEstado());
        localizacao.setPais(localizacaoAtualizada.getPais());
        localizacao.setLatitude(localizacaoAtualizada.getLatitude());
        localizacao.setLongitude(localizacaoAtualizada.getLongitude());
        localizacao.setCep(localizacaoAtualizada.getCep());

        return repository.save(localizacao);
    }

    public void deletar(Long id) {
        Localizacao localizacao = buscarPorId(id);
        repository.delete(localizacao);
    }
}
