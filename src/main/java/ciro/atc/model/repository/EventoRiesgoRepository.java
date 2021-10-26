package ciro.atc.model.repository;

import ciro.atc.model.entity.EventoRiesgo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoRiesgoRepository extends BaseRepository<EventoRiesgo> {

    List<EventoRiesgo> findAllByDeleted(Boolean deleted);
}