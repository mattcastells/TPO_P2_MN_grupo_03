package uade.api.tpo_p2_mn_grupo_03.service;

import uade.api.tpo_p2_mn_grupo_03.dto.response.ProductResponseDTO;

public interface IProductService {
    // Define los métodos del servicio aquí
    ProductResponseDTO findById(Long id);
    // Otros métodos según sea necesario
} 