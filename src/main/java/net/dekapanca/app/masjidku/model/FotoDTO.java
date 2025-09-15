package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FotoDTO {

    private Long id;

    private String url;

    private String keterangan;

    @NotNull
    private Long masjid;

}
