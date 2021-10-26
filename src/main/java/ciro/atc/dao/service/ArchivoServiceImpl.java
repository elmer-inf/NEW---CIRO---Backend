package ciro.atc.dao.service;

import ciro.atc.model.dto.Archivo.ArchivoPostDTO;

import ciro.atc.model.entity.Archivo;
import ciro.atc.model.repository.ArchivoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArchivoServiceImpl implements ArchivoService{

    @Autowired
    ArchivoRepository archivoRepository;

    public Archivo create (ArchivoPostDTO data){
        Archivo archivo = new Archivo();
        BeanUtils.copyProperties(data, archivo);
        return archivoRepository.save(archivo);
    }
}
