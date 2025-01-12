package org.pruebatecnica.parqueadero.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehiculo")
public class Vehiculo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 6, nullable = false, unique = true)
    private String placa;

    @Column(length = 30)
    private String marca;

    @Column(length = 30)
    private String modelo;

    @Column(length = 20)
    private String color;

    @Column(length = 3)
    private int contador;

    private boolean estado;

    @JsonIgnoreProperties("vehiculo")
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Registro> registros = new ArrayList<>();

    @JsonIgnoreProperties("vehiculo")
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Historial> historiales = new ArrayList<>();

    @JsonIgnoreProperties("vehiculo")
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingreso> ingresos = new ArrayList<>();
}
