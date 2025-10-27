package SneakerCompany.e_commerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CatalogoProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long categoriaId;
    private Boolean disponible;
}