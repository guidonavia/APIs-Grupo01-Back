package SneakerCompany.e_commerce.controller;

import SneakerCompany.e_commerce.dto.CategoriaDTO;
import SneakerCompany.e_commerce.dto.DisponibilidadDTO;
import SneakerCompany.e_commerce.dto.ProductoDetalleDTO;
import SneakerCompany.e_commerce.dto.ProductoResumenDTO;
import SneakerCompany.e_commerce.service.CatalogoProductosService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
public class CatalogoProductosController {

    private final CatalogoProductosService catalogoProductosService;

    public CatalogoProductosController(CatalogoProductosService catalogoProductosService) {
        this.catalogoProductosService = catalogoProductosService;
    }

    @GetMapping("/productos")
    public ResponseEntity<Page<ProductoResumenDTO>> listarProductos(
            @PageableDefault(size = 20, sort = "nombre") Pageable pageable) {
        Page<ProductoResumenDTO> productos = catalogoProductosService.listarProductos(pageable);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        List<CategoriaDTO> categorias = catalogoProductosService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDetalleDTO> obtenerDetalle(@PathVariable Long id) {
        ProductoDetalleDTO detalle = catalogoProductosService.obtenerDetalle(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalle);
    }

    @GetMapping("/productos/{id}/disponibilidad")
    public ResponseEntity<DisponibilidadDTO> consultarDisponibilidad(@PathVariable Long id) {
        DisponibilidadDTO disponibilidad = catalogoProductosService.consultarDisponibilidad(id);
        if (disponibilidad == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(disponibilidad);
    }
}