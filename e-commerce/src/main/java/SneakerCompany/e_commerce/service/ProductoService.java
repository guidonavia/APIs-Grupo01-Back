package SneakerCompany.e_commerce.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SneakerCompany.e_commerce.dto.ProductoDTO;
import SneakerCompany.e_commerce.model.Producto;
import SneakerCompany.e_commerce.repository.ProductoRepository;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaService categoriaService;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Producto getProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto saveProducto(Producto producto) {
        System.out.println("Guardando producto en el service: " + producto);
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        // if(validarCreador()){} else {throw new UnauthorizedException("No tienes permiso para eliminar este producto");}
        productoRepository.deleteById(id);
    }

    public Producto updateProducto(Long id, ProductoDTO productoDTO) {
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