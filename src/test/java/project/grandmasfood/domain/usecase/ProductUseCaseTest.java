package project.grandmasfood.domain.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import project.grandmasfood.domain.exceptions.*;
import project.grandmasfood.domain.models.Category;
import org.mockito.junit.jupiter.MockitoExtension;
import project.grandmasfood.domain.models.Product;
import project.grandmasfood.domain.spi.IProductPersistencePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {

    @Mock
    private IProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductUseCase productUseCase;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(
                "CHEESE BURGER BIG COMBO",
                Category.HAMBURGER,
                "Delicious cheese burger with bacon and fries",
                21008.41f,
                true

        );
    }


    @Test
    void updateProduct_ShouldSucceed_WhenUpdatingWithSameFantasyName() {
        UUID uuid = testProduct.getUuid();
        Product updatedProduct = new Product(
                testProduct.getFantasyName(), // Same fantasy name
                Category.HAMBURGER,
                "Updated description",
                20000.00f,
                false
        );
        updatedProduct.setUuid(uuid);

        when(productPersistencePort.getProductByUuid(uuid))
                .thenReturn(Optional.of(testProduct));

        assertDoesNotThrow(() -> productUseCase.updateProduct(updatedProduct));
        verify(productPersistencePort).updateProduct(updatedProduct);
    }



    @Test
    void isValidPrice_ShouldReturnFalse_WhenPriceHasThreeDecimals() {
        testProduct.setPrice(100.555f);
        assertThrows(InvalidProductDataPriceException.class,
                () -> productUseCase.createProduct(testProduct));
    }


    @Test
    void createProduct() {
        when(productPersistencePort.getProductByFantasyName(testProduct.getFantasyName()))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, times(1)).createProduct(testProduct);
    }

    @Test
    void createProduct_ShouldThrowException_WhenDuplicateFantasyName() {
        when(productPersistencePort.getProductByFantasyName(testProduct.getFantasyName()))
                .thenReturn(Optional.of(testProduct));

        assertThrows(DuplicateFantasyNameException.class, () -> productUseCase.createProduct(testProduct));

        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }


    @Test
    void updateProduct_Success() {
        // Original product
        Product existingProduct = new Product(
                "ORIGINAL BURGER",
                Category.HAMBURGER,
                "Original description",
                15000.00f,
                true
        );
        UUID uuid = existingProduct.getUuid();

        // Updated product data
        Product updatedProduct = new Product(
                "UPDATED BURGER",
                Category.HAMBURGER,
                "Updated description",
                18000.00f,
                false
        );
        updatedProduct.setUuid(uuid);

        // Mock behavior
        when(productPersistencePort.getProductByUuid(uuid))
                .thenReturn(Optional.of(existingProduct));
        when(productPersistencePort.getProductByFantasyName(updatedProduct.getFantasyName()))
                .thenReturn(Optional.empty());

        // Execute update
        assertDoesNotThrow(() -> productUseCase.updateProduct(updatedProduct));

        // Verify the update was called
        verify(productPersistencePort).updateProduct(updatedProduct);
        verify(productPersistencePort).getProductByUuid(uuid);
        verify(productPersistencePort).getProductByFantasyName(updatedProduct.getFantasyName());
    }


    @Test
    void getProductById_Success() {
        Long id = 1L;
        when(productPersistencePort.getProductById(id)).thenReturn(Optional.of(testProduct));

        Optional<Product> result = productUseCase.getProductById(id);

        assertTrue(result.isPresent());
        assertEquals(testProduct.getFantasyName(), result.get().getFantasyName());
    }

    @Test
    void getProductByUuid_ShouldThrowException_WhenProductNotFound() {
        Long id = 22222L;
        when(productPersistencePort.getProductById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productUseCase.getProductById(id));
    }


    @Test
    void updateProduct_ShouldThrowException_WhenProductNotFound() {
        when(productPersistencePort.getProductByUuid(testProduct.getUuid()))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productUseCase.updateProduct(testProduct));
    }

    @Test
    void updateProduct_ShouldThrowException_WhenDuplicateFantasyName() {
        Product anotherProduct = new Product(
                "NEW BURGER",
                Category.HAMBURGER,
                "Updated Description",
                19999.99f,
                false
        );

        when(productPersistencePort.getProductByUuid(any(UUID.class)))
                .thenReturn(Optional.of(testProduct));
        when(productPersistencePort.getProductByFantasyName(anotherProduct.getFantasyName()))
                .thenReturn(Optional.of(anotherProduct));

        assertThrows(DuplicateFantasyNameException.class, () -> productUseCase.updateProduct(anotherProduct));
    }

    @Test
    void deleteProduct_Success() {
        when(productPersistencePort.getProductByUuid(testProduct.getUuid()))
                .thenReturn(Optional.of(testProduct));

        assertDoesNotThrow(() -> productUseCase.deleteProduct(testProduct.getUuid()));

        verify(productPersistencePort, times(1)).deleteProduct(testProduct);
    }

    @Test
    void deleteProduct_ShouldThrowException_WhenProductNotFound() {
        when(productPersistencePort.getProductByUuid(testProduct.getUuid()))
                .thenReturn(Optional.empty());

        UUID productUuid = testProduct.getUuid();
        assertThrows(ProductNotFoundException.class, () -> productUseCase.deleteProduct(productUuid));
    }

    @Test
    void searchProductsByFantasyName_Success() {
        when(productPersistencePort.searchProductsByFantasyName("BURGER"))
                .thenReturn(List.of(testProduct));

        List<Product> products = productUseCase.searchProductsByFantasyName("BURGER");

        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
    }

    @Test
    void searchProductsByFantasyName_ShouldThrowException_WhenNoProductsFound() {
        when(productPersistencePort.searchProductsByFantasyName("BURGER"))
                .thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> productUseCase.searchProductsByFantasyName("BURGER"));
    }

    @Test
    void searchProductsByFantasyName_ShouldThrowException_WhenKeywordIsEmpty() {
        assertThrows(InvalidProductDataException.class, () -> productUseCase.searchProductsByFantasyName(""));
    }

    @Test
    void createProduct_ShouldThrowException_WhenNameExceedsMaxLength() {
        testProduct.setFantasyName("A".repeat(256));

        assertThrows(InvalidProductDataNameException.class, () -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenDescriptionExceedsMaxLength() {
        testProduct.setDescription("A".repeat(512));

        assertThrows(InvalidProductDataDescriptionException.class, () -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenPriceIsNegative() {
        testProduct.setPrice(-1.0f);

        assertThrows(InvalidProductDataPriceException.class, () -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowException_WhenPriceHasInvalidDecimals() {
        testProduct.setPrice(10.999f);

        assertThrows(InvalidProductDataPriceException.class, () -> productUseCase.createProduct(testProduct));
        verify(productPersistencePort, never()).createProduct(any(Product.class));
    }

    @Test
    void createProduct_ShouldUppercaseFantasyName() {
        testProduct.setFantasyName("burger");
        when(productPersistencePort.getProductByFantasyName("BURGER")).thenReturn(Optional.empty());

        productUseCase.createProduct(testProduct);

        assertEquals("BURGER", testProduct.getFantasyName());
        verify(productPersistencePort).createProduct(testProduct);
    }

    @Test
    void getAllProducts_Success() {
        List<Product> expectedProducts = List.of(testProduct);
        when(productPersistencePort.getAllProducts()).thenReturn(expectedProducts);

        List<Product> result = productUseCase.getAllProducts();

        assertEquals(expectedProducts, result);
        verify(productPersistencePort).getAllProducts();
    }


    @Test
    void isValidPrice_ShouldReturnTrue_WhenPriceHasNoDecimals() {
        testProduct.setPrice(100f);
        assertDoesNotThrow(() -> productUseCase.createProduct(testProduct));
    }

    @Test
    void isValidPrice_ShouldReturnTrue_WhenPriceHasTwoDecimals() {
        testProduct.setPrice(100.50f);
        assertDoesNotThrow(() -> productUseCase.createProduct(testProduct));
    }
}