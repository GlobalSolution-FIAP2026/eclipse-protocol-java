package br.com.fiap.eclipseprotocol.service;

import br.com.fiap.eclipseprotocol.model.Sensor;
import br.com.fiap.eclipseprotocol.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    private final SensorRepository repository;

    public SensorService(SensorRepository repository) {
        this.repository = repository;
    }

    public List<Sensor> listarTodos() {
        return repository.findAll();
    }

    public Sensor buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));
    }

    public Sensor salvar(Sensor sensor) {
        return repository.save(sensor);
    }

    public Sensor atualizar(Long id, Sensor sensorAtualizado) {
        Sensor sensor = buscarPorId(id);

        sensor.setNome(sensorAtualizado.getNome());
        sensor.setTipo(sensorAtualizado.getTipo());
        sensor.setAtivo(sensorAtualizado.getAtivo());
        sensor.setPlantacao(sensorAtualizado.getPlantacao());

        return repository.save(sensor);
    }

    public void deletar(Long id) {
        Sensor sensor = buscarPorId(id);
        repository.delete(sensor);
    }
}