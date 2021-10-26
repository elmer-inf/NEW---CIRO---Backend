package ciro.atc.dao.service;

import ciro.atc.model.dto.Archivo.ArchivoPostDTO;
import ciro.atc.model.entity.Archivo;

public interface ArchivoService {

    Archivo create(ArchivoPostDTO t);
}
