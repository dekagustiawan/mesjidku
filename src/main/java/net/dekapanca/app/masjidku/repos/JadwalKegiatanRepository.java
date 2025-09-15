package net.dekapanca.app.masjidku.repos;

import net.dekapanca.app.masjidku.domain.JadwalKegiatan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JadwalKegiatanRepository extends JpaRepository<JadwalKegiatan, Long> {

    JadwalKegiatan findFirstByKegiatanId(Long id);

}
