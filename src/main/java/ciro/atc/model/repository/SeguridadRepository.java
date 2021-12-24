package ciro.atc.model.repository;

import ciro.atc.model.entity.Seguridad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguridadRepository extends BaseRepository<Seguridad> {

    List<Seguridad> findAllByDeleted(Boolean deleted);
}