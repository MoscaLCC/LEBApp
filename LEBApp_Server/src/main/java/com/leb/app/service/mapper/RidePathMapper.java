package com.leb.app.service.mapper;

import com.leb.app.domain.*;
import com.leb.app.service.dto.RidePathDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RidePath} and its DTO {@link RidePathDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RidePathMapper extends EntityMapper<RidePathDTO, RidePath> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RidePathDTO toDtoId(RidePath ridePath);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<RidePathDTO> toDtoIdSet(Set<RidePath> ridePath);
}
