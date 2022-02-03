package com.leb.app.service.mapper;

import com.leb.app.domain.Request;
import com.leb.app.service.dto.RequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Request} and its DTO {@link RequestDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class })
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {
    RequestDTO toDto(Request s);
}
