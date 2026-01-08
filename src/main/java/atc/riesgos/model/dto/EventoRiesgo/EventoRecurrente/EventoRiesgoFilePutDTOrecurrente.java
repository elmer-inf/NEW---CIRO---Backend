package atc.riesgos.model.dto.EventoRiesgo.EventoRecurrente;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EventoRiesgoFilePutDTOrecurrente {

    private String eventoRiesgoPutDTOrecurrente;
    private MultipartFile[] file;

}