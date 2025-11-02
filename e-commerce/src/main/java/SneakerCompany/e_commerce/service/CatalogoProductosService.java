package SneakerCompany.e_commerce.service;

import SneakerCompany.e_commerce.catalogo.dto.ProductoResumenDTO;
import SneakerCompany.e_commerce.catalogo.dto.ProductoDetalleDTO;
import SneakerCompany.e_commerce.catalogo.dto.CategoriaDTO;
import SneakerCompany.e_commerce.catalogo.dto.DisponibilidadDTO;
import SneakerCompany.e_commerce.catalogo.ProductoCatalogoMapper;
import SneakerCompany.e_commerce.model.Producto;
import SneakerCompany.e_commerce.model.Categoria;
import SneakerCompany.e_commerce.repository.ProductoRepository;
import SneakerCompany.e_commerce.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogoProductosService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public CatalogoProductosService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<ProductoResumenDTO> listarProductos(Pageable pageable) {
        Page<Producto> productos = productoRepository.findByActivoTrue(pageable);
        return productos.map(ProductoCatalogoMapper::toResumen);
    }

    public List<CategoriaDTO> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAllByOrderByNombreAsc();
        return categorias.stream()
                .map(cat -> new CategoriaDTO(cat.getId(), cat.getNombre()))
                .collect(Collectors.toList());
    }

    public ProductoDetalleDTO obtenerDetalle(Long productoId) {
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if (producto == null) {
            return null;
        }
        return ProductoCatalogoMapper.toDetalle(producto);
    }

    public DisponibilidadDTO consultarDisponibilidad(Long productoId) {
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if (producto == null) {
            return null;
        }
        boolean inStock = producto.getStock() != null && producto.getStock() > 0;
        return new DisponibilidadDTO(producto.getId(), inStock, producto.getStock());
    }
}
