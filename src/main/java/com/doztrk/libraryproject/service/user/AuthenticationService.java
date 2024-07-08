package com.doztrk.libraryproject.service.user;

import com.doztrk.libraryproject.payload.request.authentication.LoginRequest;
import com.doztrk.libraryproject.payload.response.authentication.AuthResponse;
import com.doztrk.libraryproject.repository.user.UserRepository;
import com.doztrk.libraryproject.security.jwt.JwtUtils;
import com.doztrk.libraryproject.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    public final JwtUtils jwtUtils;
    public final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public ResponseEntity<AuthResponse> authenticateUser(LoginRequest loginRequest) {
        //!!! Gelen requestin icinden kullanici adi ve parola bilgisi aliniyor
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        // !!! authenticationManager uzerinden kullaniciyi valide ediyoruz
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        // !!! valide edilen kullanici Context e atiliyor
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // !!! JWT token olusturuluyor
        String token = "Bearer " + jwtUtils.generateJwtToken(authentication);
        // !!! login islemini gerceklestirilen kullaniciya ulasiliyor
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        // !!!  Response olarak login islemini yapan kullaniciyi donecegiz gerekli fieldlar setleniyor
        // !!! GrantedAuthority turundeki role yapisini String turune ceviriliyor
        Set<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        //!!! bir kullanicinin birden fazla rolu olmayacagi icin ilk indexli elemani aliyoruz
        Optional<String> role = roles.stream().findFirst();
        // burada login islemini gerceklestiren kullanici bilgilerini response olarak
        // gonderecegimiz icin, gerekli bilgiler setleniyor.
        AuthResponse.AuthResponseBuilder authResponse = AuthResponse.builder();
        authResponse.email(userDetails.getUsername());
        authResponse.token(token.substring(7));
        authResponse.email(userDetails.getUsername());
        // !!! role bilgisi varsa response nesnesindeki degisken setleniyor
        role.ifPresent(authResponse::role);
        // !!! AuthResponse nesnesi ResponseEntity ile donduruyoruz
        return ResponseEntity.ok(authResponse.build());
    }
}
