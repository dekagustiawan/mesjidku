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
        final List<Masjid> masjids = masjidRepository.findAll(Sort.by("idNasional"));
        return masjids.stream()
                .map(masjid -> mapToDTO(masjid, new MasjidDTO()))
                .toList();
    }

    public MasjidDTO get(final String idNasional) {
        return masjidRepository.findById(idNasional)
                .map(masjid -> mapToDTO(masjid, new MasjidDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final MasjidDTO masjidDTO) {
        final Masjid masjid = new Masjid();
        mapToEntity(masjidDTO, masjid);
        masjid.setIdNasional(masjidDTO.getIdNasional());
        return masjidRepository.save(masjid).getIdNasional();
    }

    public void update(final String idNasional, final MasjidDTO masjidDTO) {
        final Masjid masjid = masjidRepository.findById(idNasional)
                .orElseThrow(NotFoundException::new);
        mapToEntity(masjidDTO, masjid);
        masjidRepository.save(masjid);
    }

    public void delete(final String idNasional) {
        final Masjid masjid = masjidRepository.findById(idNasional)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteMasjid(idNasional));
        masjidRepository.delete(masjid);
    }

    private MasjidDTO mapToDTO(final Masjid masjid, final MasjidDTO masjidDTO) {
        masjidDTO.setIdNasional(masjid.getIdNasional());
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
        return masjid;
    }

    public boolean idNasionalExists(final String idNasional) {
        return masjidRepository.existsByIdNasionalIgnoreCase(idNasional);
    }

}
