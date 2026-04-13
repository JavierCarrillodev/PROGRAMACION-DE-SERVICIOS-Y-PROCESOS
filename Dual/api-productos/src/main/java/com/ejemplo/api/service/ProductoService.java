package com.ejemplo.api.service;

import com.ejemplo.api.dto.ProductoDTO;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<ProductoDTO> findAll();

    ProductoDTO save(ProductoDTO productoDTO);

    Optional<ProductoDTO> findById(Long id);

    Optional<ProductoDTO> update(Long id, ProductoDTO productoDTO);

    boolean delete(Long id);
}
