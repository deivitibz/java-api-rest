package com.init.products.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.init.products.dao.ProductsDAO;
import com.init.products.entitys.Product;

@RestController
@RequestMapping("products")

public class ProductsREST {
	
	@Autowired
	private ProductsDAO productDAO;
	
	
	@GetMapping
	public ResponseEntity<List<Product>> getProduct(){
		List<Product> products = productDAO.findAll();
		return ResponseEntity.ok(products);
		//return null;
		
	}
	
	@RequestMapping(value="{productId}") // /products/{productId}
	public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId){
		
		Optional<Product> optionalProduct = productDAO.findById(productId);
		if(!optionalProduct.isPresent()) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.ok(optionalProduct.get());      
		}
	}
	
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		Product newProduct = productDAO.save(product);
		return ResponseEntity.ok(newProduct);
	}
	
	@DeleteMapping(value = "{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId){
		productDAO.deleteById(productId);
		return ResponseEntity.ok("Producto eliminado");
	}
	
	@PutMapping
	public ResponseEntity<Product> editProduct(@RequestBody Product product){
		Optional<Product> optionalProduct = productDAO.findById(product.getId());
		if(!optionalProduct.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			Product updateProduct = optionalProduct.get();
			updateProduct.setName(product.getName());
			productDAO.save(updateProduct);
			return ResponseEntity.ok(updateProduct);
		}
	}
	

	
	
	//@GetMapping // localhost:8080/
	//@RequestMapping(value="hello", method=RequestMethod.GET)
	
	public String hello() {
		return "Hello World!";
	}
}
