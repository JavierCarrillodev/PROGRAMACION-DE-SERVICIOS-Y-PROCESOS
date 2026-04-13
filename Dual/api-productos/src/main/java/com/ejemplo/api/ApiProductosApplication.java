package com.ejemplo.api;

import com.ejemplo.api.domain.Producto;
import com.ejemplo.api.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiProductosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProductosApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ProductoRepository repository) {
		return args -> {
			repository.save(new Producto(null, "Laptop HP", "Laptop potente para desarrollo", 800.00, 10));
			repository.save(new Producto(null, "iPhone 15", "Smartphone de última generación", 1200.00, 5));
			repository.save(new Producto(null, "Auriculares Sony", "Cancelación de ruido", 150.00, 20));
			repository.save(new Producto(null, "Monitor Dell", "Monitor 27 pulgadas 4K", 300.00, 8));
			repository.save(new Producto(null, "Teclado Mecánico", "Switch Cherry MX Blue", 120.00, 15));
			repository.save(new Producto(null, "Ratón Logitech", "Ergonómico inalámbrico", 90.00, 25));
			repository.save(new Producto(null, "Tablet Samsung", "Galaxy Tab S9", 700.00, 7));
			repository.save(new Producto(null, "Impresora HP", "Multifunción Láser", 200.00, 12));
			repository.save(new Producto(null, "Altavoces Bose", "Sonido envolvente", 250.00, 10));
			repository.save(new Producto(null, "Disco SSD 1TB", "Samsung 990 Pro", 110.00, 30));
		};
	}

}
