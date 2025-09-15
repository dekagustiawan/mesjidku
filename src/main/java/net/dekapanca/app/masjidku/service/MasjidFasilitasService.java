package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.FasilitasTipe;
import net.dekapanca.app.masjidku.domain.Masjid;
import net.dekapanca.app.masjidku.domain.MasjidFasilitas;
import net.dekapanca.app.masjidku.events.BeforeDeleteFasilitasTipe;
import net.dekapanca.app.masjidku.events.BeforeDeleteMasjid;
import net.dekapanca.app.masjidku.model.MasjidFasilitasDTO;
import net.dekapanca.app.masjidku.repos.FasilitasTipeRepository;
import net.dekapanca.app.masjidku.repos.MasjidFasilitasRepository;
import net.dekapanca.app.masjidku.repos.MasjidRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import net.dekapanca.app.masjidku.util.ReferencedException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MasjidFasilitasService {

    private final MasjidFasilitasRepository masjidFasilitasRepository;
    private final MasjidRepository masjidRepository;
    private final FasilitasTipeRepository fasilitasTipeRepository;

    public MasjidFasilitasService(final MasjidFasilitasRepository masjidFasilitasRepository,
            final MasjidRepository masjidRepository,
            final FasilitasTipeRepository fasilitasTipeRepository) {
        this.masjidFasilitasRepository = masjidFasilitasRepository;
        this.masjidRepository = masjidRepository;
        this.fasilitasTipeRepository = fasilitasTipeRepository;
    }

    public List<MasjidFasilitasDTO> findAll() {
        final List<MasjidFasilitas> masjidFasilitases = masjidFasilitasRepository.findAll(Sort.by("id"));
        return masjidFasilitases.stream()
                .map(masjidFasilitas -> mapToDTO(masjidFasilitas, new MasjidFasilitasDTO()))
                .toList();
    }

    public MasjidFasilitasDTO get(final Long id) {
        return masjidFasilitasRepository.findById(id)
                .map(masjidFasilitas -> mapToDTO(masjidFasilitas, new MasjidFasilitasDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MasjidFasilitasDTO masjidFasilitasDTO) {
        final MasjidFasilitas masjidFasilitas = new MasjidFasilitas();
        mapToEntity(masjidFasilitasDTO, masjidFasilitas);
        return masjidFasilitasRepository.save(masjidFasilitas).getId();
    }

    public void update(final Long id, final MasjidFasilitasDTO masjidFasilitasDTO) {
        final MasjidFasilitas masjidFasilitas = masjidFasilitasRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(masjidFasilitasDTO, masjidFasilitas);
        masjidFasilitasRepository.save(masjidFasilitas);
    }

    public void delete(final Long id) {
        final MasjidFasilitas masjidFasilitas = masjidFasilitasRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        masjidFasilitasRepository.delete(masjidFasilitas);
    }

    private MasjidFasilitasDTO mapToDTO(final MasjidFasilitas masjidFasilitas,
            final MasjidFasilitasDTO masjidFasilitasDTO) {
        masjidFasilitasDTO.setId(masjidFasilitas.getId());
        masjidFasilitasDTO.setKeterangan(masjidFasilitas.getKeterangan());
        masjidFasilitasDTO.setMasjid(masjidFasilitas.getMasjid() == null ? null : masjidFasilitas.getMasjid().getId());
        masjidFasilitasDTO.setFasilitas(masjidFasilitas.getFasilitas() == null ? null : masjidFasilitas.getFasilitas().getId());
        return masjidFasilitasDTO;
    }

    private MasjidFasilitas mapToEntity(final MasjidFasilitasDTO masjidFasilitasDTO,
            final MasjidFasilitas masjidFasilitas) {
        masjidFasilitas.setKeterangan(masjidFasilitasDTO.getKeterangan());
        final Masjid masjid = masjidFasilitasDTO.getMasjid() == null ? null : masjidRepository.findById(masjidFasilitasDTO.getMasjid())
                .orElseThrow(() -> new NotFoundException("masjid not found"));
        masjidFasilitas.setMasjid(masjid);
        final FasilitasTipe fasilitas = masjidFasilitasDTO.getFasilitas() == null ? null : fasilitasTipeRepository.findById(masjidFasilitasDTO.getFasilitas())
                .orElseThrow(() -> new NotFoundException("fasilitas not found"));
        masjidFasilitas.setFasilitas(fasilitas);
        return masjidFasilitas;
    }

    @EventListener(BeforeDeleteMasjid.class)
    public void on(final BeforeDeleteMasjid event) {
        final ReferencedException referencedException = new ReferencedException();
        final MasjidFasilitas masjidMasjidFasilitas = masjidFasilitasRepository.findFirstByMasjidId(event.getId());
        if (masjidMasjidFasilitas != null) {
            referencedException.setKey("masjid.masjidFasilitas.masjid.referenced");
            referencedException.addParam(masjidMasjidFasilitas.getId());
            throw referencedException;
        }
    }

    @EventListener(BeforeDeleteFasilitasTipe.class)
    public void on(final BeforeDeleteFasilitasTipe event) {
        final ReferencedException referencedException = new ReferencedException();
        final MasjidFasilitas fasilitasMasjidFasilitas = masjidFasilitasRepository.findFirstByFasilitasId(event.getId());
        if (fasilitasMasjidFasilitas != null) {
            referencedException.setKey("fasilitasTipe.masjidFasilitas.fasilitas.referenced");
            referencedException.addParam(fasilitasMasjidFasilitas.getId());
            throw referencedException;
        }
    }

}
