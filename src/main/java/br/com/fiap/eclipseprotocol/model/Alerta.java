package br.com.fiap.eclipseprotocol.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_ALERTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERTA")
    private Long id;

    @Column(name = "NR_TEMPERATURA")
    private Double temperatura;


    @Column(name = "NR_UMIDADE")
    private Double umidade;

    @Column(name = "NR_PRECIPITACAO")
    private Double precipitacao;

    @Column(name = "NR_NDVI") // Índice de Vegetação por Diferença Normalizada
    private Double ndvi;
}
