package ciro.atc.dao.service;

import ciro.atc.model.dto.Archivo.ArchivoPostDTO;
import ciro.atc.model.dto.Archivo.ArchivoPostDTOv2;
import ciro.atc.model.entity.Archivo;

import java.util.List;

public interface ArchivoService {

    Archivo create(ArchivoPostDTO t);
    List<Archivo> create(ArchivoPostDTOv2 data);
}
