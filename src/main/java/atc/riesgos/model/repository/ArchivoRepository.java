package atc.riesgos.model.repository;

import atc.riesgos.model.entity.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArchivoRepository extends JpaRepository<Archivo, Long> {

    List<Archivo> findAllByDeleted(Boolean deleted);
    List<Archivo> findByEventoId(Long idEvento);

    @Modifying
    @Query("UPDATE Archivo a SET a.deleted = true WHERE a.id IN :ids AND a.eventoId = :eventoId")
    int softDeleteByIdsAndEvento(@Param("ids") List<Long> ids, @Param("eventoId") Long eventoId);

    List<Archivo> findAllByEventoIdAndDeletedFalse(Long eventoId);

}