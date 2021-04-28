package br.com.desafio.compassouol.service;

import br.com.desafio.compassouol.entity.Product;
import br.com.desafio.compassouol.repository.ProductRepository;
import br.com.desafio.compassouol.rest.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public ErrorResponse validate(Product product) {
        if (product.getName() == null){
            return new ErrorResponse("The name is required");
        }

        if (product.getDescription() == null) {
            return new ErrorResponse("The description is required");
        }

        if (product.getPrice() == null) {
            return new ErrorResponse("The price is required");
        }

        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return new ErrorResponse("The price cannot be negative");
        }

        return null;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> search(Optional<String> qName, Optional<String> qDescription, Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice) {
        return productRepository.search(qName, qDescription, minPrice, maxPrice);
    }

    @Override
    public Optional<Product> getById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product update(Product product, Long id) {
        Optional<Product> p = productRepository.findById(id);

        Product currentProduct = p.get();

        currentProduct.setDescription(product.getDescription());
        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());

        return productRepository.save(currentProduct);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
