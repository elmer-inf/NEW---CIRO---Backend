package ciro.atc.model.repository;

import ciro.atc.model.entity.Observacion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservacionRepository extends BaseRepository<Observacion>{
    List<Observacion> findAllByDeleted(Boolean deleted);
}
