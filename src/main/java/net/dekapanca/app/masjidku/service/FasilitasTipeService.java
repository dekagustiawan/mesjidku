package net.dekapanca.app.masjidku.service;

import java.util.List;
import net.dekapanca.app.masjidku.domain.FasilitasTipe;
import net.dekapanca.app.masjidku.events.BeforeDeleteFasilitasTipe;
import net.dekapanca.app.masjidku.model.FasilitasTipeDTO;
import net.dekapanca.app.masjidku.repos.FasilitasTipeRepository;
import net.dekapanca.app.masjidku.util.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FasilitasTipeService {

    private final FasilitasTipeRepository fasilitasTipeRepository;
    private final ApplicationEventPublisher publisher;

    public FasilitasTipeService(final FasilitasTipeRepository fasilitasTipeRepository,
            final ApplicationEventPublisher publisher) {
        this.fasilitasTipeRepository = fasilitasTipeRepository;
        this.publisher = publisher;
    }

    public List<FasilitasTipeDTO> findAll() {
        final List<FasilitasTipe> fasilitasTipes = fasilitasTipeRepository.findAll(Sort.by("id"));
        return fasilitasTipes.stream()
                .map(fasilitasTipe -> mapToDTO(fasilitasTipe, new FasilitasTipeDTO()))
                .toList();
    }

    public FasilitasTipeDTO get(final Long id) {
        return fasilitasTipeRepository.findById(id)
                .map(fasilitasTipe -> mapToDTO(fasilitasTipe, new FasilitasTipeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FasilitasTipeDTO fasilitasTipeDTO) {
        final FasilitasTipe fasilitasTipe = new FasilitasTipe();
        mapToEntity(fasilitasTipeDTO, fasilitasTipe);
        return fasilitasTipeRepository.save(fasilitasTipe).getId();
    }

    public void update(final Long id, final FasilitasTipeDTO fasilitasTipeDTO) {
        final FasilitasTipe fasilitasTipe = fasilitasTipeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(fasilitasTipeDTO, fasilitasTipe);
        fasilitasTipeRepository.save(fasilitasTipe);
    }

    public void delete(final Long id) {
        final FasilitasTipe fasilitasTipe = fasilitasTipeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteFasilitasTipe(id));
        fasilitasTipeRepository.delete(fasilitasTipe);
    }

    private FasilitasTipeDTO mapToDTO(final FasilitasTipe fasilitasTipe,
            final FasilitasTipeDTO fasilitasTipeDTO) {
        fasilitasTipeDTO.setId(fasilitasTipe.getId());
        fasilitasTipeDTO.setKode(fasilitasTipe.getKode());
        fasilitasTipeDTO.setNama(fasilitasTipe.getNama());
        return fasilitasTipeDTO;
    }

    private FasilitasTipe mapToEntity(final FasilitasTipeDTO fasilitasTipeDTO,
            final FasilitasTipe fasilitasTipe) {
        fasilitasTipe.setKode(fasilitasTipeDTO.getKode());
        fasilitasTipe.setNama(fasilitasTipeDTO.getNama());
        return fasilitasTipe;
    }

}
