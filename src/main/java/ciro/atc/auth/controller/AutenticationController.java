package ciro.atc.auth.controller;

import ciro.atc.auth.http.Loginable;
import ciro.atc.auth.service.AutenticationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*", maxAge = 20000)
public class AutenticationController {

    @Autowired
    AutenticationService autenticationService;

    @PostMapping("/authentication")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody Loginable loginable, HttpServletRequest request) {
        return autenticationService.authentication(loginable, request);
    }

    @GetMapping("/menu/ui")
    public ResponseEntity<?> menuUI(HttpServletRequest request) {
         return autenticationService.getMenuUI(request);
    }

    @GetMapping("/menu/path")
    public ResponseEntity<?> menuPath(HttpServletRequest request) {

        return autenticationService.getMenuPath(request);
    }

}
