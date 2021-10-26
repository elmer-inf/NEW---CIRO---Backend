package ciro.atc.model.repository;

import ciro.atc.model.entity.Archivo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArchivoRepository extends BaseRepository<Archivo> {

    List<Archivo> findAllByDeleted(Boolean deleted);
}