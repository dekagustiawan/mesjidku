package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.Foto;
import net.dekapanca.app.masjidku.domain.Masjid;
import net.dekapanca.app.masjidku.events.BeforeDeleteMasjid;
import net.dekapanca.app.masjidku.model.FotoDTO;
import net.dekapanca.app.masjidku.repos.FotoRepository;
import net.dekapanca.app.masjidku.repos.MasjidRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import net.dekapanca.app.masjidku.util.ReferencedException;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FotoService {

    private final FotoRepository fotoRepository;
    private final MasjidRepository masjidRepository;

    public FotoService(final FotoRepository fotoRepository,
            final MasjidRepository masjidRepository) {
        this.fotoRepository = fotoRepository;
        this.masjidRepository = masjidRepository;
    }

    public List<FotoDTO> findAll() {
        final List<Foto> fotoes = fotoRepository.findAll(Sort.by("id"));
        return fotoes.stream()
                .map(foto -> mapToDTO(foto, new FotoDTO()))
                .toList();
    }

    public FotoDTO get(final Long id) {
        return fotoRepository.findById(id)
                .map(foto -> mapToDTO(foto, new FotoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FotoDTO fotoDTO) {
        final Foto foto = new Foto();
        mapToEntity(fotoDTO, foto);
        return fotoRepository.save(foto).getId();
    }

    public void update(final Long id, final FotoDTO fotoDTO) {
        final Foto foto = fotoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fotoDTO, foto);
        fotoRepository.save(foto);
    }

    public void delete(final Long id) {
        final Foto foto = fotoRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        fotoRepository.delete(foto);
    }

    private FotoDTO mapToDTO(final Foto foto, final FotoDTO fotoDTO) {
        fotoDTO.setId(foto.getId());
        fotoDTO.setUrl(foto.getUrl());
        fotoDTO.setKeterangan(foto.getKeterangan());
        fotoDTO.setMasjid(foto.getMasjid() == null ? null : foto.getMasjid().getId());
        return fotoDTO;
    }

    private Foto mapToEntity(final FotoDTO fotoDTO, final Foto foto) {
        foto.setUrl(fotoDTO.getUrl());
        foto.setKeterangan(fotoDTO.getKeterangan());
        final Masjid masjid = fotoDTO.getMasjid() == null ? null : masjidRepository.findById(fotoDTO.getMasjid())
                .orElseThrow(() -> new NotFoundException("masjid not found"));
        foto.setMasjid(masjid);
        return foto;
    }

    @EventListener(BeforeDeleteMasjid.class)
    public void on(final BeforeDeleteMasjid event) {
        final ReferencedException referencedException = new ReferencedException();
        final Foto masjidFoto = fotoRepository.findFirstByMasjidId(event.getId());
        if (masjidFoto != null) {
            referencedException.setKey("masjid.foto.masjid.referenced");
            referencedException.addParam(masjidFoto.getId());
            throw referencedException;
        }
    }

}
