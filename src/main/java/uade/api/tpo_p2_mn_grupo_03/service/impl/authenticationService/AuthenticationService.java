package uade.api.tpo_p2_mn_grupo_03.service.impl.authenticationService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uade.api.tpo_p2_mn_grupo_03.config.JwtService;
import uade.api.tpo_p2_mn_grupo_03.dto.request.AuthenticationRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.request.RegisterRequestDTO;
import uade.api.tpo_p2_mn_grupo_03.dto.response.AuthenticationResponseDTO;
import uade.api.tpo_p2_mn_grupo_03.model.User;
import uade.api.tpo_p2_mn_grupo_03.model.UserRole;
import uade.api.tpo_p2_mn_grupo_03.repository.UserRepository;
import uade.api.tpo_p2_mn_grupo_03.service.IAuthenticationService;
import uade.api.tpo_p2_mn_grupo_03.service.impl.userService.exception.UserAlreadyExistsException;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        
        @Override
        public AuthenticationResponseDTO register(RegisterRequestDTO request) {
                userRepository.findByEmail(request.getEmail())
                        .ifPresent(user -> {
                                throw new UserAlreadyExistsException();
                        });
                User user = new User(
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getFirstname(),
                    request.getLastname(),
                    UserRole.valueOf(request.getRole())
                    );

                    userRepository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponseDTO.builder()
                                .accessToken(jwtToken)
                                .build();
        }

        @Override
        public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getPassword()));
                var user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponseDTO.builder()
                                .accessToken(jwtToken)
                                .build();
        }
}
