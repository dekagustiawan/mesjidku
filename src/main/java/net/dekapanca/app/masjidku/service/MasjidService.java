package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.Masjid;
import net.dekapanca.app.masjidku.events.BeforeDeleteMasjid;
import net.dekapanca.app.masjidku.model.MasjidDTO;
import net.dekapanca.app.masjidku.repos.MasjidRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MasjidService {

    private final MasjidRepository masjidRepository;
    private final ApplicationEventPublisher publisher;

    public MasjidService(final MasjidRepository masjidRepository,
            final ApplicationEventPublisher publisher) {
        this.masjidRepository = masjidRepository;
        this.publisher = publisher;
    }

    public List<MasjidDTO> findAll() {
        final List<Masjid> masjids = masjidRepository.findAll(Sort.by("id"));
        return masjids.stream()
                .map(masjid -> mapToDTO(masjid, new MasjidDTO()))
                .toList();
    }

    public MasjidDTO get(final Long id) {
        return masjidRepository.findById(id)
                .map(masjid -> mapToDTO(masjid, new MasjidDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MasjidDTO masjidDTO) {
        final Masjid masjid = new Masjid();
        mapToEntity(masjidDTO, masjid);
        return masjidRepository.save(masjid).getId();
    }

    public void update(final Long id, final MasjidDTO masjidDTO) {
        final Masjid masjid = masjidRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(masjidDTO, masjid);
        masjidRepository.save(masjid);
    }

    public void delete(final Long id) {
        final Masjid masjid = masjidRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteMasjid(id));
        masjidRepository.delete(masjid);
    }

    private MasjidDTO mapToDTO(final Masjid masjid, final MasjidDTO masjidDTO) {
        masjidDTO.setId(masjid.getId());
        masjidDTO.setNama(masjid.getNama());
        masjidDTO.setTipologi(masjid.getTipologi());
        masjidDTO.setNpsm(masjid.getNpsm());
        masjidDTO.setStatusVerifikasi(masjid.getStatusVerifikasi());
        masjidDTO.setTanggalUpdate(masjid.getTanggalUpdate());
        masjidDTO.setTahunBerdiri(masjid.getTahunBerdiri());
        masjidDTO.setTahunRenovasiTerakhir(masjid.getTahunRenovasiTerakhir());
        masjidDTO.setLuasTanahM2(masjid.getLuasTanahM2());
        masjidDTO.setLuasBangunanM2(masjid.getLuasBangunanM2());
        masjidDTO.setKapasitasJamaah(masjid.getKapasitasJamaah());
        masjidDTO.setStatusTanah(masjid.getStatusTanah());
        masjidDTO.setNomorSertifikat(masjid.getNomorSertifikat());
        masjidDTO.setLatitude(masjid.getLatitude());
        masjidDTO.setLongitude(masjid.getLongitude());
        masjidDTO.setWebsite(masjid.getWebsite());
        masjidDTO.setKontakTelepon(masjid.getKontakTelepon());
        masjidDTO.setKontakEmail(masjid.getKontakEmail());
        masjidDTO.setIdNasional(masjid.getIdNasional());
        return masjidDTO;
    }

    private Masjid mapToEntity(final MasjidDTO masjidDTO, final Masjid masjid) {
        masjid.setNama(masjidDTO.getNama());
        masjid.setTipologi(masjidDTO.getTipologi());
        masjid.setNpsm(masjidDTO.getNpsm());
        masjid.setStatusVerifikasi(masjidDTO.getStatusVerifikasi());
        masjid.setTanggalUpdate(masjidDTO.getTanggalUpdate());
        masjid.setTahunBerdiri(masjidDTO.getTahunBerdiri());
        masjid.setTahunRenovasiTerakhir(masjidDTO.getTahunRenovasiTerakhir());
        masjid.setLuasTanahM2(masjidDTO.getLuasTanahM2());
        masjid.setLuasBangunanM2(masjidDTO.getLuasBangunanM2());
        masjid.setKapasitasJamaah(masjidDTO.getKapasitasJamaah());
        masjid.setStatusTanah(masjidDTO.getStatusTanah());
        masjid.setNomorSertifikat(masjidDTO.getNomorSertifikat());
        masjid.setLatitude(masjidDTO.getLatitude());
        masjid.setLongitude(masjidDTO.getLongitude());
        masjid.setWebsite(masjidDTO.getWebsite());
        masjid.setKontakTelepon(masjidDTO.getKontakTelepon());
        masjid.setKontakEmail(masjidDTO.getKontakEmail());
        masjid.setIdNasional(masjidDTO.getIdNasional());
        return masjid;
    }

}
