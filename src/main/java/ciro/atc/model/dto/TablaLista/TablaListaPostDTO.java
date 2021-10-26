package ciro.atc.model.dto.TablaLista;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter

public class TablaListaPostDTO {

    private String nombre_tabla;
    private int nivel2;
    private int nivel3;


}
