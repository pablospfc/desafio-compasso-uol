package br.com.desafio.compassouol.controller;

import br.com.desafio.compassouol.entity.Product;
import br.com.desafio.compassouol.rest.ErrorResponse;
import br.com.desafio.compassouol.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
@Api(value = "Product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @ApiOperation("Listar todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista de todos os produtos"),
            @ApiResponse(code = 400, message = "Entrada inválida"),
            @ApiResponse(code = 500, message = "Ocorreu alguma exceção na aplicação")
    })
    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return new ResponseEntity<>(
                productService.getAll(),
                HttpStatus.OK
        );
    }

    @ApiOperation("Filtrar produtos pelos parâmetros name, description, min_price e max_price")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto de acordo com o(s) parâmetro(s) passado(s)"),
            @ApiResponse(code = 400, message = "Entrada inválida"),
            @ApiResponse(code = 500, message = "Ocorreu alguma exceção na aplicação")
    })
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam(value = "q", required = false) String q,
                                                @RequestParam(value = "min_price", required = false) BigDecimal minPrice,
                                                @RequestParam(value = "max_price", required = false) BigDecimal maxPrice) {
        return new ResponseEntity<>(
                productService.search(Optional.ofNullable(q), Optional.ofNullable(q),
                        Optional.ofNullable(minPrice), Optional.ofNullable(maxPrice)),
                HttpStatus.OK
        );
    }

    @ApiOperation("Retorna um produto específico pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna o produto"),
            @ApiResponse(code = 404, message = "Prodoto não encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu alguma exceção na aplicação")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getById(@PathVariable("id") Long id) {
        Optional<Product> p = productService.getById(id);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(
                p.get(),
                HttpStatus.OK
        );
    }

    @ApiOperation("Salva um produto")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Retorna o novo produto cadastrado"),
            @ApiResponse(code = 400, message = "Entrada inválida"),
            @ApiResponse(code = 500, message = "Ocorreu alguma exceção na aplicação")
    })
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Product product) {
        ErrorResponse response = productService.validate(product);

        if (response != null) {
            return new ResponseEntity<>(
                    response,
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                productService.save(product),
                HttpStatus.CREATED
        );

    }

    @ApiOperation("Atualiza um produto")
    @ApiResponses( value = {
            @ApiResponse(code = 200, message = "Retorna dados do produto atualizado"),
            @ApiResponse(code = 404, message = "Produto não encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu alguma exceção na aplicação")
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, @PathVariable("id") Long id) {
        Optional<Product> p = productService.getById(id);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ErrorResponse response = productService.validate(product);

        if (response != null) {
            return new ResponseEntity<>(
                    response,
                    HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity<>(
                productService.update(product,id),
                HttpStatus.OK
        );
    }

    @ApiOperation("Remove um produto específico pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "O produto é removido"),
            @ApiResponse(code = 404, message = "Produto não encontrado"),
            @ApiResponse(code = 500, message = "Ocorreu alguma exceção na aplicação")
    })

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        Optional<Product> p = productService.getById(id);

        if (p.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        productService.delete(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );

    }


}
