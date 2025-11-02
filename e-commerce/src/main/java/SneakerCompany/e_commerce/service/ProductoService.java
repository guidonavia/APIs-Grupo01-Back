package SneakerCompany.e_commerce.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SneakerCompany.e_commerce.dto.ProductoDTO;
import SneakerCompany.e_commerce.model.Producto;
import SneakerCompany.e_commerce.model.Usuario;
import SneakerCompany.e_commerce.repository.ProductoRepository;
import SneakerCompany.e_commerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ProductoDTO> getAllProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream() // Devuelvo los datos en base a la estructura del DTO
            .map(producto -> ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .descripcion(producto.getDescripcion())
                .stock(producto.getStock())
                .fotos(producto.getFotos())
                .categoriaId(producto.getCategoria().getId())
                .build())
            .collect(Collectors.toList());
    }

    public ProductoDTO getProductoById(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        return ProductoDTO.builder()
            .id(producto.getId())
            .nombre(producto.getNombre())
            .descripcion(producto.getDescripcion())
            .precio(producto.getPrecio())
            .stock(producto.getStock())
            .fotos(producto.getFotos())
            .categoriaId(producto.getCategoria().getId())
            .build();
    }

    public List<ProductoDTO> getProductoByCategoria(Long categoriaId) {
        List<Producto> productos = productoRepository.findByCategoriaId(categoriaId);
        return productos.stream() // Devuelvo los datos en base a la estructura del DTO
            .map(producto -> ProductoDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .descripcion(producto.getDescripcion())
                .stock(producto.getStock())
                .fotos(producto.getFotos())
                .categoriaId(producto.getCategoria().getId())
                .build())
            .collect(Collectors.toList());
    }

    public Producto saveProducto(Producto producto) {
        // Validar campos requeridos
        validarCamposRequeridos(producto);
        
        // Obtener el usuario autenticado y asignarlo como creador
        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();
        producto.setCreador(usuarioAutenticado);
        
        System.out.println("Guardando producto en el service: " + producto);
        return productoRepository.save(producto);
    }

    /**
     * Valida que el producto tenga los campos mínimos requeridos:
     * - descripción no nula ni vacía
     * - categoría no nula
     * - al menos una foto
     */
    private void validarCamposRequeridos(Producto producto) {
        if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción es obligatoria");
        }
        
        if (producto.getCategoria() == null) {
            throw new RuntimeException("La categoría es obligatoria");
        }
        
        if (producto.getFotos() == null || producto.getFotos().isEmpty()) {
            throw new RuntimeException("Debe haber al menos una foto");
        }
    }

    /**
     * Obtiene el usuario autenticado desde el SecurityContextHolder
     * @return Usuario autenticado
     * @throws RuntimeException si no hay usuario autenticado
     */
    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    /**
     * Valida que el usuario autenticado sea el creador del producto
     * @param productoId ID del producto a validar
     * @throws RuntimeException si el usuario no es el creador del producto
     */
    private void validarCreador(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));
        
        Usuario usuarioAutenticado = obtenerUsuarioAutenticado();
        
        if (!producto.getCreador().getId().equals(usuarioAutenticado.getId())) {
            throw new RuntimeException("No tienes permiso para realizar esta acción. Solo el creador del producto puede modificarlo o eliminarlo");
        }
    }

    public void deleteProducto(Long id) {
        validarCreador(id);
        productoRepository.deleteById(id);
    }

    public Producto updateProducto(Long id, ProductoDTO productoDTO) {
        validarCreador(id);
        
        return productoRepository.findById(id)
            .map(producto -> {
                producto.setNombre(productoDTO.getNombre());
                producto.setDescripcion(productoDTO.getDescripcion());
                producto.setPrecio(productoDTO.getPrecio());
                producto.setStock(productoDTO.getStock());
                producto.setFotos(productoDTO.getFotos());

                // Validar y buscar la categoría
                if (productoDTO.getCategoriaId() == null) {
                    throw new RuntimeException("El ID de la categoría no puede ser nulo.");
                }
                var categoria = categoriaService.getCategoriaById(productoDTO.getCategoriaId());
                if (categoria == null) {
                    throw new RuntimeException("Categoría no encontrada con ID: " + productoDTO.getCategoriaId());
                }
                producto.setCategoria(categoria);
                
                return productoRepository.save(producto);
            })
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }
}