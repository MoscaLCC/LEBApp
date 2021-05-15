package com.leb.app.service.mapper;

import com.leb.app.domain.*;
import com.leb.app.service.dto.DeliveryManDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DeliveryMan} and its DTO {@link DeliveryManDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, PointMapper.class })
public interface DeliveryManMapper extends EntityMapper<DeliveryManDTO, DeliveryMan> {
    @Mapping(target = "userInfo", source = "userInfo", qualifiedByName = "id")
    @Mapping(target = "point", source = "point", qualifiedByName = "id")
    DeliveryManDTO toDto(DeliveryMan s);
}
