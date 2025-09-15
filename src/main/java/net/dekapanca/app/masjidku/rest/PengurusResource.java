package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.PengurusDTO;
import net.dekapanca.app.masjidku.service.PengurusService;
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
@RequestMapping(value = "/api/penguruses", produces = MediaType.APPLICATION_JSON_VALUE)
public class PengurusResource {

    private final PengurusService pengurusService;

    public PengurusResource(final PengurusService pengurusService) {
        this.pengurusService = pengurusService;
    }

    @GetMapping
    public ResponseEntity<List<PengurusDTO>> getAllPenguruses() {
        return ResponseEntity.ok(pengurusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PengurusDTO> getPengurus(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(pengurusService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPengurus(@RequestBody @Valid final PengurusDTO pengurusDTO) {
        final Long createdId = pengurusService.create(pengurusDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePengurus(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PengurusDTO pengurusDTO) {
        pengurusService.update(id, pengurusDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePengurus(@PathVariable(name = "id") final Long id) {
        pengurusService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
