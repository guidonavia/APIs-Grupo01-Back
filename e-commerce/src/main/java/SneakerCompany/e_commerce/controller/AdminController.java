package SneakerCompany.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SneakerCompany.e_commerce.dto.CheckoutResponseDTO;
import java.util.List;
import SneakerCompany.e_commerce.service.CheckoutService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping("/pedidos")
    public List<CheckoutResponseDTO> getAllPedidos() {
        return checkoutService.getAllPedidos();
    }
    
}
