package web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class AddCrRequest {
    @NotBlank(message = "Code is mandatory")
    private String code;

    @NotBlank(message = "Creancier name is mandatory")
    private String nomCreancier;

    private String logoUrl;


}
