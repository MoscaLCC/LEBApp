package com.leb.app.service.mapper;

import com.leb.app.domain.*;
import com.leb.app.service.dto.ZoneDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Zone} and its DTO {@link ZoneDTO}.
 */
@Mapper(componentModel = "spring", uses = { TransporterMapper.class })
public interface ZoneMapper extends EntityMapper<ZoneDTO, Zone> {
    @Mapping(target = "transporters", source = "transporters", qualifiedByName = "idSet")
    ZoneDTO toDto(Zone s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ZoneDTO toDtoId(Zone zone);

    @Mapping(target = "removeTransporters", ignore = true)
    Zone toEntity(ZoneDTO zoneDTO);
}
