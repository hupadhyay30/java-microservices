package com.hbu.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbu.productservice.dao.ProductRequest;
import com.hbu.productservice.dao.ProductResponse;
import com.hbu.productservice.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
		
		ProductResponse productResponse = productService.createProduct(productRequest);
		if(productResponse != null)
			return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable int id){
		
		ProductResponse productResponse = productService.getProductByID(id);
		if(productResponse != null)
			return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts(){
		
		List<ProductResponse> products = productService.getAllProducts();
		if(products != null)
			return new ResponseEntity<>(products, HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
