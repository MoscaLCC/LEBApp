package com.leb.app.service.mapper;

import com.leb.app.domain.*;
import com.leb.app.service.dto.RequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Request} and its DTO {@link RequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { DimensionsMapper.class, RidePathMapper.class, ProducerMapper.class })
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {
    @Mapping(target = "dimensions", source = "dimensions", qualifiedByName = "id")
    @Mapping(target = "ridePath", source = "ridePath", qualifiedByName = "id")
    @Mapping(target = "producer", source = "producer", qualifiedByName = "id")
    RequestDTO toDto(Request s);
}
