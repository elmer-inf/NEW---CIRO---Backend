package atc.riesgos.model.repository;

import atc.riesgos.model.entity.MatrizRiesgo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatrizRiesgoRepository extends BaseRepository<MatrizRiesgo> {

    List<MatrizRiesgo> findAllByDeleted(Boolean deleted);

    @Query(nativeQuery=true, value = "SELECT COUNT(*) FROM riesgos.tbl_matriz_riesgo WHERE rie_codigo LIKE 'RO-' || ?1 || '\\_%' ESCAPE '\\'")
    Integer countMatrizRiesgoCodigo(String sigla);

    @Query(nativeQuery=true, value = "SELECT MAX(rie_id_unidad_correlativo) FROM riesgos.tbl_matriz_riesgo WHERE rie_codigo LIKE 'RO-' || ?1 || '\\_%' ESCAPE '\\'")
    Integer findUltimoIdUnidad(String sigla);


    @Query(value="Select c from MatrizRiesgo c where c.deleted is false and c.id IN (?1) ")
    List<MatrizRiesgo> matrizInById(List<Long> in);

}