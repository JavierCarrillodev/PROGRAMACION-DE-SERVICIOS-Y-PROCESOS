package com.ejemplo.api.service;

import com.ejemplo.api.domain.Producto;
import com.ejemplo.api.dto.ProductoDTO;
import com.ejemplo.api.dto.mapper.ProductoMapper;
import com.ejemplo.api.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoServiceImpl(ProductoRepository productoRepository, ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper = productoMapper;
    }

    @Override
    public List<ProductoDTO> findAll() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDTO save(ProductoDTO productoDTO) {
        Producto producto = productoMapper.toEntity(productoDTO);
        return productoMapper.toDTO(productoRepository.save(producto));
    }

    @Override
    public Optional<ProductoDTO> findById(Long id) {
        return productoRepository.findById(id)
                .map(productoMapper::toDTO);
    }

    @Override
    public Optional<ProductoDTO> update(Long id, ProductoDTO productoDTO) {
        return productoRepository.findById(id)
                .map(existingProducto -> {
                    existingProducto.setNombre(productoDTO.getNombre());
                    existingProducto.setDescripcion(productoDTO.getDescripcion());
                    existingProducto.setPrecio(productoDTO.getPrecio());
                    existingProducto.setStock(productoDTO.getStock());
                    return productoMapper.toDTO(productoRepository.save(existingProducto));
                });
    }

    @Override
    public boolean delete(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
