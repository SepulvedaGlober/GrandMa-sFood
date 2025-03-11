package project.grandmasfood.infrastructure.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.grandmasfood.application.dto.product.ProductRequestDto;
import project.grandmasfood.application.dto.product.ProductResponseDto;
import project.grandmasfood.application.handler.interfaces.IProductHandler;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final IProductHandler productHandler;


    //CREATE PRODUCT
    @Operation(summary = "Create a new product", description = "Endpoint for creating a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created", content = @Content(schema = @Schema(implementation = ProductResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict: A product with the same name already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        try{
            ProductResponseDto productResponse = productHandler.createProduct(productRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);

        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //SEARCH PRODUCT BY UUID
    @Operation(summary = "Search product", description = "Search for a product by UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found", content = @Content(schema = @Schema(implementation = ProductResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid UUID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{idProduct}")
    public ResponseEntity<ProductResponseDto> getProductByUuid(@PathVariable("idProduct") Long idProduct) {
        Optional<ProductResponseDto> productResponse = productHandler.getProductById(idProduct);
        return productResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    //UPDATE PRODUCT BY UUID
    @Operation(summary = "Update an existing product", description = "Updates a product in the database using its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product updated correctly", content = @Content(schema = @Schema(implementation = ProductResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "Conflict : A product with the same name already exists"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{idProduct}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long idProduct, @RequestBody ProductRequestDto productRequestDto) {
        try{
            productHandler.updateProduct(idProduct, productRequestDto);
            return ResponseEntity.noContent().build();
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    //DELETE PRODUCT
    @Operation(summary = "Delete product", description = "Deletes a product from the database using its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted correctly"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productHandler.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //GET ALL PRODUCTS
    @Operation(summary = "List all products", description = "Endpoint to list all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all products", content = @Content(schema = @Schema(implementation = ProductResponseDto.class))),
            @ApiResponse(responseCode = "204", description = "No products found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productHandler.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    //SEARCH PRODUCTS BY FANTASY NAME
    @Operation(summary = "Search products by fantasy name", description = "Search for products that contain the keyword in their fantasy name (case-insensitive).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found", content = @Content(schema = @Schema(implementation = ProductResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Keyword is empty"),
            @ApiResponse(responseCode = "404", description = "No products found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProducts(@RequestParam(name = "q") String keyword) {
        List<ProductResponseDto> products = productHandler.searchProductsByFantasyName(keyword);
        return ResponseEntity.ok(products);
    }

}
