package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MasjidDTO {

    @Size(max = 30)
    @MasjidIdNasionalValid
    private String idNasional;

    private String nama;

    @Size(max = 30)
    private String tipologi;

    private String npsm;

    @Size(max = 40)
    private String statusVerifikasi;

    private LocalDate tanggalUpdate;

    private Integer tahunBerdiri;

    private Integer tahunRenovasiTerakhir;

    private Double luasTanahM2;

    private Double luasBangunanM2;

    private Integer kapasitasJamaah;

    @Size(max = 40)
    private String statusTanah;

    private String nomorSertifikat;

    private Double latitude;

    private Double longitude;

    private String website;

    private String kontakTelepon;

    private String kontakEmail;

}
