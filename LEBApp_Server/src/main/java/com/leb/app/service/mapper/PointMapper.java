package com.leb.app.service.mapper;

import com.leb.app.domain.*;
import com.leb.app.service.dto.PointDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Point} and its DTO {@link PointDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, ZoneMapper.class })
public interface PointMapper extends EntityMapper<PointDTO, Point> {
    @Mapping(target = "userInfo", source = "userInfo", qualifiedByName = "id")
    @Mapping(target = "zone", source = "zone", qualifiedByName = "id")
    PointDTO toDto(Point s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PointDTO toDtoId(Point point);
}
