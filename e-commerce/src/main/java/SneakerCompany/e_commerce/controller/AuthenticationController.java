package SneakerCompany.e_commerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SneakerCompany.e_commerce.dto.LoginDTO;
import SneakerCompany.e_commerce.dto.RegisterDTO;
import SneakerCompany.e_commerce.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
//anotación de Lombok que genera automáticamente un constructor que incluye todos los campos marcados como final, es igual que usar @autowired 
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authenticationService;

    //http://localhost:8080/api/auth/register con metodo post http, enviar un body -> crear un usuario
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    //http://localhost:8080/api/auth/login con metodo post http, enviar un body -> loguear un usuario
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}