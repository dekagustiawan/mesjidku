package net.dekapanca.app.masjidku.repos;

import net.dekapanca.app.masjidku.domain.Kegiatan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface KegiatanRepository extends JpaRepository<Kegiatan, Long> {

    Kegiatan findFirstByMasjidIdNasional(String idNasional);

}
