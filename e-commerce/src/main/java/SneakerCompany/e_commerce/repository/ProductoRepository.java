package SneakerCompany.e_commerce.repository;

import SneakerCompany.e_commerce.model.Producto;
import org.springframework.data.domain.Page;          
import org.springframework.data.domain.Pageable;    
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaId(Long categoriaId);
    Page<Producto> findByStockGreaterThanEqual(int stock, Pageable pageable);
}
