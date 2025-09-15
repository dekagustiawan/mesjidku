package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.MasjidDTO;
import net.dekapanca.app.masjidku.service.MasjidService;
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
@RequestMapping(value = "/api/masjids", produces = MediaType.APPLICATION_JSON_VALUE)
public class MasjidResource {

    private final MasjidService masjidService;

    public MasjidResource(final MasjidService masjidService) {
        this.masjidService = masjidService;
    }

    @GetMapping
    public ResponseEntity<List<MasjidDTO>> getAllMasjids() {
        return ResponseEntity.ok(masjidService.findAll());
    }

    @GetMapping("/{idNasional}")
    public ResponseEntity<MasjidDTO> getMasjid(
            @PathVariable(name = "idNasional") final String idNasional) {
        return ResponseEntity.ok(masjidService.get(idNasional));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createMasjid(@RequestBody @Valid final MasjidDTO masjidDTO) {
        final String createdIdNasional = masjidService.create(masjidDTO);
        return new ResponseEntity<>('"' + createdIdNasional + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{idNasional}")
    public ResponseEntity<String> updateMasjid(
            @PathVariable(name = "idNasional") final String idNasional,
            @RequestBody @Valid final MasjidDTO masjidDTO) {
        masjidService.update(idNasional, masjidDTO);
        return ResponseEntity.ok('"' + idNasional + '"');
    }

    @DeleteMapping("/{idNasional}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMasjid(
            @PathVariable(name = "idNasional") final String idNasional) {
        masjidService.delete(idNasional);
        return ResponseEntity.noContent().build();
    }

}
