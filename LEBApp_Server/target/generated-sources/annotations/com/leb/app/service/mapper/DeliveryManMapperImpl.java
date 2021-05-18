package com.leb.app.service.mapper;

import com.leb.app.domain.DeliveryMan;
import com.leb.app.service.dto.DeliveryManDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-18T23:54:36+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class DeliveryManMapperImpl implements DeliveryManMapper {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private PointMapper pointMapper;

    @Override
    public DeliveryMan toEntity(DeliveryManDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DeliveryMan deliveryMan = new DeliveryMan();

        deliveryMan.id( dto.getId() );
        deliveryMan.setOpeningTime( dto.getOpeningTime() );
        deliveryMan.setNumberOfDeliveries( dto.getNumberOfDeliveries() );
        deliveryMan.setNumberOfKm( dto.getNumberOfKm() );
        deliveryMan.setReceivedValue( dto.getReceivedValue() );
        deliveryMan.setValueToReceive( dto.getValueToReceive() );
        deliveryMan.setRanking( dto.getRanking() );
        deliveryMan.setUserInfo( userInfoMapper.toEntity( dto.getUserInfo() ) );
        deliveryMan.setPoint( pointMapper.toEntity( dto.getPoint() ) );

        return deliveryMan;
    }

    @Override
    public List<DeliveryMan> toEntity(List<DeliveryManDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DeliveryMan> list = new ArrayList<DeliveryMan>( dtoList.size() );
        for ( DeliveryManDTO deliveryManDTO : dtoList ) {
            list.add( toEntity( deliveryManDTO ) );
        }

        return list;
    }

    @Override
    public List<DeliveryManDTO> toDto(List<DeliveryMan> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DeliveryManDTO> list = new ArrayList<DeliveryManDTO>( entityList.size() );
        for ( DeliveryMan deliveryMan : entityList ) {
            list.add( toDto( deliveryMan ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(DeliveryMan entity, DeliveryManDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getOpeningTime() != null ) {
            entity.setOpeningTime( dto.getOpeningTime() );
        }
        if ( dto.getNumberOfDeliveries() != null ) {
            entity.setNumberOfDeliveries( dto.getNumberOfDeliveries() );
        }
        if ( dto.getNumberOfKm() != null ) {
            entity.setNumberOfKm( dto.getNumberOfKm() );
        }
        if ( dto.getReceivedValue() != null ) {
            entity.setReceivedValue( dto.getReceivedValue() );
        }
        if ( dto.getValueToReceive() != null ) {
            entity.setValueToReceive( dto.getValueToReceive() );
        }
        if ( dto.getRanking() != null ) {
            entity.setRanking( dto.getRanking() );
        }
        if ( dto.getUserInfo() != null ) {
            entity.setUserInfo( userInfoMapper.toEntity( dto.getUserInfo() ) );
        }
        if ( dto.getPoint() != null ) {
            entity.setPoint( pointMapper.toEntity( dto.getPoint() ) );
        }
    }

    @Override
    public DeliveryManDTO toDto(DeliveryMan s) {
        if ( s == null ) {
            return null;
        }

        DeliveryManDTO deliveryManDTO = new DeliveryManDTO();

        deliveryManDTO.setUserInfo( userInfoMapper.toDtoId( s.getUserInfo() ) );
        deliveryManDTO.setPoint( pointMapper.toDtoId( s.getPoint() ) );
        deliveryManDTO.setId( s.getId() );
        deliveryManDTO.setOpeningTime( s.getOpeningTime() );
        deliveryManDTO.setNumberOfDeliveries( s.getNumberOfDeliveries() );
        deliveryManDTO.setNumberOfKm( s.getNumberOfKm() );
        deliveryManDTO.setReceivedValue( s.getReceivedValue() );
        deliveryManDTO.setValueToReceive( s.getValueToReceive() );
        deliveryManDTO.setRanking( s.getRanking() );

        return deliveryManDTO;
    }
}
