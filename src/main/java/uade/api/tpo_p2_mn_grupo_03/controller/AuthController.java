package uade.api.tpo_p2_mn_grupo_03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import uade.api.tpo_p2_mn_grupo_03.dto.request.AuthenticationRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.RegisterRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.AuthenticationResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.service.IAuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private IAuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(
            @Validated @RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @Validated @RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}