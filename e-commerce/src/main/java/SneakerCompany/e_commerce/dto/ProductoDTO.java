package SneakerCompany.e_commerce.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;

}
