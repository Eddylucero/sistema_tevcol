package com.utc.sistema_tevcol.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "actividad_accion")
public class ActividadAccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ← IMPORTANTE si es AUTO_INCREMENT
    @Column(name = "codigo_actividad")
    private Long codigoActividad;

    @Column(name = "numero_actividad")
    private Integer numeroActividad;

    @Column(name = "fecha_ini_actividad")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIniActividad;

    @Column(name = "fecha_max_actividad")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public ActividadAccion() {}

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

    // ===== GETTERS Y SETTERS =====

    public Long getCodigoActividad() {
        return codigoActividad;
    }

    public void setCodigoActividad(Long codigoActividad) {
        this.codigoActividad = codigoActividad;
    }

    public Integer getNumeroActividad() {
        return numeroActividad;
    }

    public void setNumeroActividad(Integer numeroActividad) {
        this.numeroActividad = numeroActividad;
    }

    public LocalDate getFechaIniActividad() {
        return fechaIniActividad;
    }

    public void setFechaIniActividad(LocalDate fechaIniActividad) {
        this.fechaIniActividad = fechaIniActividad;
    }

    public LocalDate getFechaMaxActividad() {
        return fechaMaxActividad;
    }

    public void setFechaMaxActividad(LocalDate fechaMaxActividad) {
        this.fechaMaxActividad = fechaMaxActividad;
    }

    public String getEstadoActividad() {
        return estadoActividad;
    }

    public void setEstadoActividad(String estadoActividad) {
        this.estadoActividad = estadoActividad;
    }

    public LocalDateTime getFechaCreadoActividad() {
        return fechaCreadoActividad;
    }

    public void setFechaCreadoActividad(LocalDateTime fechaCreadoActividad) {
        this.fechaCreadoActividad = fechaCreadoActividad;
    }

    public LocalDateTime getFechaEditadoActividad() {
        return fechaEditadoActividad;
    }

    public void setFechaEditadoActividad(LocalDateTime fechaEditadoActividad) {
        this.fechaEditadoActividad = fechaEditadoActividad;
    }

    public AccionPlanAmbiental getAccion() {
        return accion;
    }

    public void setAccion(AccionPlanAmbiental accion) {
        this.accion = accion;
    }
}