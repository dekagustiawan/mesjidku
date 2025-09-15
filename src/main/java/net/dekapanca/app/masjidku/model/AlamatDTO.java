package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AlamatDTO {

    private Long id;

    private String alamatJalan;

    @Size(max = 5)
    private String rt;

    @Size(max = 5)
    private String rw;

    private String desaKelurahan;

    private String kecamatan;

    private String kabKota;

    private String provinsi;

    @Size(max = 10)
    private String kodePos;

    @NotNull
    private Long masjid;

}
