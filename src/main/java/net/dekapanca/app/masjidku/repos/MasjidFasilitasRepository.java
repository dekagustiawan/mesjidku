package net.dekapanca.app.masjidku.repos;

import net.dekapanca.app.masjidku.domain.MasjidFasilitas;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MasjidFasilitasRepository extends JpaRepository<MasjidFasilitas, Long> {

    MasjidFasilitas findFirstByMasjidIdNasional(String idNasional);

    MasjidFasilitas findFirstByFasilitasId(Long id);

}
