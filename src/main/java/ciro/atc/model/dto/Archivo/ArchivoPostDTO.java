package ciro.atc.model.dto.Archivo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class ArchivoPostDTO {

    private String archivo;
    private Long eventoId;
    private int usuarioId;

}
