package atc.riesgos.model.dto.ArchivoEveRecurrente;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter

public class ArchivoEveRecurrentePostDTO {
    private MultipartFile[] file;
    private Long eventoId;

    public ArchivoEveRecurrentePostDTO(MultipartFile[] file, Long eventoId) {
        this.file = file;
        this.eventoId =eventoId;
    }

    public ArchivoEveRecurrentePostDTO() {
    }
}