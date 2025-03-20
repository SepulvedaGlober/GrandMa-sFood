package project.grandmasfood.infrastructure.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.grandmasfood.application.dto.product.ProductRequestDto;
import project.grandmasfood.application.dto.product.ProductResponseDto;
import project.grandmasfood.application.handler.interfaces.IProductHandler;
import project.grandmasfood.domain.models.Category;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRestControllerTest {

    @Mock
    private IProductHandler productHandler;

    @InjectMocks
    private ProductRestController productRestController;

    private ProductRequestDto productRequestDto;
    private ProductResponseDto productResponseDto;
    private UUID uuid;

    @BeforeEach
    void setUp() {
        uuid = UUID.randomUUID();

        productRequestDto = new ProductRequestDto();
        productRequestDto.setFantasyName("Test Burger");
        productRequestDto.setDescription("Test Description");
        productRequestDto.setCategory(Category.HAMBURGER);
        productRequestDto.setPrice(15000.00f);
        productRequestDto.setAvailable(true);

        productResponseDto = new ProductResponseDto();
        productResponseDto.setUuid(uuid);
        productResponseDto.setFantasyName("Test Burger");
        productResponseDto.setDescription("Test Description");
        productResponseDto.setCategory(Category.HAMBURGER);
        productResponseDto.setPrice(15000.00f);
        productResponseDto.setAvailable(true);
    }

    @Test
    void createProduct_Success() {
        when(productHandler.createProduct(any(ProductRequestDto.class)))
                .thenReturn(productResponseDto);

        ResponseEntity<ProductResponseDto> response =
                productRestController.createProduct(productRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productResponseDto, response.getBody());
        verify(productHandler).createProduct(productRequestDto);
    }

    @Test
    void createProduct_BadRequest() {
        when(productHandler.createProduct(any(ProductRequestDto.class)))
                .thenThrow(IllegalArgumentException.class);

        ResponseEntity<ProductResponseDto> response =
                productRestController.createProduct(productRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(productHandler).createProduct(productRequestDto);
    }

    @Test
    void getProductByUuid_Success() {
        when(productHandler.getProductByUuid(uuid))
                .thenReturn(Optional.of(productResponseDto));

        ResponseEntity<ProductResponseDto> response =
                productRestController.getProductByUuid(uuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(productResponseDto, response.getBody());
        verify(productHandler).getProductByUuid(uuid);
    }

    @Test
    void getProductByUuid_NotFound() {
        when(productHandler.getProductByUuid(uuid))
                .thenReturn(Optional.empty());

        ResponseEntity<ProductResponseDto> response =
                productRestController.getProductByUuid(uuid);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(productHandler).getProductByUuid(uuid);
    }

    @Test
    void updateProduct_Success() {
        doNothing().when(productHandler)
                .updateProduct(eq(uuid), any(ProductRequestDto.class));

        ResponseEntity<ProductResponseDto> response =
                productRestController.updateProduct(uuid, productRequestDto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(productHandler).updateProduct(uuid, productRequestDto);
    }

    @Test
    void updateProduct_BadRequest() {
        doThrow(IllegalArgumentException.class)
                .when(productHandler).updateProduct(eq(uuid), any(ProductRequestDto.class));

        ResponseEntity<ProductResponseDto> response =
                productRestController.updateProduct(uuid, productRequestDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(productHandler).updateProduct(uuid, productRequestDto);
    }

    @Test
    void deleteProduct_Success() {
        doNothing().when(productHandler).deleteProduct(uuid);

        ResponseEntity<Void> response = productRestController.deleteProduct(uuid);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(productHandler).deleteProduct(uuid);
    }

    @Test
    void getAllProducts_Success() {
        List<ProductResponseDto> products =
                Arrays.asList(productResponseDto, productResponseDto);
        when(productHandler.getAllProducts()).thenReturn(products);

        ResponseEntity<List<ProductResponseDto>> response =
                productRestController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(productHandler).getAllProducts();
    }

    @Test
    void getAllProducts_NoContent() {
        when(productHandler.getAllProducts()).thenReturn(List.of());

        ResponseEntity<List<ProductResponseDto>> response =
                productRestController.getAllProducts();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(productHandler).getAllProducts();
    }

    @Test
    void searchProducts_Success() {
        String keyword = "burger";
        List<ProductResponseDto> products =
                Arrays.asList(productResponseDto, productResponseDto);
        when(productHandler.searchProductsByFantasyName(keyword))
                .thenReturn(products);

        ResponseEntity<List<ProductResponseDto>> response =
                productRestController.searchProducts(keyword);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(productHandler).searchProductsByFantasyName(keyword);
    }

    @Test
    void deleteProduct_ThrowsException() {
        doThrow(RuntimeException.class)
                .when(productHandler).deleteProduct(uuid);

        assertThrows(RuntimeException.class, () ->
                productRestController.deleteProduct(uuid));

        verify(productHandler).deleteProduct(uuid);
    }
}