package SneakerCompany.e_commerce.repository;

import SneakerCompany.e_commerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
