package com.utc.sistema_tevcol.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "actividad_accion")
public class ActividadAccion {

    @Id
    @Column(name = "codigo_actividad")
    private Long codigoActividad;

    @Column(name = "numero_actividad")
    private Integer numeroActividad;

    @Column(name = "fecha_ini_actividad")
    private LocalDate fechaIniActividad;

    @Column(name = "fecha_max_actividad")
    private LocalDate fechaMaxActividad;

    @Column(name = "estado_actividad")
    private String estadoActividad = "ABIERTO";

    @Column(name = "fk_cod_accion", insertable = false, updatable = false)
    private Long fkCodAccion;

    @Column(name = "fecha_creado_actividad", updatable = false)
    private LocalDateTime fechaCreadoActividad;

    @Column(name = "fecha_editado_actividad")
    private LocalDateTime fechaEditadoActividad;

    @ManyToOne
    @JoinColumn(name = "fk_cod_accion", referencedColumnName = "codigo_accion")
    private AccionPlanAmbiental accion;

    public ActividadAccion() {
    }

    // Getters y Setters
}