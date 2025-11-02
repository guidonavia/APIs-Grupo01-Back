package SneakerCompany.e_commerce.catalogo.dto;

import java.io.Serializable;

public class CategoriaDTO implements Serializable {
    private Long id;
    private String nombre;

    public CategoriaDTO() {}

    public CategoriaDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
}
