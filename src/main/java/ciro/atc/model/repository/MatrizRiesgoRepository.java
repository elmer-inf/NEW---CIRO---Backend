package ciro.atc.model.repository;

import ciro.atc.model.entity.MatrizRiesgo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatrizRiesgoRepository extends BaseRepository<MatrizRiesgo> {

    List<MatrizRiesgo> findAllByDeleted(Boolean deleted);

    @Query(value = "SELECT COUNT(*) FROM MatrizRiesgo e WHERE e.codigo LIKE ?1%")
    Integer countMatrizRiesgoCodigo(String sigla);

    @Query(value = "SELECT MAX(e.idUnidadCorrelativo) FROM MatrizRiesgo e WHERE e.codigo LIKE ?1%")
    Integer findUltimoIdCorrelativoUnidad(String sigla);
}