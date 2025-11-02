package SneakerCompany.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import SneakerCompany.e_commerce.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	java.util.List<Categoria> findAllByOrderByNombreAsc();
}