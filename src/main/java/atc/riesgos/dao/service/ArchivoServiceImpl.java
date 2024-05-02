package atc.riesgos.dao.service;

import atc.riesgos.config.log.Log;
import atc.riesgos.exception.BadRequestException;
import atc.riesgos.exception.NotFoundException;
import atc.riesgos.exception.NotImplementedException;
import atc.riesgos.model.dto.Archivo.ArchivoPostDTO;
import atc.riesgos.model.dto.Archivo.ArchivoPostDTOv2;
import atc.riesgos.model.entity.Archivo;
import atc.riesgos.model.entity.EventoRiesgo;
import atc.riesgos.model.repository.ArchivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    @Autowired
    ArchivoRepository archivoRepository;

    @Autowired
    EventoRiesgoService eventoRiesgoService;

    @Value("${file.limit}")
    private int fileLimit;


    public Archivo create(ArchivoPostDTO data) {
        Archivo archivo = new Archivo();
        EventoRiesgo eventoRiesgo = eventoRiesgoService.findByIdEvento(data.getEventoId());
        return archivoRepository.save(archivo);
    }

    public Boolean isEmptyList(MultipartFile[] data) {
        MultipartFile[] fileList0 = data;
        Boolean empty = false;

        for (MultipartFile multipartFile : fileList0) {
           Boolean x = multipartFile.isEmpty();
           return x;
        }
        return empty;
    }

    public List<Archivo> create(ArchivoPostDTOv2 data) {
        List<Archivo> archivos = new ArrayList<>();
        try {
            if (data.getFile() != null && !isEmptyList(data.getFile())) {
                List<MultipartFile> fileList = Arrays.asList(data.getFile());

                if (fileList.size() <= fileLimit) {
                    for (MultipartFile f : fileList) {
                        Archivo archivo = new Archivo();
                        archivo.setNombreArchivo(StringUtils.cleanPath(f.getOriginalFilename()));
                        archivo.setSize(f.getSize());
                        archivo.setArchivoBase64(f.getBytes());
                        archivo.setTipo(f.getContentType());
                        archivo.setEventoId(data.getEventoId());
                        archivoRepository.save(archivo);
                        archivos.add(archivo);
                    }
                } else {
                    Log.error("Superó el límite de archivos a cargar");
                    throw new BadRequestException("Superó el límite de archivos a cargar");
                }
            }
        } catch (NullPointerException e) {
            Log.error("Error al intentar guardar un archivo: ", e);
        } catch (BadRequestException e) {
            Log.error("Error al intentar guardar un archivo: ", e);
            throw new BadRequestException("Error al intentar guardar un archivo: " + e);
        } catch (NotFoundException e) {
            Log.error("Error al intentar guardar un archivo: ", e);
            throw new NotFoundException("Error al intentar guardar un archivo: ", e);
        } catch (Exception e) {
            Log.error("Error al intentar guardar un archivo: ", e);
            throw new NotImplementedException("Error al intentar guardar un archivo: " + e);
        }
        return archivos;
    }

    public List<Archivo> findAllByEvento(Long id) {
        List<Archivo> archivos = new ArrayList<>();
        try {
            archivos = archivoRepository.findByEventoId(id);
        } catch (Exception e) {
            Log.error("findAllByEvento => ", e);
        }
        return archivos;
    }

    public Archivo deleteByIdArchivo(Long id) {
        Optional<Archivo> founded = archivoRepository.findById(id);
        Archivo archivo = founded.get();
        archivo.setDeleted(true);

        Archivo archDel = archivoRepository.save(archivo);
        return archDel;
    }

}
