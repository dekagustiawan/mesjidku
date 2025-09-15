package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.JadwalKegiatanDTO;
import net.dekapanca.app.masjidku.service.JadwalKegiatanService;
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
@RequestMapping(value = "/api/jadwalKegiatans", produces = MediaType.APPLICATION_JSON_VALUE)
public class JadwalKegiatanResource {

    private final JadwalKegiatanService jadwalKegiatanService;

    public JadwalKegiatanResource(final JadwalKegiatanService jadwalKegiatanService) {
        this.jadwalKegiatanService = jadwalKegiatanService;
    }

    @GetMapping
    public ResponseEntity<List<JadwalKegiatanDTO>> getAllJadwalKegiatans() {
        return ResponseEntity.ok(jadwalKegiatanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JadwalKegiatanDTO> getJadwalKegiatan(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(jadwalKegiatanService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createJadwalKegiatan(
            @RequestBody @Valid final JadwalKegiatanDTO jadwalKegiatanDTO) {
        final Long createdId = jadwalKegiatanService.create(jadwalKegiatanDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateJadwalKegiatan(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final JadwalKegiatanDTO jadwalKegiatanDTO) {
        jadwalKegiatanService.update(id, jadwalKegiatanDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteJadwalKegiatan(@PathVariable(name = "id") final Long id) {
        jadwalKegiatanService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
