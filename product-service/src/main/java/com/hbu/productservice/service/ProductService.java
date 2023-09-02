package com.hbu.productservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hbu.productservice.dao.ProductRequest;
import com.hbu.productservice.dao.ProductResponse;
import com.hbu.productservice.model.Product;
import com.hbu.productservice.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private  final ProductRepository productRepository;
	
	public ProductResponse createProduct(ProductRequest product) {
		
		Product productToSave = Product.builder().name(product.getName())
		.brand(product.getBrand())
		.price(product.getPrice())
		.build();
		
		Product finalProduct = productRepository.save(productToSave);
		
		return returnProductResponse(finalProduct);
	}
	
	public ProductResponse getProductByID(int id) {
		Optional<Product> productOptional = productRepository.findById(id);
		if(productOptional.isPresent())
			return returnProductResponse(productOptional.get());
		else
			return null;
	}
	
	public List<ProductResponse> getAllProducts(){
		List<Product> productList = productRepository.findAll();
		return productList.stream()
			.map(product -> returnProductResponse(product))
			.collect(Collectors.toList());
	}
	
	private ProductResponse returnProductResponse(Product product) {
		
		return ProductResponse.builder().id(product.getId())
			.name(product.getName())
			.brand(product.getBrand())
			.price(product.getPrice())
			.build();
	}

}
