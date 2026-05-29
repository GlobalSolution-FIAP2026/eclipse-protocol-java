package br.com.fiap.eclipseprotocol.service;

import br.com.fiap.eclipseprotocol.exception.ResourceNotFoundException;
import br.com.fiap.eclipseprotocol.model.Leitura;
import br.com.fiap.eclipseprotocol.repository.LeituraRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeituraService {

    private final LeituraRepository repository;

    public LeituraService(LeituraRepository repository) {
        this.repository = repository;
    }

    public List<Leitura> listarTodos() {
        return repository.findAll();
    }

    public Leitura buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura não encontrada"));
    }

    public Leitura salvar(Leitura leitura) {
        return repository.save(leitura);
    }

    public Leitura atualizar(Long id, Leitura leituraAtualizada) {
        Leitura leitura = buscarPorId(id);

        leitura.setTemperatura(leituraAtualizada.getTemperatura());
        leitura.setUmidade(leituraAtualizada.getUmidade());
        leitura.setPrecipitacao(leituraAtualizada.getPrecipitacao());
        leitura.setNdvi(leituraAtualizada.getNdvi());
        leitura.setDataLeitura(leituraAtualizada.getDataLeitura());
        leitura.setSensor(leituraAtualizada.getSensor());

        return repository.save(leitura);
    }

    public void deletar(Long id) {
        Leitura leitura = buscarPorId(id);
        repository.delete(leitura);
    }
}