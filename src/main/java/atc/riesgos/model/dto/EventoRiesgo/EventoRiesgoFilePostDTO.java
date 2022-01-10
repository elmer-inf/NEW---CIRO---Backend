package atc.riesgos.model.dto.EventoRiesgo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
public class EventoRiesgoFilePostDTO {

    private String eventoRiesgoPostDTO;

    // Upload N files
    private MultipartFile[] file;


}
