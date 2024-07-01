package atc.riesgos.dao.service;

import atc.riesgos.exception.BadRequestException;
import atc.riesgos.exception.NotFoundException;
import atc.riesgos.exception.NotImplementedException;
import atc.riesgos.model.dto.ArchivoEveRecurrente.ArchivoEveRecurrentePostDTO;
import atc.riesgos.model.entity.ArchivoEveRecurrente;
import atc.riesgos.model.repository.ArchivoEveRecurrenteRepository;
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
public class ArchivoEveRecurrenteServiceImpl implements ArchivoEveRecurrenteService {

    @Autowired
    ArchivoEveRecurrenteRepository archivoRepository;

    @Autowired
    EventoRiesgoService eventoRiesgoService;

    @Value("${fileEventoRecurrente.limit}")
    private int fileLimit;

    public Boolean isEmptyList(MultipartFile[] data) {
        MultipartFile[] fileList0 = data;
        Boolean empty = false;

        for (MultipartFile multipartFile : fileList0) {
            Boolean x = multipartFile.isEmpty();
            return x;
        }
        return empty;
    }

    public List<ArchivoEveRecurrente> create(ArchivoEveRecurrentePostDTO data) {
        List<ArchivoEveRecurrente> archivos = new ArrayList<>();
        try {
            if (data.getFile() != null && !isEmptyList(data.getFile())) {
                List<MultipartFile> fileList = Arrays.asList(data.getFile());

                if (fileList.size() <= fileLimit) {
                    for (MultipartFile f : fileList) {
                        ArchivoEveRecurrente archivo = new ArchivoEveRecurrente();
                        archivo.setNombreArchivo(StringUtils.cleanPath(f.getOriginalFilename()));
                        archivo.setSize(f.getSize());
                        archivo.setArchivoBase64(f.getBytes());
                        archivo.setTipo(f.getContentType());
                        archivo.setEventoId(data.getEventoId());
                        archivoRepository.save(archivo);
                        archivos.add(archivo);
                    }
                } else {

                    throw new BadRequestException("Superó el límite de archivos a cargar");
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        } catch (BadRequestException e) {
            throw new BadRequestException("Error al intentar guardar un archivo: " + e);
        } catch (NotFoundException e) {
            throw new NotFoundException("Error al intentar guardar un archivo: ", e);
        } catch (Exception e) {
            throw new NotImplementedException("Error al intentar guardar un archivo: " + e);
        }
        return archivos;
    }

    public List<ArchivoEveRecurrente> findAllByEvento(Long id) {
        List<ArchivoEveRecurrente> archivos = new ArrayList<>();
        try {
            archivos = archivoRepository.findByEventoId(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return archivos;
    }

    public ArchivoEveRecurrente deleteByIdArchivo(Long id) {
        Optional<ArchivoEveRecurrente> founded = archivoRepository.findById(id);
        ArchivoEveRecurrente archivo = founded.get();
        archivo.setDeleted(true);

        ArchivoEveRecurrente archDel = archivoRepository.save(archivo);
        return archDel;
    }

}