package SneakerCompany.e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SneakerCompany.e_commerce.dto.ProductoDTO;
import SneakerCompany.e_commerce.model.Producto;
import SneakerCompany.e_commerce.service.ProductoService;

@RestController
@RequestMapping("/api/productos") //localhost:8080/api/productos del locahost:8080/api/productos/id
//TODO: ssanchez - agregar manejo de excepciones con @ControllerAdvice
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;

    //https://localhost:8080/api/productos con metodo get http
    @GetMapping
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    // https://localhost:8080/api/productos/1 con metodo get http

    //TODO: fromano - devolver ProductoDTO en vez de Producto, y response entity.
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {

        ProductoDTO response = productoService.getProductoById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<ProductoDTO> getProductoByCategoria(@PathVariable Long categoriaId) {
        System.out.println("Categoria ID recibido en el controller: " + categoriaId);
        return productoService.getProductoByCategoria(categoriaId);
    }

    //https://localhost:8080/api/productos con metodo post http, enviar un body
    @PostMapping
    public Producto addProducto(@RequestBody Producto producto) {        
        return productoService.saveProducto(producto);
    }
    
    //https://localhost:8080/api/productos/1 con metodo put http, enviar un body
    @PutMapping("/{id}")
    public Producto updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        System.out.print("LLEGUE AL CONTROLLER A MODIFICAR EL PRODUCTO: " + id);
        return productoService.updateProducto(id, productoDTO);
    }

    //https://localhost:8080/api/productos/1 con metodo delete http
    @DeleteMapping("/{id}")
    public void deleteProducto(@PathVariable Long id) {
        System.out.print("LLEGUE AL CONTROLLER A BORRAR EL PRODUCTO: " + id);
        productoService.deleteProducto(id);
    }
}