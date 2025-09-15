package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.JadwalKegiatan;
import net.dekapanca.app.masjidku.domain.Kegiatan;
import net.dekapanca.app.masjidku.events.BeforeDeleteKegiatan;
import net.dekapanca.app.masjidku.model.JadwalKegiatanDTO;
import net.dekapanca.app.masjidku.repos.JadwalKegiatanRepository;
import net.dekapanca.app.masjidku.repos.KegiatanRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import net.dekapanca.app.masjidku.util.ReferencedException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class JadwalKegiatanService {

    private final JadwalKegiatanRepository jadwalKegiatanRepository;
    private final KegiatanRepository kegiatanRepository;

    public JadwalKegiatanService(final JadwalKegiatanRepository jadwalKegiatanRepository,
            final KegiatanRepository kegiatanRepository) {
        this.jadwalKegiatanRepository = jadwalKegiatanRepository;
        this.kegiatanRepository = kegiatanRepository;
    }

    public List<JadwalKegiatanDTO> findAll() {
        final List<JadwalKegiatan> jadwalKegiatans = jadwalKegiatanRepository.findAll(Sort.by("id"));
        return jadwalKegiatans.stream()
                .map(jadwalKegiatan -> mapToDTO(jadwalKegiatan, new JadwalKegiatanDTO()))
                .toList();
    }

    public JadwalKegiatanDTO get(final Long id) {
        return jadwalKegiatanRepository.findById(id)
                .map(jadwalKegiatan -> mapToDTO(jadwalKegiatan, new JadwalKegiatanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final JadwalKegiatanDTO jadwalKegiatanDTO) {
        final JadwalKegiatan jadwalKegiatan = new JadwalKegiatan();
        mapToEntity(jadwalKegiatanDTO, jadwalKegiatan);
        return jadwalKegiatanRepository.save(jadwalKegiatan).getId();
    }

    public void update(final Long id, final JadwalKegiatanDTO jadwalKegiatanDTO) {
        final JadwalKegiatan jadwalKegiatan = jadwalKegiatanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(jadwalKegiatanDTO, jadwalKegiatan);
        jadwalKegiatanRepository.save(jadwalKegiatan);
    }

    public void delete(final Long id) {
        final JadwalKegiatan jadwalKegiatan = jadwalKegiatanRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        jadwalKegiatanRepository.delete(jadwalKegiatan);
    }

    private JadwalKegiatanDTO mapToDTO(final JadwalKegiatan jadwalKegiatan,
            final JadwalKegiatanDTO jadwalKegiatanDTO) {
        jadwalKegiatanDTO.setId(jadwalKegiatan.getId());
        jadwalKegiatanDTO.setHari(jadwalKegiatan.getHari());
        jadwalKegiatanDTO.setPukul(jadwalKegiatan.getPukul());
        jadwalKegiatanDTO.setKeterangan(jadwalKegiatan.getKeterangan());
        jadwalKegiatanDTO.setKegiatan(jadwalKegiatan.getKegiatan() == null ? null : jadwalKegiatan.getKegiatan().getId());
        return jadwalKegiatanDTO;
    }

    private JadwalKegiatan mapToEntity(final JadwalKegiatanDTO jadwalKegiatanDTO,
            final JadwalKegiatan jadwalKegiatan) {
        jadwalKegiatan.setHari(jadwalKegiatanDTO.getHari());
        jadwalKegiatan.setPukul(jadwalKegiatanDTO.getPukul());
        jadwalKegiatan.setKeterangan(jadwalKegiatanDTO.getKeterangan());
        final Kegiatan kegiatan = jadwalKegiatanDTO.getKegiatan() == null ? null : kegiatanRepository.findById(jadwalKegiatanDTO.getKegiatan())
                .orElseThrow(() -> new NotFoundException("kegiatan not found"));
        jadwalKegiatan.setKegiatan(kegiatan);
        return jadwalKegiatan;
    }

    @EventListener(BeforeDeleteKegiatan.class)
    public void on(final BeforeDeleteKegiatan event) {
        final ReferencedException referencedException = new ReferencedException();
        final JadwalKegiatan kegiatanJadwalKegiatan = jadwalKegiatanRepository.findFirstByKegiatanId(event.getId());
        if (kegiatanJadwalKegiatan != null) {
            referencedException.setKey("kegiatan.jadwalKegiatan.kegiatan.referenced");
            referencedException.addParam(kegiatanJadwalKegiatan.getId());
            throw referencedException;
        }
    }

}
