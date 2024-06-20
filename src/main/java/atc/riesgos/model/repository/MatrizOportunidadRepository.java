package atc.riesgos.model.repository;

import atc.riesgos.model.entity.MatrizOportunidad;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatrizOportunidadRepository extends BaseRepository<MatrizOportunidad> {

    List<MatrizOportunidad> findAllByDeleted(Boolean deleted);

    @Query(nativeQuery=true, value = "SELECT COUNT(*) FROM riesgos.tbl_matriz_oportunidad e WHERE opo_codigo LIKE 'OP-' || ?1 || '\\_%' ESCAPE '\\'")
    Integer countMatrizOportunidadCodigo(String sigla);

    @Query(nativeQuery=true, value = "SELECT MAX(opo_id_macro_correlativo) FROM riesgos.tbl_matriz_oportunidad e WHERE opo_codigo LIKE 'OP-' || ?1 || '\\_%' ESCAPE '\\'")
    Integer findUltimoIdMacro(String sigla);
}