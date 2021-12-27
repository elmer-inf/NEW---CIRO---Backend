package atc.riesgos.model.dto.Archivo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter

public class ArchivoPostDTOv2 {
    private MultipartFile[] file;
    private Long eventoId;

    public ArchivoPostDTOv2(MultipartFile[] file,Long eventoId) {
        this.file = file;
        this.eventoId =eventoId;
    }

    public ArchivoPostDTOv2() {
    }
}
