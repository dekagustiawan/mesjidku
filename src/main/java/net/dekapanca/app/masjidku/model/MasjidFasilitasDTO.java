package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MasjidFasilitasDTO {

    private Long id;

    private String keterangan;

    @NotNull
    @Size(max = 30)
    private String masjid;

    @NotNull
    private Long fasilitas;

}
