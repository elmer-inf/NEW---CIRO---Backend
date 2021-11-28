package ciro.atc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "tbl_tabla_descripcion")

public class TablaDescripcion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "des_id")
    private Long id;

    @Column(name = "des_clave", length = 50)
    private String clave;

    @Column(name = "des_nombre", length = 1000)
    private String nombre;

    @Column(name = "des_descripcion", length = 1000)
    private String descripcion;

    @Column(name = "des_campo_a", length = 1000)
    private String campoA;

    @Column(name = "des_campo_b", length = 1000)
    private String campoB;

    @Column(name = "des_campo_c", length = 1000)
    private String campoC;

    @Column(name = "des_campo_d", length = 1000)
    private String campoD;

    /* -------- RELACION DE PARAMETROS --------- */
    @JsonIgnore
    @OneToMany(mappedBy = "agenciaId")
    private List<EventoRiesgo> eventoRiesgo1;

    @JsonIgnore
    @OneToMany(mappedBy = "ciudadId")
    private List<EventoRiesgo> eventoRiesgo2;

    @JsonIgnore
    @OneToMany(mappedBy = "areaID")
    private List<EventoRiesgo> eventoRiesgo3;

    @JsonIgnore
    @OneToMany(mappedBy = "unidadId")
    private List<EventoRiesgo> eventoRiesgo4;

    @JsonIgnore
    @OneToMany(mappedBy = "entidadId")
    private List<EventoRiesgo> eventoRiesgo5;

    @JsonIgnore
    @OneToMany(mappedBy = "cargoId")
    private List<EventoRiesgo> eventoRiesgo6;

    @JsonIgnore
    @OneToMany(mappedBy = "fuenteInfId")
    private List<EventoRiesgo> eventoRiesgo7;

    @JsonIgnore
    @OneToMany(mappedBy = "canalAsfiId")
    private List<EventoRiesgo> eventoRiesgo8;

    @JsonIgnore
    @OneToMany(mappedBy = "subcategorizacionId")
    private List<EventoRiesgo> eventoRiesgo9;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoEventoPerdidaId")
    private List<EventoRiesgo> eventoRiesgo10;

    @JsonIgnore
    @OneToMany(mappedBy = "subEventoId")
    private List<EventoRiesgo> eventoRiesgo11;

    @JsonIgnore
    @OneToMany(mappedBy = "claseEventoId")
    private List<EventoRiesgo> eventoRiesgo12;

    @JsonIgnore
    @OneToMany(mappedBy = "factorRiesgoId")
    private List<EventoRiesgo> eventoRiesgo13;

    @JsonIgnore
    @OneToMany(mappedBy = "procesoId")
    private List<EventoRiesgo> eventoRiesgo14;

    @JsonIgnore
    @OneToMany(mappedBy = "procedimientoId")
    private List<EventoRiesgo> eventoRiesgo15;

    @JsonIgnore
    @OneToMany(mappedBy = "lineaAsfiId")
    private List<EventoRiesgo> eventoRiesgo16;

    @JsonIgnore
    @OneToMany(mappedBy = "operacionId")
    private List<EventoRiesgo> eventoRiesgo17;

    @JsonIgnore
    @OneToMany(mappedBy = "efectoPerdidaId")
    private List<EventoRiesgo> eventoRiesgo18;

    @JsonIgnore
    @OneToMany(mappedBy = "opeProSerId")
    private List<EventoRiesgo> eventoRiesgo19;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoServicioId")
    private List<EventoRiesgo> eventoRiesgo20;

    @JsonIgnore
    @OneToMany(mappedBy = "descServicioId")
    private List<EventoRiesgo> eventoRiesgo21;

    @JsonIgnore
    @OneToMany(mappedBy = "tasaCambioId")
    private List<EventoRiesgo> eventoRiesgo22;

    @JsonIgnore
    @OneToMany(mappedBy = "monedaId")
    private List<EventoRiesgo> eventoRiesgo23;

    @JsonIgnore
    @OneToMany(mappedBy = "impactoId")
    private List<EventoRiesgo> eventoRiesgo24;

    @JsonIgnore
    @OneToMany(mappedBy = "polizaSeguroId")
    private List<EventoRiesgo> eventoRiesgo25;


    @JsonIgnore
    @OneToMany(mappedBy = "operativoId")
    private List<EventoRiesgo> eventoRiesgo26;

    @JsonIgnore
    @OneToMany(mappedBy = "liquidezId")
    private List<EventoRiesgo> eventoRiesgo27;

    @JsonIgnore
    @OneToMany(mappedBy = "fraudeId")
    private List<EventoRiesgo> eventoRiesgo28;

    @JsonIgnore
    @OneToMany(mappedBy = "legalId")
    private List<EventoRiesgo> eventoRiesgo29;

    @JsonIgnore
    @OneToMany(mappedBy = "reputacionalId")
    private List<EventoRiesgo> eventoRiesgo30;

    @JsonIgnore
    @OneToMany(mappedBy = "cumplimientoId")
    private List<EventoRiesgo> eventoRiesgo31;

    @JsonIgnore
    @OneToMany(mappedBy = "estrategicoId")
    private List<EventoRiesgo> eventoRiesgo32;

    @JsonIgnore
    @OneToMany(mappedBy = "gobiernoId")
    private List<EventoRiesgo> eventoRiesgo33;

    /*@JsonIgnore
    @OneToMany(mappedBy = "seguridadId")
    private List<EventoRiesgo> eventoRiesgo34;

    @JsonIgnore
    @OneToMany(mappedBy = "lgiId")
    private List<EventoRiesgo> eventoRiesgo35;*/

    /* -------- FIN RELACION DE PARAMETROS  --------- */


    @Column(name = "des_usuario_id")
    private int usuario_id;

    @ManyToOne
    @JoinColumn(name = "des_tabla_id")
    private TablaLista tablaLista;

    @Column(name = "des_nivel2_id")
    private int nivel2_id;

    @Column(name = "des_nivel3_id")
    private int nivel3_id;

    /*@ManyToOne(fetch = FetchType.LAZY, optional=true)
    @JoinTable(
            name = "tbl_lista_descripcion",
            joinColumns = @JoinColumn(name = "descripcion_id"),
            inverseJoinColumns = @JoinColumn(name = "lista_id")
    )
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private TablaLista tablaLista;*/

    @CreationTimestamp
    @Column(name = "des_dateTimeCreate")
    private Timestamp created;

    @UpdateTimestamp
    @Column(name = "des_dateTimeUpdate")
    private Timestamp updated;

    @Column(name = "des_delete")
    private boolean deleted;

    final public TablaDescripcion deleteOnly() {
        this.deleted = true;
        return this;
    }


}
