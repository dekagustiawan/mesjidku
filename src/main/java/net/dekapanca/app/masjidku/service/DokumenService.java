package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.Dokumen;
import net.dekapanca.app.masjidku.domain.Masjid;
import net.dekapanca.app.masjidku.events.BeforeDeleteMasjid;
import net.dekapanca.app.masjidku.model.DokumenDTO;
import net.dekapanca.app.masjidku.repos.DokumenRepository;
import net.dekapanca.app.masjidku.repos.MasjidRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import net.dekapanca.app.masjidku.util.ReferencedException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class DokumenService {

    private final DokumenRepository dokumenRepository;
    private final MasjidRepository masjidRepository;

    public DokumenService(final DokumenRepository dokumenRepository,
            final MasjidRepository masjidRepository) {
        this.dokumenRepository = dokumenRepository;
        this.masjidRepository = masjidRepository;
    }

    public List<DokumenDTO> findAll() {
        final List<Dokumen> dokumens = dokumenRepository.findAll(Sort.by("id"));
        return dokumens.stream()
                .map(dokumen -> mapToDTO(dokumen, new DokumenDTO()))
                .toList();
    }

    public DokumenDTO get(final Long id) {
        return dokumenRepository.findById(id)
                .map(dokumen -> mapToDTO(dokumen, new DokumenDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final DokumenDTO dokumenDTO) {
        final Dokumen dokumen = new Dokumen();
        mapToEntity(dokumenDTO, dokumen);
        return dokumenRepository.save(dokumen).getId();
    }

    public void update(final Long id, final DokumenDTO dokumenDTO) {
        final Dokumen dokumen = dokumenRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(dokumenDTO, dokumen);
        dokumenRepository.save(dokumen);
    }

    public void delete(final Long id) {
        final Dokumen dokumen = dokumenRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        dokumenRepository.delete(dokumen);
    }

    private DokumenDTO mapToDTO(final Dokumen dokumen, final DokumenDTO dokumenDTO) {
        dokumenDTO.setId(dokumen.getId());
        dokumenDTO.setJenis(dokumen.getJenis());
        dokumenDTO.setNomor(dokumen.getNomor());
        dokumenDTO.setUrl(dokumen.getUrl());
        dokumenDTO.setTanggalDok(dokumen.getTanggalDok());
        dokumenDTO.setMasjid(dokumen.getMasjid() == null ? null : dokumen.getMasjid().getIdNasional());
        return dokumenDTO;
    }

    private Dokumen mapToEntity(final DokumenDTO dokumenDTO, final Dokumen dokumen) {
        dokumen.setJenis(dokumenDTO.getJenis());
        dokumen.setNomor(dokumenDTO.getNomor());
        dokumen.setUrl(dokumenDTO.getUrl());
        dokumen.setTanggalDok(dokumenDTO.getTanggalDok());
        final Masjid masjid = dokumenDTO.getMasjid() == null ? null : masjidRepository.findById(dokumenDTO.getMasjid())
                .orElseThrow(() -> new NotFoundException("masjid not found"));
        dokumen.setMasjid(masjid);
        return dokumen;
    }

    @EventListener(BeforeDeleteMasjid.class)
    public void on(final BeforeDeleteMasjid event) {
        final ReferencedException referencedException = new ReferencedException();
        final Dokumen masjidDokumen = dokumenRepository.findFirstByMasjidIdNasional(event.getIdNasional());
        if (masjidDokumen != null) {
            referencedException.setKey("masjid.dokumen.masjid.referenced");
            referencedException.addParam(masjidDokumen.getId());
            throw referencedException;
        }
    }

}
