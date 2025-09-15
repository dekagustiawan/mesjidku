package net.dekapanca.app.masjidku.repos;

import net.dekapanca.app.masjidku.domain.Alamat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AlamatRepository extends JpaRepository<Alamat, Long> {

    Alamat findFirstByMasjidId(Long id);

}
