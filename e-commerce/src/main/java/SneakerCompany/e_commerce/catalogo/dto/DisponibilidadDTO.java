package SneakerCompany.e_commerce.catalogo.dto;

import java.io.Serializable;

public class DisponibilidadDTO implements Serializable {
    private Long productoId;
    private boolean inStock;
    private Integer stock;

    public DisponibilidadDTO() {}

    public DisponibilidadDTO(Long productoId, boolean inStock, Integer stock) {
        this.productoId = productoId;
        this.inStock = inStock;
        this.stock = stock;
    }

    public Long getProductoId() { return productoId; }
    public boolean isInStock() { return inStock; }
    public Integer getStock() { return stock; }
}
