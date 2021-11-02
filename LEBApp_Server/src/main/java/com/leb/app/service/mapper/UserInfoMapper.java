package com.leb.app.service.mapper;

import com.leb.app.domain.UserInfo;
import com.leb.app.service.dto.UserInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserInfo} and its DTO {@link UserInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserInfoMapper extends EntityMapper<UserInfoDTO, UserInfo> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserInfoDTO toDtoId(UserInfo userInfo);
}
