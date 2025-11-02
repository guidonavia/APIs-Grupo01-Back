package SneakerCompany.e_commerce.catalogo.dto;

import java.io.Serializable;

public class ProductoResumenDTO implements Serializable {
    private Long id;
    private String nombre;
    private double precio;
    private boolean inStock;
    private String urlImagenPrincipal;

    public ProductoResumenDTO() {}

    public ProductoResumenDTO(Long id, String nombre, double precio, boolean inStock, String urlImagenPrincipal) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.inStock = inStock;
        this.urlImagenPrincipal = urlImagenPrincipal;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public boolean isInStock() { return inStock; }
    public String getUrlImagenPrincipal() { return urlImagenPrincipal; }}
