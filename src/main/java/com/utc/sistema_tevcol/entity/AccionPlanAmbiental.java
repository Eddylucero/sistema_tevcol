package com.utc.sistema_tevcol.entity;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "accion_plan_ambiental")
public class AccionPlanAmbiental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_accion")
    private Long codigoAccion;

    @Column(name = "aspecto_ambiental_accion", columnDefinition = "LONGTEXT")
    private String aspectoAmbientalAccion;

    @Column(name = "impacto_ambiental_accion", columnDefinition = "LONGTEXT")
    private String impactoAmbientalAccion;

    @Column(name = "medidas_propuestas_accion", columnDefinition = "LONGTEXT")
    private String medidasPropuestasAccion;

    @Column(name = "indicador_placeholder_accion", length = 100)
    private String indicadorPlaceholderAccion;

    @Column(name = "numerador_valor_accion")
    private Integer numeradorValorAccion;

    @Column(name = "denominador_valor_accion")
    private Integer denominadorValorAccion;

    @Column(name = "estado_aplica")
    private Integer estadoAplica;

    @Column(name = "color_accion", length = 25)
    private String colorAccion;

    @Column(name = "valor_accion", length = 25)
    private String valorAccion;

    @Column(name = "frecuencia_accion")
    private Integer frecuenciaAccion;

    @Column(name = "periodo_accion")
    private String periodoAccion;

    @Column(name = "fk_cod_seccion")
    private Long fkCodSeccion;

    @Column(name = "fecha_creado_accion", updatable = false)
    private LocalDateTime fechaCreadoAccion;

    @Column(name = "fecha_editado_accion")
    private LocalDateTime fechaEditadoAccion;

    @OneToMany(mappedBy = "accion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActividadAccion> actividades;

    public AccionPlanAmbiental() {}

    // =========================
    // PrePersist / PreUpdate
    // =========================
    @PrePersist
    protected void onCreate() {
        this.fechaCreadoAccion = LocalDateTime.now();
        if (this.estadoAplica == null) {
            this.estadoAplica = 1;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaEditadoAccion = LocalDateTime.now();
    }

    // =========================
    // Getters y Setters
    // =========================
    public Long getCodigoAccion() {
        return codigoAccion;
    }

    public void setCodigoAccion(Long codigoAccion) {
        this.codigoAccion = codigoAccion;
    }

    public String getAspectoAmbientalAccion() {
        return aspectoAmbientalAccion;
    }

    public void setAspectoAmbientalAccion(String aspectoAmbientalAccion) {
        this.aspectoAmbientalAccion = aspectoAmbientalAccion;
    }

    public String getImpactoAmbientalAccion() {
        return impactoAmbientalAccion;
    }

    public void setImpactoAmbientalAccion(String impactoAmbientalAccion) {
        this.impactoAmbientalAccion = impactoAmbientalAccion;
    }

    public String getMedidasPropuestasAccion() {
        return medidasPropuestasAccion;
    }

    public void setMedidasPropuestasAccion(String medidasPropuestasAccion) {
        this.medidasPropuestasAccion = medidasPropuestasAccion;
    }

    public String getIndicadorPlaceholderAccion() {
        return indicadorPlaceholderAccion;
    }

    public void setIndicadorPlaceholderAccion(String indicadorPlaceholderAccion) {
        this.indicadorPlaceholderAccion = indicadorPlaceholderAccion;
    }

    public Integer getNumeradorValorAccion() {
        return numeradorValorAccion;
    }

    public void setNumeradorValorAccion(Integer numeradorValorAccion) {
        this.numeradorValorAccion = numeradorValorAccion;
    }

    public Integer getDenominadorValorAccion() {
        return denominadorValorAccion;
    }

    public void setDenominadorValorAccion(Integer denominadorValorAccion) {
        this.denominadorValorAccion = denominadorValorAccion;
    }

    public Integer getEstadoAplica() {
        return estadoAplica;
    }

    public void setEstadoAplica(Integer estadoAplica) {
        this.estadoAplica = estadoAplica;
    }

    public String getColorAccion() {
        return colorAccion;
    }

    public void setColorAccion(String colorAccion) {
        this.colorAccion = colorAccion;
    }

    public String getValorAccion() {
        return valorAccion;
    }

    public void setValorAccion(String valorAccion) {
        this.valorAccion = valorAccion;
    }

    public Integer getFrecuenciaAccion() {
        return frecuenciaAccion;
    }

    public void setFrecuenciaAccion(Integer frecuenciaAccion) {
        this.frecuenciaAccion = frecuenciaAccion;
    }

    public String getPeriodoAccion() {
        return periodoAccion;
    }

    public void setPeriodoAccion(String periodoAccion) {
        this.periodoAccion = periodoAccion;
    }

    public Long getFkCodSeccion() {
        return fkCodSeccion;
    }

    public void setFkCodSeccion(Long fkCodSeccion) {
        this.fkCodSeccion = fkCodSeccion;
    }

    public LocalDateTime getFechaCreadoAccion() {
        return fechaCreadoAccion;
    }

    public void setFechaCreadoAccion(LocalDateTime fechaCreadoAccion) {
        this.fechaCreadoAccion = fechaCreadoAccion;
    }

    public LocalDateTime getFechaEditadoAccion() {
        return fechaEditadoAccion;
    }

    public void setFechaEditadoAccion(LocalDateTime fechaEditadoAccion) {
        this.fechaEditadoAccion = fechaEditadoAccion;
    }

    public List<ActividadAccion> getActividades() {
        return actividades;
    }

    public void setActividades(List<ActividadAccion> actividades) {
        this.actividades = actividades;
    }
}