package org.pruebatecnica.parqueadero.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parqueadero")
public class Parqueadero implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idParqueadero;

    @Column(length = 40, nullable = false)
    private String nombre;

    @Column(length = 3)
    private int capacidadMax;

    @Column(length = 3)
    private int capacidadOcup;

    @Column(length = 10)
    private BigDecimal costoHora;

    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario socio;

    @JsonIgnoreProperties("parqueadero")
    @OneToMany(mappedBy = "parqueadero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registro> registros = new ArrayList<>();

    @JsonIgnoreProperties("parqueadero")
    @OneToMany(mappedBy = "parqueadero", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Historial> historiales = new ArrayList<>();
}
