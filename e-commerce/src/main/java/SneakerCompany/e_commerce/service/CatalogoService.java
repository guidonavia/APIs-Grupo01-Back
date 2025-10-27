package SneakerCompany.e_commerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import SneakerCompany.e_commerce.dto.CatalogoProductoDTO;

@Service
@Transactional
public class CatalogoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    public Page<CatalogoProductoDTO> buscarProductos( String search, Long categoriaId, Double precioMin, Double precioMax) {
        Page<Producto> productos = productoRepository.buscarProductos(
            search, 
            categoriaId, 
            precioMin, 
            precioMax
        );
        return productos.map(this::convertToDTO);
    }

    public CatalogoProductoDTO getDetalleProducto(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertToDTO(producto);
    }

    private CatalogoProductoDTO convertToDTO(Producto producto) {
        return CatalogoProductoDTO.builder()
            .id(producto.getId())
            .nombre(producto.getNombre())
            .descripcion(producto.getDescripcion())
            .precio(producto.getPrecio())
            .categoria(producto.getCategoria().getNombre())
            .build();
    }
}