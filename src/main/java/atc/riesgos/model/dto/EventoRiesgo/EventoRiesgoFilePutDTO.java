package atc.riesgos.model.dto.EventoRiesgo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
public class EventoRiesgoFilePutDTO {
    private String eventoRiesgoPutDTO;
    private MultipartFile[] file;
    private String filesToDelete;
}