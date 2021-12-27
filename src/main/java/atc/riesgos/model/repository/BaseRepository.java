package atc.riesgos.model.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository <T extends Object> extends JpaRepository<T, Long> {
    Optional<T> findByIdAndDeleted(Long id, boolean deleted);
}