package atc.riesgos.auth.http;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class Loginable implements Serializable {

    @NotNull(message = "El campo Usuario es requerido...")
    @Size(min = 4, max = 60)
    private String username;

    @NotNull(message = "El campo Password es requerido...")
    @Size(min = 4, max = 40)
    private String password;


}
