package io.github.dan7arievlis.autoflextest.controller.dto.mapper;

import io.github.dan7arievlis.autoflextest.controller.dto.product.ProductRequestDTO;
import io.github.dan7arievlis.autoflextest.controller.dto.product.ProductResponseDTO;
import io.github.dan7arievlis.autoflextest.model.Product;
    import org.mapstruct.*;

import java.math.BigDecimal;

@Mapper(
        config = CentralMapperConfig.class,
        uses = InputMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    @Mapping(target = "inputs", ignore = true)
    Product requestToEntity(ProductRequestDTO dto);

    ProductResponseDTO entityToResponse(Product product, Integer maxProduction, BigDecimal totalValue);

    ProductResponseDTO entityToResponse(Product product);
}
