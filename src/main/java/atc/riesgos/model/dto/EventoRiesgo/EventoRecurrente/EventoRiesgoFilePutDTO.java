package atc.riesgos.model.dto.EventoRiesgo.EventoRecurrente;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
public class EventoRiesgoFilePutDTO {

    private String eventoRiesgoPutDTOrecurrente;
    private MultipartFile[] file;
    //private String filesToDelete;
}