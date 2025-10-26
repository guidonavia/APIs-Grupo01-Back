package SneakerCompany.e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SneakerCompany.e_commerce.dto.CheckoutRequestDTO;
import SneakerCompany.e_commerce.dto.CheckoutResponseDTO;
import SneakerCompany.e_commerce.service.CheckoutService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador para el proceso de checkout
 * Este endpoint está securitizado con JWT a través de SecurityConfig
 * Solo los usuarios autenticados pueden realizar compras
 */
@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * Endpoint para procesar el checkout de una compra
     * POST /api/checkout
     * 
     * Seguridad:
     * - Requiere autenticación JWT (configurado en SecurityConfig)
     * - El token JWT debe enviarse en el header Authorization: Bearer {token}
     * 
     * Request Body:
     * {
     *   "usuarioId": 1,
     *   "items": [
     *     {
     *       "productoId": 1,
     *       "cantidad": 2
     *     },
     *     {
     *       "productoId": 2,
     *       "cantidad": 1
     *     }
     *   ]
     * }
     * 
     * Response:
     * {
     *   "pedidoId": 1,
     *   "usuarioId": 1,
     *   "nombreUsuario": "Juan Perez",
     *   "fechaCompra": "2025-10-26T18:30:00",
     *   "total": 150.50,
     *   "mensaje": "Compra realizada exitosamente"
     * }
     * 
     * @param checkoutRequest Request con el usuario y los items a comprar
     * @return ResponseEntity con los detalles de la compra realizada
     */
    @PostMapping
    public ResponseEntity<CheckoutResponseDTO> realizarCheckout(@RequestBody CheckoutRequestDTO checkoutRequest) {
        try {
            CheckoutResponseDTO response = checkoutService.procesarCheckout(checkoutRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // TODO: ssanchez - Implementar manejo de excepciones con @ControllerAdvice
            // Por ahora devolvemos el error en el response
            CheckoutResponseDTO errorResponse = CheckoutResponseDTO.builder()
                .mensaje("Error al procesar el checkout: " + e.getMessage())
                .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
