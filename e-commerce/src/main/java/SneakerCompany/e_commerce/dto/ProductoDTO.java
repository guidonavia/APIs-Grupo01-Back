package SneakerCompany.e_commerce.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private double precio;
    private String descripcion;
    private Integer stock;
    private List<String> fotos;
    private Long categoriaId;
}
