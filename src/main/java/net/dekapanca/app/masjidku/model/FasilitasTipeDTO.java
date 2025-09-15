package net.dekapanca.app.masjidku.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FasilitasTipeDTO {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String kode;

    @NotNull
    @Size(max = 100)
    private String nama;

}
