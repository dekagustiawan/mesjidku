package net.dekapanca.app.masjidku.repos;

import net.dekapanca.app.masjidku.domain.Foto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FotoRepository extends JpaRepository<Foto, Long> {

    Foto findFirstByMasjidId(Long id);

}
