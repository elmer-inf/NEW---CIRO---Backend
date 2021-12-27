package ciro.atc.dao.service;

import ciro.atc.config.log.Log;
import ciro.atc.exception.BadRequestException;
import ciro.atc.exception.NotFoundException;
import ciro.atc.exception.NotImplementedException;
import ciro.atc.model.dto.Archivo.ArchivoPostDTO;
import ciro.atc.model.dto.Archivo.ArchivoPostDTOv2;
import ciro.atc.model.dto.TablaDescripcion.TablaDescripcionPostDTO;
import ciro.atc.model.entity.Archivo;
import ciro.atc.model.entity.EventoRiesgo;
import ciro.atc.model.repository.ArchivoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        //BeanUtils.copyProperties(data, archivo);
        // archivo.setEventoId(eventoRiesgo);

        return archivoRepository.save(archivo);
    }


    public List<Archivo> create(ArchivoPostDTOv2 data) {
        //Archivo archivo = new Archivo();
        // EventoRiesgo eventoRiesgo = eventoRiesgoService.findByIdEvento(data.getEventoId());
        //BeanUtils.copyProperties(data, archivo);
        // archivo.setEventoId(eventoRiesgo);

        // return archivoRepository.save(archivo);
        List<Archivo> archivos = new ArrayList<>();
        try {
            if (data.getFile() != null) {
                List<MultipartFile> fileList = Arrays.asList(data.getFile());

                if (fileList.size() <= fileLimit) {
                    // Regularizacion regularizacion = regularizacionRepository.findById(respaldoRegularizacionPostDto.getIdRegularizacion())
                    //.orElseThrow(() -> new NotFoundException("La regularizacion no existe"));
                    //int i = 0;
                    for (MultipartFile f : fileList) {
                        //String nameFile = StringUtils.cleanPath(f.getOriginalFilename());
                        //byte[] baseFile = f.getBytes();
                        //String type = f.getContentType();

                        Archivo archivo = new Archivo();
                        archivo.setNombreArchivo(StringUtils.cleanPath(f.getOriginalFilename()));
                        archivo.setSize(f.getSize());
                        archivo.setArchivoBase64(f.getBytes());
                        archivo.setTipo(f.getContentType());
                      // archivo.setEventoId(data.getEventoId());
                        archivoRepository.save(archivo);
                        archivos.add(archivo);
                        //respaldoRegularizacion.setNombreArchivoCorrelativo("Respaldo" + (i + 1));

                        //i++;
                    }
                } else {
                    Log.error("Superó el límite de archivos a cargar");
                    throw new BadRequestException("Superó el límite de archivos a cargar");
                }
            } else {
                Log.error("No se adjunto ningun archivo");

                throw new NullPointerException("No se adjunto ningun archivo ");
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
}
