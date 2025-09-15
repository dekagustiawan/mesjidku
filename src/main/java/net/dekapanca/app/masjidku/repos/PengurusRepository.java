package net.dekapanca.app.masjidku.repos;

import net.dekapanca.app.masjidku.domain.Pengurus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PengurusRepository extends JpaRepository<Pengurus, Long> {

    Pengurus findFirstByMasjidId(Long id);

}
