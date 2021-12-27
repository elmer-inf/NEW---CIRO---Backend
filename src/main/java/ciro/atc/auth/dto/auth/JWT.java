package ciro.atc.auth.dto.auth;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWT {
    private String accessToken;

    private String tokenType;

   /* public JWT(String accessToken) {

        this.accessToken = accessToken;
    }*/
}
