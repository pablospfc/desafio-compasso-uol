package br.com.desafio.compassouol.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message="The name cannot be null")
    private String name;

    @NotNull(message="The description cannot be null")
    private String description;

    @NotNull(message="The price cannot be null")
    private BigDecimal price;

}
