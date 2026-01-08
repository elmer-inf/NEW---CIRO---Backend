package atc.riesgos.model.dto.EventoRiesgo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class EventoRiesgoFilePutDTO {
    private String eventoRiesgoPutDTO; // JSON string
    private MultipartFile[] file;      // archivos nuevos
    private String filesToDelete;      // "1,2,3"
}
