package atc.riesgos.auth.service;

import atc.riesgos.auth.http.Loginable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AutenticationService {
    ResponseEntity<?> authentication(Loginable data, HttpServletRequest request);

    ResponseEntity<?> getMenuUI(HttpServletRequest request);

    ResponseEntity<?> getMenuPath(HttpServletRequest request);

    Boolean askAvailable(HttpServletRequest request);
}
