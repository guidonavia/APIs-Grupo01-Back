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
        // if(validarCreador()){} else {throw new UnauthorizedException("No tienes permiso para eliminar este producto");}
        return productoRepository.findById(id)
            .map(producto -> {
                producto.setNombre(productoDTO.getNombre());
                producto.setDescripcion(productoDTO.getDescripcion());
                producto.setPrecio(productoDTO.getPrecio());
                producto.setStock(productoDTO.getStock());
                var categoria = categoriaService.getCategoriaById(productoDTO.getCategoriaId());
                producto.setCategoria(categoria);
                return productoRepository.save(producto);
            })
            .orElse(null);
    }
}