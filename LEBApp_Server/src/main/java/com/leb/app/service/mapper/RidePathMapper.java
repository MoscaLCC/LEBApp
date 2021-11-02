package com.leb.app.service.mapper;

import com.leb.app.domain.RidePath;
import com.leb.app.service.dto.RidePathDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RidePath} and its DTO {@link RidePathDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RidePathMapper extends EntityMapper<RidePathDTO, RidePath> {}
