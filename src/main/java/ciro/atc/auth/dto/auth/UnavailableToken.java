package ciro.atc.auth.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnavailableToken {

    private Boolean status;
    private String message;



    public UnavailableToken(Boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public UnavailableToken() {
    }
}
