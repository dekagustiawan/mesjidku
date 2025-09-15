package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DokumenDTO {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String jenis;

    private String nomor;

    private String url;

    private LocalDate tanggalDok;

    @NotNull
    private Long masjid;

}
