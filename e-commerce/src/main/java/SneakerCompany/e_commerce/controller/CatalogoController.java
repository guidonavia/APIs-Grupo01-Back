package SneakerCompany.e_commerce.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import SneakerCompany.e_commerce.dto.CatalogoProductoDTO;
import SneakerCompany.e_commerce.service.CatalogoService;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoController {
    
    private final CatalogoService catalogoService;

    @GetMapping
    public ResponseEntity<Page<CatalogoProductoDTO>> getCatalogo(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) Double precioMin,
        @RequestParam(required = false) Double precioMax
    ) {  
        Page<CatalogoProductoDTO> productos = catalogoService.buscarProductos(
            search, categoria, precioMin, precioMax);
            
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogoProductoDTO> getDetalleProducto(@PathVariable Long id) {
        CatalogoProductoDTO producto = catalogoService.getDetalleProducto(id);
        return ResponseEntity.ok(producto);
    }
}