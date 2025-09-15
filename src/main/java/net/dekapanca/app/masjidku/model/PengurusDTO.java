package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PengurusDTO {

    private Long id;

    @NotNull
    @Size(max = 40)
    private String role;

    private String nama;

    private String telepon;

    private String email;

    @NotNull
    private Long masjid;

}
