package ciro.atc.model.dto.TablaLista;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TablaListaGetDTO {

    private Long id;
    private String nombre_tabla;
    private int nivel2;
    private int nivel3;

}