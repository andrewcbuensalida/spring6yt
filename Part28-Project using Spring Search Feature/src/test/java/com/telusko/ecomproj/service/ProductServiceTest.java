package com.telusko.ecomproj.service;

import com.telusko.ecomproj.model.Product;
import com.telusko.ecomproj.repo.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Why does it still work without @ExtendWith(MockitoExtension.class)? Maybe because there is a MockitoAnnotations.openMocks(this) in the @BeforeEach setUp method.
class ProductServiceTest {

  @Mock
  private ProductRepo repo;

  @InjectMocks
  private ProductService productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetAllProducts() {
    Product product1 = new Product();
    Product product2 = new Product();
    List<Product> products = Arrays.asList(product1, product2);

    when(repo.findAll()).thenReturn(products);

    List<Product> result = productService.getAllProducts();

    assertEquals(2, result.size());
    verify(repo, times(1)).findAll();
  }

  @Test
  void testGetProduct() {
    Product product = new Product();
    when(repo.findById(1)).thenReturn(Optional.of(product));

    Product result = productService.getProduct(1);

    assertNotNull(result);
    verify(repo, times(1)).findById(1);
  }

  @Test
  void testAddProduct() throws IOException {
    Product product = new Product();
    MultipartFile imageFile = mock(MultipartFile.class);

    when(imageFile.getOriginalFilename()).thenReturn("image.jpg");
    when(imageFile.getContentType()).thenReturn("image/jpeg");
    when(imageFile.getBytes()).thenReturn(new byte[] { 1, 2, 3 });
    when(repo.save(product)).thenReturn(product);

    Product result = productService.addProduct(product, imageFile);

    assertNotNull(result);
    assertEquals("image.jpg", product.getImageName());
    assertEquals("image/jpeg", product.getImageType());
    assertArrayEquals(new byte[] { 1, 2, 3 }, product.getImageData());
    verify(repo, times(1)).save(product);
  }

  @Test
  void testUpdateProduct() throws IOException {
    Product product = new Product();
    MultipartFile imageFile = mock(MultipartFile.class);

    when(imageFile.getOriginalFilename()).thenReturn("image.jpg");
    when(imageFile.getContentType()).thenReturn("image/jpeg");
    when(imageFile.getBytes()).thenReturn(new byte[] { 1, 2, 3 });
    when(repo.save(product)).thenReturn(product);

    Product result = productService.updateProduct(1, product, imageFile);

    assertNotNull(result);
    assertEquals("image.jpg", product.getImageName());
    assertEquals("image/jpeg", product.getImageType());
    assertArrayEquals(new byte[] { 1, 2, 3 }, product.getImageData());
    verify(repo, times(1)).save(product);
  }

  @Test
  void testDeleteProduct() {
    doNothing().when(repo).deleteById(1);

    productService.deleteProduct(1);

    verify(repo, times(1)).deleteById(1);
  }

  @Test
  void testSearchProducts() {
    Product product1 = new Product();
    Product product2 = new Product();
    List<Product> products = Arrays.asList(product1, product2);

    when(repo.searchProducts("keyword")).thenReturn(products);

    List<Product> result = productService.searchProducts("keyword");

    assertEquals(2, result.size());
    verify(repo, times(1)).searchProducts("keyword");
  }
}