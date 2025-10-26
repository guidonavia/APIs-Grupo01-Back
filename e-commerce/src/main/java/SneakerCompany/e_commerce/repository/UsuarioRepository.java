package SneakerCompany.e_commerce.repository;


import SneakerCompany.e_commerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Método personalizado para buscar usuario por email
    Optional<Usuario> findByEmail(String email);
    
    // Verificar si existe un email
    boolean existsByEmail(String email);
}