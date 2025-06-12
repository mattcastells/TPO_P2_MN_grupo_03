package uade.api.tpo_p2_mn_grupo_03.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserAddressDTO {
    String street;
    String postalCode;
    String city;
    String state;
    String phone;
} 