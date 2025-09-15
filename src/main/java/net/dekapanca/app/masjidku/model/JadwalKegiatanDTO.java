package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JadwalKegiatanDTO {

    private Long id;

    private String hari;

    private String pukul;

    private String keterangan;

    @NotNull
    private Long kegiatan;

}
