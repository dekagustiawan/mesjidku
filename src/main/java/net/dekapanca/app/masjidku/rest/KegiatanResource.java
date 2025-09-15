package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.KegiatanDTO;
import net.dekapanca.app.masjidku.service.KegiatanService;
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
@RequestMapping(value = "/api/kegiatans", produces = MediaType.APPLICATION_JSON_VALUE)
public class KegiatanResource {

    private final KegiatanService kegiatanService;

    public KegiatanResource(final KegiatanService kegiatanService) {
        this.kegiatanService = kegiatanService;
    }

    @GetMapping
    public ResponseEntity<List<KegiatanDTO>> getAllKegiatans() {
        return ResponseEntity.ok(kegiatanService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KegiatanDTO> getKegiatan(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(kegiatanService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createKegiatan(@RequestBody @Valid final KegiatanDTO kegiatanDTO) {
        final Long createdId = kegiatanService.create(kegiatanDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateKegiatan(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final KegiatanDTO kegiatanDTO) {
        kegiatanService.update(id, kegiatanDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteKegiatan(@PathVariable(name = "id") final Long id) {
        kegiatanService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
