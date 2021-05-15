package com.leb.app.service.mapper;

import com.leb.app.domain.*;
import com.leb.app.service.dto.ProducerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producer} and its DTO {@link ProducerDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface ProducerMapper extends EntityMapper<ProducerDTO, Producer> {
    @Mapping(target = "userInfo", source = "userInfo", qualifiedByName = "id")
    ProducerDTO toDto(Producer s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProducerDTO toDtoId(Producer producer);
}
