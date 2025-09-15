package net.dekapanca.app.masjidku.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import net.dekapanca.app.masjidku.model.FasilitasTipeDTO;
import net.dekapanca.app.masjidku.service.FasilitasTipeService;
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
@RequestMapping(value = "/api/fasilitasTipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class FasilitasTipeResource {

    private final FasilitasTipeService fasilitasTipeService;

    public FasilitasTipeResource(final FasilitasTipeService fasilitasTipeService) {
        this.fasilitasTipeService = fasilitasTipeService;
    }

    @GetMapping
    public ResponseEntity<List<FasilitasTipeDTO>> getAllFasilitasTipes() {
        return ResponseEntity.ok(fasilitasTipeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FasilitasTipeDTO> getFasilitasTipe(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(fasilitasTipeService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFasilitasTipe(
            @RequestBody @Valid final FasilitasTipeDTO fasilitasTipeDTO) {
        final Long createdId = fasilitasTipeService.create(fasilitasTipeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateFasilitasTipe(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FasilitasTipeDTO fasilitasTipeDTO) {
        fasilitasTipeService.update(id, fasilitasTipeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFasilitasTipe(@PathVariable(name = "id") final Long id) {
        fasilitasTipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
