package com.leb.app.service.mapper;

import com.leb.app.domain.*;
import com.leb.app.service.dto.TransporterDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transporter} and its DTO {@link TransporterDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserInfoMapper.class, RidePathMapper.class })
public interface TransporterMapper extends EntityMapper<TransporterDTO, Transporter> {
    @Mapping(target = "userInfo", source = "userInfo", qualifiedByName = "id")
    @Mapping(target = "ridePaths", source = "ridePaths", qualifiedByName = "idSet")
    TransporterDTO toDto(Transporter s);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<TransporterDTO> toDtoIdSet(Set<Transporter> transporter);

    @Mapping(target = "removeRidePath", ignore = true)
    Transporter toEntity(TransporterDTO transporterDTO);
}
