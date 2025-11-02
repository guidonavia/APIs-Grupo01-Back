package SneakerCompany.e_commerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import SneakerCompany.e_commerce.repository.UsuarioRepository;
import SneakerCompany.e_commerce.security.JwtFilter;
import lombok.RequiredArgsConstructor;

// Indica que esta clase contiene configuraciones de Spring
@Configuration
// Habilita la seguridad web de Spring Security
@EnableWebSecurity
// Genera un constructor con los campos final requeridos lombok 
@EnableTransactionManagement
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtFilter jwtFilter;
    // Inyección del repositorio de usuarios
    // pueden utilizar también @Autowired
    private final UsuarioRepository usuarioRepository;

    // Cargar los datos del usuario desde tu sistema a través de UsuarioRepository
    //lo utiliza AuthenticationService. para buscar el email
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> usuarioRepository.findByEmail(username)
                //TODO: ssanchez - capturar con globalexceptionhanlder @ControllerAdivce
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Recibe las credenciales del usuario (a través del UsernamePasswordAuthenticationToken)
    // Usa el UserDetailsService para buscar el usuario en la base de datos
    // Usa el PasswordEncoder para verificar si la contraseña proporcionada coincide con la almacenada
    // Si todo es correcto, crea un token de autenticación; si no, lanza una excepción    
    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    //     authProvider.setPasswordEncoder(passwordEncoder());
    //     authProvider.setUserDetailsService(userDetailsService());
    //     return authProvider;
    // }

    /**
     * AuthenticationManager es el componente central de autenticación en Spring Security.
     * 
     * Funcionamiento:
     * 1. Recibe un objeto Authentication (UsernamePasswordAuthenticationToken en nuestro caso)
     * 2. Delega la autenticación a una cadena de AuthenticationProvider configurados
     * 3. Por defecto, usa DaoAuthenticationProvider que:
     *    - Utiliza UserDetailsService para cargar el usuario de la base de datos
     *    - Emplea PasswordEncoder para verificar la contraseña
     *    - Compara las credenciales proporcionadas con las almacenadas
     * 
     * Proceso de autenticación:
     * - Entrada: Credenciales sin verificar (username/password)
     * - Proceso: Validación de credenciales
     * - Salida: Authentication completamente autenticado con authorities
     * 
     * Si la autenticación falla, lanza AuthenticationException
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Define el codificador de contraseñas que se usará para encriptar y verificar passwords
    // este encoder lo utiliza AuthenticationService.authenticate para verificar la pass, la encripta y compara con la pass de db
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura las reglas de seguridad para las diferentes rutas de la API
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configure(http))  // Enable CORS
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // Allow preflight requests
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/catalogo/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/productos").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/productos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").authenticated()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/pedidos/**").authenticated()
                .requestMatchers("/api/checkout/**").authenticated()
                .anyRequest().authenticated())
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
