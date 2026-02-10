package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("test-id-123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(product)).thenReturn(product);

        Product result = productService.create(product);

        assertNotNull(result);
        assertEquals("Test Product", result.getProductName());
        assertEquals(10, result.getProductQuantity());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductQuantity(5);
        productList.add(product2);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertEquals("Test Product", result.get(0).getProductName());
        assertEquals("Product 2", result.get(1).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        when(productRepository.findById("test-id-123")).thenReturn(product);

        Product result = productService.findById("test-id-123");

        assertNotNull(result);
        assertEquals("test-id-123", result.getProductId());
        assertEquals("Test Product", result.getProductName());
        verify(productRepository, times(1)).findById("test-id-123");
    }

    @Test
    void testUpdateProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(20);

        when(productRepository.update("test-id-123", updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update("test-id-123", updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Product", result.getProductName());
        assertEquals(20, result.getProductQuantity());
        verify(productRepository, times(1)).update("test-id-123", updatedProduct);
    }

    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).delete("test-id-123");

        productService.delete("test-id-123");

        verify(productRepository, times(1)).delete("test-id-123");
    }

    @Test
    void testFindProductById_NotFound() {
        when(productRepository.findById("nonexistent-id")).thenReturn(null);

        Product result = productService.findById("nonexistent-id");

        assertNull(result);
        verify(productRepository, times(1)).findById("nonexistent-id");
    }
}