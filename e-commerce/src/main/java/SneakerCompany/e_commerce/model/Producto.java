package SneakerCompany.e_commerce.model;

//import java.util.ArrayList;
//import java.util.List;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;
    private double precio;
    private String descripcion;

    @Column(nullable = false)
    private String categoria;
    
    @Column(nullable = false)
    private Integer stock;


    //TODO: fromano - Modelar categorias y relaciones

    /* @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "productos_categorias",
        joinColumns = @JoinColumn(name = "producto_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias = new ArrayList<>(); */
        
}   