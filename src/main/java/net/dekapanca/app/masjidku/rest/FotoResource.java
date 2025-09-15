package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.FotoDTO;
import net.dekapanca.app.masjidku.service.FotoService;
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
@RequestMapping(value = "/api/fotos", produces = MediaType.APPLICATION_JSON_VALUE)
public class FotoResource {

    private final FotoService fotoService;

    public FotoResource(final FotoService fotoService) {
        this.fotoService = fotoService;
    }

    @GetMapping
    public ResponseEntity<List<FotoDTO>> getAllFotos() {
        return ResponseEntity.ok(fotoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotoDTO> getFoto(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(fotoService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFoto(@RequestBody @Valid final FotoDTO fotoDTO) {
        final Long createdId = fotoService.create(fotoDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFoto(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FotoDTO fotoDTO) {
        fotoService.update(id, fotoDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFoto(@PathVariable(name = "id") final Long id) {
        fotoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
