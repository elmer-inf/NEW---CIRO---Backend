package ciro.atc.auth.http;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter

public class Loginable implements Serializable {

    @NotNull(message = "El campo Usuario es requerido...")
    @Size(min = 4, max = 60)
    private String username;

    @NotNull(message = "El campo Password es requerido...")
    @Size(min = 4, max = 40)
    private String password;


}
