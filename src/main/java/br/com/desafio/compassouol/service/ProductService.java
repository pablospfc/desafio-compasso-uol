package br.com.desafio.compassouol.service;

import br.com.desafio.compassouol.entity.Product;
import br.com.desafio.compassouol.exceptions.ProductNotFound;
import br.com.desafio.compassouol.rest.ErrorResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> getAll();
    Product save(Product product);
    Optional<Product> getById(Long id);
    Product update(Product product, Long id);
    void delete(Long id) throws ProductNotFound;
    List<Product> search(Optional<String> qName, Optional<String> qDescription, Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice);
    ErrorResponse validate(Product product);

}
