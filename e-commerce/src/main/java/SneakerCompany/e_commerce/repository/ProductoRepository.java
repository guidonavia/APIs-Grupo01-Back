package SneakerCompany.e_commerce.repository;

import SneakerCompany.e_commerce.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    @Query("SELECT p FROM Producto p WHERE " +
           "(:search IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:categoriaId IS NULL OR p.categoria.id = :categoriaId) AND " +
           "(:precioMin IS NULL OR p.precio >= :precioMin) AND " +
           "(:precioMax IS NULL OR p.precio <= :precioMax)")
    Page<Producto> buscarProductos(
        @Param("search") String search,
        @Param("categoriaId") Long categoriaId,
        @Param("precioMin") Double precioMin,
        @Param("precioMax") Double precioMax
    );
}
