package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class KegiatanDTO {

    private Long id;

    private String nama;

    private String penyelenggara;

    private String deskripsi;

    @NotNull
    private Long masjid;

}
