package SneakerCompany.e_commerce.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import SneakerCompany.e_commerce.dto.CheckoutRequestDTO;
import SneakerCompany.e_commerce.dto.CheckoutResponseDTO;
import SneakerCompany.e_commerce.dto.ItemCheckoutDTO;
import SneakerCompany.e_commerce.model.DetallePedido;
import SneakerCompany.e_commerce.model.Pedido;
import SneakerCompany.e_commerce.model.Producto;
import SneakerCompany.e_commerce.model.Usuario;
import SneakerCompany.e_commerce.repository.PedidoRepository;
import SneakerCompany.e_commerce.repository.ProductoRepository;
import SneakerCompany.e_commerce.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CheckoutService {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoRepository pedidoRepository;

    /**
     * Procesa el checkout de una compra
     * 1. Obtiene el usuario autenticado desde el token JWT
     * 2. Valida que todos los productos existan y tengan stock suficiente
     * 3. Descuenta el stock de los productos
     * 4. Crea el pedido con sus detalles
     * 5. Persiste el pedido en la base de datos
     * 
     * @param checkoutRequest Request con la lista de items a comprar (el usuario se obtiene del JWT)
     * @return CheckoutResponseDTO con los detalles de la compra realizada
     * @throws RuntimeException si el usuario no está autenticado, el producto no existe o no hay stock suficiente
     */
    public CheckoutResponseDTO procesarCheckout(CheckoutRequestDTO checkoutRequest) {
        
        // 1. Obtener el usuario autenticado desde el token JWT
        Usuario usuario = obtenerUsuarioAutenticado();

        // 2. Validar items y verificar stock disponible
        List<DetallePedido> detalles = new ArrayList<>();
        double totalPedido = 0.0;

        for (ItemCheckoutDTO item : checkoutRequest.getItems()) {
            // Buscar el producto
            Producto producto = productoRepository.findById(item.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + item.getProductoId()));

            // Validar que haya stock suficiente
            if (producto.getStock() == null || producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombre() 
                    + ". Stock disponible: " + (producto.getStock() != null ? producto.getStock() : 0) 
                    + ", solicitado: " + item.getCantidad());
            }

            // Validar cantidad positiva
            if (item.getCantidad() <= 0) {
                throw new RuntimeException("La cantidad debe ser mayor a 0 para el producto: " + producto.getNombre());
            }

            // 3. Descontar el stock del producto
            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepository.save(producto);

            // Crear el detalle del pedido
            double subtotal = producto.getPrecio() * item.getCantidad();
            DetallePedido detalle = DetallePedido.builder()
                .producto(producto)
                .cantidad(item.getCantidad())
                .precioUnitario(producto.getPrecio())
                .subtotal(subtotal)
                .build();

            detalles.add(detalle);
            totalPedido += subtotal;
        }

        // 4. Crear el pedido
        Pedido pedido = Pedido.builder()
            .usuario(usuario)
            .fechaCompra(LocalDateTime.now())
            .total(totalPedido)
            .detalles(new ArrayList<>())
            .build();

        // Agregar los detalles al pedido
        for (DetallePedido detalle : detalles) {
            pedido.addDetalle(detalle);
        }

        // 5. Persistir el pedido (cascade guardará automáticamente los detalles)
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // 6. Construir y retornar la respuesta
        return CheckoutResponseDTO.builder()
            .pedidoId(pedidoGuardado.getId())
            .usuarioId(usuario.getId())
            .nombreUsuario(usuario.getNombre() + " " + usuario.getApellido())
            .fechaCompra(pedidoGuardado.getFechaCompra())
            .total(pedidoGuardado.getTotal())
            .mensaje("Compra realizada exitosamente")
            .build();
    }

    public List<CheckoutResponseDTO> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<CheckoutResponseDTO> responseList = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            CheckoutResponseDTO responseDTO = CheckoutResponseDTO.builder()
                .pedidoId(pedido.getId())
                .usuarioId(pedido.getUsuario().getId())
                .nombreUsuario(pedido.getUsuario().getNombre() + " " + pedido.getUsuario().getApellido())
                .fechaCompra(pedido.getFechaCompra())
                .total(pedido.getTotal())
                .mensaje("Detalle del pedido")
                .build();
            responseList.add(responseDTO);
        }

        return responseList;
    }

    /**
     * Obtiene el usuario autenticado desde el SecurityContextHolder
     * Extrae el email del token JWT (que está en el Authentication.getName())
     * y busca el usuario en la base de datos
     * 
     * @return Usuario autenticado
     * @throws RuntimeException si no hay usuario autenticado
     */
    private Usuario obtenerUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuario no autenticado");
        }
        
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }
    
}
