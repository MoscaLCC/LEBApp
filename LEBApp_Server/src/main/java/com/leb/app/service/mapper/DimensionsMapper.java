package com.leb.app.service.mapper;

import com.leb.app.domain.*;
import com.leb.app.service.dto.DimensionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dimensions} and its DTO {@link DimensionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DimensionsMapper extends EntityMapper<DimensionsDTO, Dimensions> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DimensionsDTO toDtoId(Dimensions dimensions);
}
