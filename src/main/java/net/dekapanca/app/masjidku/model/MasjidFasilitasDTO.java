package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MasjidFasilitasDTO {

    private Long id;

    private String keterangan;

    @NotNull
    private Long masjid;

    @NotNull
    private Long fasilitas;

}
