package br.com.desafio.compassouol.controller;

import br.com.desafio.compassouol.entity.Product;
import br.com.desafio.compassouol.rest.ErrorResponse;
import br.com.desafio.compassouol.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerUnitTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    public void deveRetornar200AoListarProdutos() {

        List<Product> products = new ArrayList<>();
        products.add(new Product());
        Mockito.when(productService.getAll()).thenReturn(products);
        ResponseEntity<List<Product>> response = productController.list();
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deveRetornar200AoBuscarPorId() {
        Long id = 1L;
        Optional<Product> product = Optional.of(new Product());
        product.get().setId(1L);
        Mockito.when(productService.getById(id)).thenReturn(product);
        ResponseEntity<Product> response = productController.getById(id);
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deveRetornar404AoBuscarCasoIdNaoExistir() {
        Long id = 1L;
        Mockito.when(productService.getById(id)).thenReturn(Optional.empty());
        ResponseEntity<Product> response = productController.getById(id);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void deveRetornar200AoPesquisar() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        Optional<String> q = Optional.of("Product 1");
        Optional<BigDecimal> minPrice = Optional.of(BigDecimal.ONE);
        Optional<BigDecimal> maxPrice = Optional.of(BigDecimal.TEN);

        Mockito.when(productService.search(q, q, minPrice, maxPrice)).thenReturn(products);
        ResponseEntity<List<Product>> response = productController.search(q.get(), minPrice.get(), maxPrice.get());
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deveRetornar200AoRemover() {
        Long idBusca = 1L;
        Mockito.when(productService.getById(idBusca)).thenReturn(Optional.of(new Product()));

        ResponseEntity<?> response = productController.delete(1L);
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deveRetornar404AoRemoverCasoIdInexistente() {
        Long idBusca = 1L;
        Mockito.when(productService.getById(idBusca)).thenReturn(Optional.empty());

        ResponseEntity<?> response = productController.delete(1L);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void deveRetornar201AoCriar() {
        Product productSaved = new Product();
        productSaved.setName("Product 1");
        productSaved.setDescription("product 1");
        productSaved.setPrice(BigDecimal.TEN);

        Product productExpected = new Product();
        productExpected.setName("Product 1");
        productExpected.setDescription("product 1");
        productExpected.setPrice(BigDecimal.TEN);

        Mockito.when(productService.validate(productSaved)).thenReturn(null);
        Mockito.when(productService.save(productSaved)).thenReturn(productExpected);
        ResponseEntity<?> response = productController.save(productSaved);
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deveRetornar400AoCriarCasoNaoTenhaDescription() {
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(BigDecimal.TEN);

        Mockito.when(productService.validate(product)).thenReturn(new ErrorResponse("The description is required"));
        ResponseEntity<?> response = productController.save(product);
        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void deveRetornar400AoCriarPriceSejaNegativo() {
        Product product = new Product();
        product.setName("Product 1");
        product.setDescription("product 1");
        product.setPrice(new BigDecimal("-1"));

        Mockito.when(productService.validate(product)).thenReturn(new ErrorResponse("The price cannot be negative"));
        ResponseEntity<?> response = productController.save(product);
        Assert.assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void deveRetornar200AoAtualizar() {
        Long idBusca = 1L;

        Product oldProduct = new Product();
        oldProduct.setId(1L);
        oldProduct.setName("Product 1");
        oldProduct.setDescription("product 1");
        oldProduct.setPrice(BigDecimal.TEN);

        Product newProduct = new Product();
        newProduct.setId(1L);
        newProduct.setName("Product 1");
        newProduct.setDescription("product 1");
        newProduct.setPrice(BigDecimal.ONE);

        Mockito.when(productService.getById(idBusca)).thenReturn(Optional.of(oldProduct));
        Mockito.when(productService.validate(newProduct)).thenReturn(null);
        Mockito.when(productService.update(newProduct, 1L)).thenReturn(newProduct);
        ResponseEntity<?> response = productController.update(newProduct, 1L);
        Assert.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deveRetornar404AoAtualizarCasoIdNaoExistir() {
        Long idBusca = 1L;

        Product newProduct = new Product();
        newProduct.setId(1L);
        newProduct.setName("Product 1");
        newProduct.setDescription("product 1");
        newProduct.setPrice(BigDecimal.ONE);

        Mockito.when(productService.getById(idBusca)).thenReturn(Optional.empty());
        ResponseEntity<?> response = productController.update(newProduct, 1L);
        Assert.assertEquals(404, response.getStatusCodeValue());
    }
}
