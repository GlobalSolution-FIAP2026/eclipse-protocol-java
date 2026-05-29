package br.com.fiap.eclipseprotocol.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TB_PLANTACAO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plantacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PLANTACAO")
    private Long id;

    @Column(name = "NM_PLANTACAO", nullable = false, length = 100)
    private String nome;

    @Column(name = "DS_CULTURA", nullable = false, length = 100)
    private String cultura;

    @Column(name = "NR_AREA_HECTARES", nullable = false)
    private Double areaHectares;

    @Column(name = "DS_LOCALIZACAO", length = 300)
    private String localizacao;

}
