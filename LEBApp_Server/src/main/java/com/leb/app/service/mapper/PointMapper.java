package com.leb.app.service.mapper;

import com.leb.app.domain.Point;
import com.leb.app.service.dto.PointDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Point} and its DTO {@link PointDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface PointMapper extends EntityMapper<PointDTO, Point> {
    @Mapping(target = "ownerPoint", source = "ownerPoint", qualifiedByName = "id")
    PointDTO toDto(Point s);
}
