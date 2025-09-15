package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.Alamat;
import net.dekapanca.app.masjidku.domain.Masjid;
import net.dekapanca.app.masjidku.events.BeforeDeleteMasjid;
import net.dekapanca.app.masjidku.model.AlamatDTO;
import net.dekapanca.app.masjidku.repos.AlamatRepository;
import net.dekapanca.app.masjidku.repos.MasjidRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import net.dekapanca.app.masjidku.util.ReferencedException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AlamatService {

    private final AlamatRepository alamatRepository;
    private final MasjidRepository masjidRepository;

    public AlamatService(final AlamatRepository alamatRepository,
            final MasjidRepository masjidRepository) {
        this.alamatRepository = alamatRepository;
        this.masjidRepository = masjidRepository;
    }

    public List<AlamatDTO> findAll() {
        final List<Alamat> alamats = alamatRepository.findAll(Sort.by("id"));
        return alamats.stream()
                .map(alamat -> mapToDTO(alamat, new AlamatDTO()))
                .toList();
    }

    public AlamatDTO get(final Long id) {
        return alamatRepository.findById(id)
                .map(alamat -> mapToDTO(alamat, new AlamatDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AlamatDTO alamatDTO) {
        final Alamat alamat = new Alamat();
        mapToEntity(alamatDTO, alamat);
        return alamatRepository.save(alamat).getId();
    }

    public void update(final Long id, final AlamatDTO alamatDTO) {
        final Alamat alamat = alamatRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(alamatDTO, alamat);
        alamatRepository.save(alamat);
    }

    public void delete(final Long id) {
        final Alamat alamat = alamatRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        alamatRepository.delete(alamat);
    }

    private AlamatDTO mapToDTO(final Alamat alamat, final AlamatDTO alamatDTO) {
        alamatDTO.setId(alamat.getId());
        alamatDTO.setAlamatJalan(alamat.getAlamatJalan());
        alamatDTO.setRt(alamat.getRt());
        alamatDTO.setRw(alamat.getRw());
        alamatDTO.setDesaKelurahan(alamat.getDesaKelurahan());
        alamatDTO.setKecamatan(alamat.getKecamatan());
        alamatDTO.setKabKota(alamat.getKabKota());
        alamatDTO.setProvinsi(alamat.getProvinsi());
        alamatDTO.setKodePos(alamat.getKodePos());
        alamatDTO.setMasjid(alamat.getMasjid() == null ? null : alamat.getMasjid().getIdNasional());
        return alamatDTO;
    }

    private Alamat mapToEntity(final AlamatDTO alamatDTO, final Alamat alamat) {
        alamat.setAlamatJalan(alamatDTO.getAlamatJalan());
        alamat.setRt(alamatDTO.getRt());
        alamat.setRw(alamatDTO.getRw());
        alamat.setDesaKelurahan(alamatDTO.getDesaKelurahan());
        alamat.setKecamatan(alamatDTO.getKecamatan());
        alamat.setKabKota(alamatDTO.getKabKota());
        alamat.setProvinsi(alamatDTO.getProvinsi());
        alamat.setKodePos(alamatDTO.getKodePos());
        final Masjid masjid = alamatDTO.getMasjid() == null ? null : masjidRepository.findById(alamatDTO.getMasjid())
                .orElseThrow(() -> new NotFoundException("masjid not found"));
        alamat.setMasjid(masjid);
        return alamat;
    }

    @EventListener(BeforeDeleteMasjid.class)
    public void on(final BeforeDeleteMasjid event) {
        final ReferencedException referencedException = new ReferencedException();
        final Alamat masjidAlamat = alamatRepository.findFirstByMasjidIdNasional(event.getIdNasional());
        if (masjidAlamat != null) {
            referencedException.setKey("masjid.alamat.masjid.referenced");
            referencedException.addParam(masjidAlamat.getId());
            throw referencedException;
        }
    }

}
