package ciro.atc.model.repository;

import ciro.atc.model.entity.TablaLista;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TablaListaRepository extends BaseRepository<TablaLista> {

    List<TablaLista> findAllByDeleted(Boolean deleted);
}
