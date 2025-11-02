package SneakerCompany.e_commerce.dto;

import java.io.Serializable;
import java.util.List;

public class ProductoDetalleDTO implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private Integer stock;
    private boolean inStock;
    private String urlImagen;
    private String categoria;

    public ProductoDetalleDTO() {}

    public ProductoDetalleDTO(Long id, String nombre, String descripcion, double precio, Integer stock, boolean inStock, String urlImagen, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.inStock = inStock;
        this.urlImagen = urlImagen;
        this.categoria = categoria;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public Integer getStock() { return stock; }
    public boolean isInStock() { return inStock; }
    public String getUrlImagenPrincipal() { return urlImagen; }
    public String getCategoria() { return categoria; }
}
 