package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.DokumenDTO;
import net.dekapanca.app.masjidku.service.DokumenService;
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
@RequestMapping(value = "/api/dokumens", produces = MediaType.APPLICATION_JSON_VALUE)
public class DokumenResource {

    private final DokumenService dokumenService;

    public DokumenResource(final DokumenService dokumenService) {
        this.dokumenService = dokumenService;
    }

    @GetMapping
    public ResponseEntity<List<DokumenDTO>> getAllDokumens() {
        return ResponseEntity.ok(dokumenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DokumenDTO> getDokumen(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(dokumenService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createDokumen(@RequestBody @Valid final DokumenDTO dokumenDTO) {
        final Long createdId = dokumenService.create(dokumenDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDokumen(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DokumenDTO dokumenDTO) {
        dokumenService.update(id, dokumenDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteDokumen(@PathVariable(name = "id") final Long id) {
        dokumenService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
