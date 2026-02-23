package com.utc.sistema_tevcol.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "accion_plan_ambiental")
public class AccionPlanAmbiental {

    @Id
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
    private Integer estadoAplica = 1;

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

    @OneToMany(mappedBy = "accion", cascade = CascadeType.ALL)
    private List<ActividadAccion> actividades;

    public AccionPlanAmbiental() {
    }

    // Getters y Setters
}