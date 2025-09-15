package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.Masjid;
import net.dekapanca.app.masjidku.domain.Pengurus;
import net.dekapanca.app.masjidku.events.BeforeDeleteMasjid;
import net.dekapanca.app.masjidku.model.PengurusDTO;
import net.dekapanca.app.masjidku.repos.MasjidRepository;
import net.dekapanca.app.masjidku.repos.PengurusRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import net.dekapanca.app.masjidku.util.ReferencedException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PengurusService {

    private final PengurusRepository pengurusRepository;
    private final MasjidRepository masjidRepository;

    public PengurusService(final PengurusRepository pengurusRepository,
            final MasjidRepository masjidRepository) {
        this.pengurusRepository = pengurusRepository;
        this.masjidRepository = masjidRepository;
    }

    public List<PengurusDTO> findAll() {
        final List<Pengurus> penguruses = pengurusRepository.findAll(Sort.by("id"));
        return penguruses.stream()
                .map(pengurus -> mapToDTO(pengurus, new PengurusDTO()))
                .toList();
    }

    public PengurusDTO get(final Long id) {
        return pengurusRepository.findById(id)
                .map(pengurus -> mapToDTO(pengurus, new PengurusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PengurusDTO pengurusDTO) {
        final Pengurus pengurus = new Pengurus();
        mapToEntity(pengurusDTO, pengurus);
        return pengurusRepository.save(pengurus).getId();
    }

    public void update(final Long id, final PengurusDTO pengurusDTO) {
        final Pengurus pengurus = pengurusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(pengurusDTO, pengurus);
        pengurusRepository.save(pengurus);
    }

    public void delete(final Long id) {
        final Pengurus pengurus = pengurusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        pengurusRepository.delete(pengurus);
    }

    private PengurusDTO mapToDTO(final Pengurus pengurus, final PengurusDTO pengurusDTO) {
        pengurusDTO.setId(pengurus.getId());
        pengurusDTO.setRole(pengurus.getRole());
        pengurusDTO.setNama(pengurus.getNama());
        pengurusDTO.setTelepon(pengurus.getTelepon());
        pengurusDTO.setEmail(pengurus.getEmail());
        pengurusDTO.setMasjid(pengurus.getMasjid() == null ? null : pengurus.getMasjid().getIdNasional());
        return pengurusDTO;
    }

    private Pengurus mapToEntity(final PengurusDTO pengurusDTO, final Pengurus pengurus) {
        pengurus.setRole(pengurusDTO.getRole());
        pengurus.setNama(pengurusDTO.getNama());
        pengurus.setTelepon(pengurusDTO.getTelepon());
        pengurus.setEmail(pengurusDTO.getEmail());
        final Masjid masjid = pengurusDTO.getMasjid() == null ? null : masjidRepository.findById(pengurusDTO.getMasjid())
                .orElseThrow(() -> new NotFoundException("masjid not found"));
        pengurus.setMasjid(masjid);
        return pengurus;
    }

    @EventListener(BeforeDeleteMasjid.class)
    public void on(final BeforeDeleteMasjid event) {
        final ReferencedException referencedException = new ReferencedException();
        final Pengurus masjidPengurus = pengurusRepository.findFirstByMasjidIdNasional(event.getIdNasional());
        if (masjidPengurus != null) {
            referencedException.setKey("masjid.pengurus.masjid.referenced");
            referencedException.addParam(masjidPengurus.getId());
            throw referencedException;
        }
    }

}
