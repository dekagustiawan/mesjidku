package net.dekapanca.app.masjidku.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Masjids")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Masjid {

    @Id
    @Column(nullable = false, updatable = false, length = 30)
    private String idNasional;

    @Column(columnDefinition = "text")
    private String nama;

    @Column(length = 30)
    private String tipologi;

    @Column(columnDefinition = "text")
    private String npsm;

    @Column(length = 40)
    private String statusVerifikasi;

    @Column
    private LocalDate tanggalUpdate;

    @Column
    private Integer tahunBerdiri;

    @Column
    private Integer tahunRenovasiTerakhir;

    @Column
    private Double luasTanahM2;

    @Column
    private Double luasBangunanM2;

    @Column
    private Integer kapasitasJamaah;

    @Column(length = 40)
    private String statusTanah;

    @Column(columnDefinition = "text")
    private String nomorSertifikat;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(columnDefinition = "text")
    private String website;

    @Column(columnDefinition = "text")
    private String kontakTelepon;

    @Column(columnDefinition = "text")
    private String kontakEmail;

    @OneToMany(mappedBy = "masjid")
    private Set<Alamat> masjidAlamats = new HashSet<>();

    @OneToMany(mappedBy = "masjid")
    private Set<Pengurus> masjidPenguruses = new HashSet<>();

    @OneToMany(mappedBy = "masjid")
    private Set<Dokumen> masjidDokumens = new HashSet<>();

    @OneToMany(mappedBy = "masjid")
    private Set<Foto> masjidFotoes = new HashSet<>();

    @OneToMany(mappedBy = "masjid")
    private Set<Kegiatan> masjidKegiatans = new HashSet<>();

    @OneToMany(mappedBy = "masjid")
    private Set<MasjidFasilitas> masjidMasjidFasilitases = new HashSet<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
