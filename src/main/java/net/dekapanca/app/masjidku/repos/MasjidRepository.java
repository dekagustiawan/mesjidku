package net.dekapanca.app.masjidku.repos;

import net.dekapanca.app.masjidku.domain.Masjid;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MasjidRepository extends JpaRepository<Masjid, Long> {
}
