package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.AlamatDTO;
import net.dekapanca.app.masjidku.service.AlamatService;
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
@RequestMapping(value = "/api/alamats", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlamatResource {

    private final AlamatService alamatService;

    public AlamatResource(final AlamatService alamatService) {
        this.alamatService = alamatService;
    }

    @GetMapping
    public ResponseEntity<List<AlamatDTO>> getAllAlamats() {
        return ResponseEntity.ok(alamatService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlamatDTO> getAlamat(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(alamatService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createAlamat(@RequestBody @Valid final AlamatDTO alamatDTO) {
        final Long createdId = alamatService.create(alamatDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateAlamat(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final AlamatDTO alamatDTO) {
        alamatService.update(id, alamatDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteAlamat(@PathVariable(name = "id") final Long id) {
        alamatService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
