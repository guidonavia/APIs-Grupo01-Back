package SneakerCompany.e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutResponseDTO {
    private Long pedidoId;
    private Long usuarioId;
    private String nombreUsuario;
    private LocalDateTime fechaCompra;
    private Double total;
    private String mensaje;
}
