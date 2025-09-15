package net.dekapanca.app.masjidku.repos;

import net.dekapanca.app.masjidku.domain.Dokumen;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DokumenRepository extends JpaRepository<Dokumen, Long> {

    Dokumen findFirstByMasjidIdNasional(String idNasional);

}
