package br.com.fiap.eclipseprotocol.service;

import br.com.fiap.eclipseprotocol.exception.ResourceNotFoundException;
import br.com.fiap.eclipseprotocol.model.RiscoOrbital;
import br.com.fiap.eclipseprotocol.repository.RiscoOrbitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiscoOrbitalService {

    private final RiscoOrbitalRepository repository;

    public RiscoOrbitalService(RiscoOrbitalRepository repository) {
        this.repository = repository;
    }

    public List<RiscoOrbital> listarTodos() {
        return repository.findAll();
    }

    public RiscoOrbital buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Risco orbital não encontrado"));
    }

    public RiscoOrbital salvar(RiscoOrbital riscoOrbital) {
        return repository.save(riscoOrbital);
    }

    public RiscoOrbital atualizar(Long id, RiscoOrbital riscoAtualizado) {
        RiscoOrbital risco = buscarPorId(id);

        risco.setSatelite(riscoAtualizado.getSatelite());
        risco.setLixoEspacial(riscoAtualizado.getLixoEspacial());
        risco.setNivelRisco(riscoAtualizado.getNivelRisco());
        risco.setDescricaoRisco(riscoAtualizado.getDescricaoRisco());
        risco.setDataAnalise(riscoAtualizado.getDataAnalise());

        return repository.save(risco);
    }

    public void deletar(Long id) {
        RiscoOrbital risco = buscarPorId(id);
        repository.delete(risco);
    }
}

