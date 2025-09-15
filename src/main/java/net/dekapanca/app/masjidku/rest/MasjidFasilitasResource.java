package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.MasjidFasilitasDTO;
import net.dekapanca.app.masjidku.service.MasjidFasilitasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/masjidFasilitass", produces = MediaType.APPLICATION_JSON_VALUE)
public class MasjidFasilitasResource {

    private final MasjidFasilitasService masjidFasilitasService;

    public MasjidFasilitasResource(final MasjidFasilitasService masjidFasilitasService) {
        this.masjidFasilitasService = masjidFasilitasService;
    }

    @GetMapping
    public ResponseEntity<List<MasjidFasilitasDTO>> getAllMasjidFasilitass() {
        return ResponseEntity.ok(masjidFasilitasService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasjidFasilitasDTO> getMasjidFasilitas(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(masjidFasilitasService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createMasjidFasilitas(
            @RequestBody @Valid final MasjidFasilitasDTO masjidFasilitasDTO) {
        final Long createdId = masjidFasilitasService.create(masjidFasilitasDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMasjidFasilitas(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MasjidFasilitasDTO masjidFasilitasDTO) {
        masjidFasilitasService.update(id, masjidFasilitasDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMasjidFasilitas(@PathVariable(name = "id") final Long id) {
        masjidFasilitasService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
