package uade.api.tpo_p2_mn_grupo_03.service;

import uade.api.tpo_p2_mn_grupo_03.dto.request.AuthenticationRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.RegisterRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.UpdatePasswordRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.AuthenticationResponseDTO;

public interface IAuthenticationService {

    AuthenticationResponseDTO register(RegisterRequestDTO request);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

    AuthenticationResponseDTO updatePassword(String email, UpdatePasswordRequestDTO request);

}