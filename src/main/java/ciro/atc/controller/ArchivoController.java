package ciro.atc.controller;

import ciro.atc.dao.service.ArchivoService;
import ciro.atc.dao.service.EventoRiesgoService;
import ciro.atc.model.dto.Archivo.ArchivoPostDTO;
import ciro.atc.model.dto.EventoRiesgo.EventoRiesgoPostDTO;
import ciro.atc.model.entity.Archivo;
import ciro.atc.model.entity.EventoRiesgo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/archivo")
@CrossOrigin(origins = "*", maxAge = 20000)

public class ArchivoController {

    @Autowired
    ArchivoService archivoService;

    @PostMapping("/registrar")
    public Archivo save (@Valid @RequestBody ArchivoPostDTO data){
        return archivoService.create(data);
    }
}
