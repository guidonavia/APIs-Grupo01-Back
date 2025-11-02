package SneakerCompany.e_commerce.mapper;
import SneakerCompany.e_commerce.dto.ProductoDetalleDTO;
import SneakerCompany.e_commerce.dto.ProductoResumenDTO;
import SneakerCompany.e_commerce.model.Producto;

public class ProductoCatalogoMapper {
    public static ProductoResumenDTO toResumen(Producto producto) {
        String urlImagenPrincipal = null;
        if (producto.getFotos() != null && !producto.getFotos().isEmpty()) {
            urlImagenPrincipal = producto.getFotos().get(0);
        }
        boolean inStock = producto.getStock() != null && producto.getStock() > 0;
        return new ProductoResumenDTO(
            producto.getId(),
            producto.getNombre(),
            producto.getPrecio(),
            inStock,
            urlImagenPrincipal
        );
    }

    public static ProductoDetalleDTO toDetalle(Producto producto) {
        String urlImagenPrincipal = null;
        if (producto.getFotos() != null && !producto.getFotos().isEmpty()) {
            urlImagenPrincipal = producto.getFotos().get(0);
        }
        boolean inStock = producto.getStock() != null && producto.getStock() > 0;
        String categoriaNombre = producto.getCategoria() != null ? producto.getCategoria().getNombre() : null;
        return new ProductoDetalleDTO(
            producto.getId(),
            producto.getNombre(),
            producto.getDescripcion(),
            producto.getPrecio(),
            producto.getStock(),
            inStock,
            urlImagenPrincipal,
            categoriaNombre
        );
    }
}
