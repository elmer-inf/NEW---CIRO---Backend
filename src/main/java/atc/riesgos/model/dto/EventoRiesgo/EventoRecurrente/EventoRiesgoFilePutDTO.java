package atc.riesgos.model.dto.EventoRiesgo.EventoRecurrente;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EventoRiesgoFilePutDTO   {

    private String eventoRiesgoPutDTOrecurrente;
    private MultipartFile[] file;

}