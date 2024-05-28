package atc.riesgos.model.dto.email;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class EmailRequest {
    private String to;
    private String userName;

}
