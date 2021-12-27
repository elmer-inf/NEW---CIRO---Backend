package atc.riesgos.model.repository;

import atc.riesgos.model.entity.MatrizOportunidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatrizOportunidadRepository extends BaseRepository<MatrizOportunidad> {

    List<MatrizOportunidad> findAllByDeleted(Boolean deleted);

    @Query(value = "SELECT COUNT(*) FROM MatrizOportunidad e WHERE e.codigo LIKE %?1%")
    Integer countMatrizOportunidadCodigo(String sigla);

    @Query(value = "SELECT MAX(e.idMacroCorrelativo) FROM MatrizOportunidad e WHERE e.codigo LIKE %?1%")
    Integer findUltimoIdMacro(String sigla);
}