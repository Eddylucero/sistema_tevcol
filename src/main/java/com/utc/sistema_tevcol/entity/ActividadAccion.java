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
    private String estadoActividad;

    @Column(name = "fecha_creado_actividad", updatable = false)
    private LocalDateTime fechaCreadoActividad;

    @Column(name = "fecha_editado_actividad")
    private LocalDateTime fechaEditadoActividad;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_cod_accion", referencedColumnName = "codigo_accion")
    private AccionPlanAmbiental accion;

    public ActividadAccion() {
    }

    @PrePersist
    protected void onCreate() {
        this.fechaCreadoActividad = LocalDateTime.now();
        if (this.estadoActividad == null) {
            this.estadoActividad = "ABIERTO";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaEditadoActividad = LocalDateTime.now();
    }

    // Getters y Setters
}