package com.leb.app.service.mapper;

import com.leb.app.domain.UserInfo;
import com.leb.app.service.dto.UserInfoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-05T00:37:56+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210618-1653, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class UserInfoMapperImpl implements UserInfoMapper {

    @Override
    public UserInfo toEntity(UserInfoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserInfo userInfo = new UserInfo();

        userInfo.id( dto.getId() );
        userInfo.setPhoneNumber( dto.getPhoneNumber() );
        userInfo.setNib( dto.getNib() );
        userInfo.setNif( dto.getNif() );
        userInfo.setBirthday( dto.getBirthday() );
        userInfo.setAdress( dto.getAdress() );

        return userInfo;
    }

    @Override
    public UserInfoDTO toDto(UserInfo entity) {
        if ( entity == null ) {
            return null;
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        userInfoDTO.setId( entity.getId() );
        userInfoDTO.setPhoneNumber( entity.getPhoneNumber() );
        userInfoDTO.setNib( entity.getNib() );
        userInfoDTO.setNif( entity.getNif() );
        userInfoDTO.setBirthday( entity.getBirthday() );
        userInfoDTO.setAdress( entity.getAdress() );

        return userInfoDTO;
    }

    @Override
    public List<UserInfo> toEntity(List<UserInfoDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserInfo> list = new ArrayList<UserInfo>( dtoList.size() );
        for ( UserInfoDTO userInfoDTO : dtoList ) {
            list.add( toEntity( userInfoDTO ) );
        }

        return list;
    }

    @Override
    public List<UserInfoDTO> toDto(List<UserInfo> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserInfoDTO> list = new ArrayList<UserInfoDTO>( entityList.size() );
        for ( UserInfo userInfo : entityList ) {
            list.add( toDto( userInfo ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(UserInfo entity, UserInfoDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getNib() != null ) {
            entity.setNib( dto.getNib() );
        }
        if ( dto.getNif() != null ) {
            entity.setNif( dto.getNif() );
        }
        if ( dto.getBirthday() != null ) {
            entity.setBirthday( dto.getBirthday() );
        }
        if ( dto.getAdress() != null ) {
            entity.setAdress( dto.getAdress() );
        }
    }

    @Override
    public UserInfoDTO toDtoId(UserInfo userInfo) {
        if ( userInfo == null ) {
            return null;
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        userInfoDTO.setId( userInfo.getId() );

        return userInfoDTO;
    }
}
