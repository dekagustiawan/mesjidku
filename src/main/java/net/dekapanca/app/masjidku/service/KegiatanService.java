package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.Kegiatan;
import net.dekapanca.app.masjidku.domain.Masjid;
import net.dekapanca.app.masjidku.events.BeforeDeleteKegiatan;
import net.dekapanca.app.masjidku.events.BeforeDeleteMasjid;
import net.dekapanca.app.masjidku.model.KegiatanDTO;
import net.dekapanca.app.masjidku.repos.KegiatanRepository;
import net.dekapanca.app.masjidku.repos.MasjidRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import net.dekapanca.app.masjidku.util.ReferencedException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class KegiatanService {

    private final KegiatanRepository kegiatanRepository;
    private final MasjidRepository masjidRepository;
    private final ApplicationEventPublisher publisher;

    public KegiatanService(final KegiatanRepository kegiatanRepository,
            final MasjidRepository masjidRepository, final ApplicationEventPublisher publisher) {
        this.kegiatanRepository = kegiatanRepository;
        this.masjidRepository = masjidRepository;
        this.publisher = publisher;
    }

    public List<KegiatanDTO> findAll() {
        final List<Kegiatan> kegiatans = kegiatanRepository.findAll(Sort.by("id"));
        return kegiatans.stream()
                .map(kegiatan -> mapToDTO(kegiatan, new KegiatanDTO()))
                .toList();
    }

    public KegiatanDTO get(final Long id) {
        return kegiatanRepository.findById(id)
                .map(kegiatan -> mapToDTO(kegiatan, new KegiatanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final KegiatanDTO kegiatanDTO) {
        final Kegiatan kegiatan = new Kegiatan();
        mapToEntity(kegiatanDTO, kegiatan);
        return kegiatanRepository.save(kegiatan).getId();
    }

    public void update(final Long id, final KegiatanDTO kegiatanDTO) {
        final Kegiatan kegiatan = kegiatanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(kegiatanDTO, kegiatan);
        kegiatanRepository.save(kegiatan);
    }

    public void delete(final Long id) {
        final Kegiatan kegiatan = kegiatanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteKegiatan(id));
        kegiatanRepository.delete(kegiatan);
    }

    private KegiatanDTO mapToDTO(final Kegiatan kegiatan, final KegiatanDTO kegiatanDTO) {
        kegiatanDTO.setId(kegiatan.getId());
        kegiatanDTO.setNama(kegiatan.getNama());
        kegiatanDTO.setPenyelenggara(kegiatan.getPenyelenggara());
        kegiatanDTO.setDeskripsi(kegiatan.getDeskripsi());
        kegiatanDTO.setMasjid(kegiatan.getMasjid() == null ? null : kegiatan.getMasjid().getId());
        return kegiatanDTO;
    }

    private Kegiatan mapToEntity(final KegiatanDTO kegiatanDTO, final Kegiatan kegiatan) {
        kegiatan.setNama(kegiatanDTO.getNama());
        kegiatan.setPenyelenggara(kegiatanDTO.getPenyelenggara());
        kegiatan.setDeskripsi(kegiatanDTO.getDeskripsi());
        final Masjid masjid = kegiatanDTO.getMasjid() == null ? null : masjidRepository.findById(kegiatanDTO.getMasjid())
                .orElseThrow(() -> new NotFoundException("masjid not found"));
        kegiatan.setMasjid(masjid);
        return kegiatan;
    }

    @EventListener(BeforeDeleteMasjid.class)
    public void on(final BeforeDeleteMasjid event) {
        final ReferencedException referencedException = new ReferencedException();
        final Kegiatan masjidKegiatan = kegiatanRepository.findFirstByMasjidId(event.getId());
        if (masjidKegiatan != null) {
            referencedException.setKey("masjid.kegiatan.masjid.referenced");
            referencedException.addParam(masjidKegiatan.getId());
            throw referencedException;
        }
    }

}
