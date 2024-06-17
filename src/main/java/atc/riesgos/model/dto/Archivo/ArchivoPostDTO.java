package atc.riesgos.model.dto.Archivo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter

public class ArchivoPostDTO {
    private MultipartFile[] file;
    private Long eventoId;

    public ArchivoPostDTO(MultipartFile[] file, Long eventoId) {
        this.file = file;
        this.eventoId =eventoId;
    }

    public ArchivoPostDTO() {
    }
}