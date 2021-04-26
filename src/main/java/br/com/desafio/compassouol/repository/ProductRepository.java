package br.com.desafio.compassouol.repository;

import br.com.desafio.compassouol.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT product FROM Product product WHERE 1=1 "
            + " and (:name IS NULL OR product.name LIKE %:name%)"
            + " and (:description IS NULL OR product.description LIKE %:description%)"
            + " and (:maxPrice IS NULL OR product.price <= :maxPrice)"
            + " and (:minPrice IS NULL OR product.price >= :minPrice)")
    List<Product> search(@Param("name") Optional<String> name,
                         @Param("description") Optional<String> description,
                         @Param("minPrice") Optional<BigDecimal> minPrice,
                         @Param("maxPrice") Optional<BigDecimal> maxPrice);
}
