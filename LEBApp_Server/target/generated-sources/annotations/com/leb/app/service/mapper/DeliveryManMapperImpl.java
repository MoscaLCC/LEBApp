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
    date = "2021-05-14T22:42:45+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class DeliveryManMapperImpl implements DeliveryManMapper {

    @Autowired
    private PointMapper pointMapper;

    @Override
    public DeliveryMan toEntity(DeliveryManDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DeliveryMan deliveryMan = new DeliveryMan();

        deliveryMan.id( dto.getId() );
        deliveryMan.setName( dto.getName() );
        deliveryMan.setEmail( dto.getEmail() );
        deliveryMan.setPhoneNumber( dto.getPhoneNumber() );
        deliveryMan.setNif( dto.getNif() );
        deliveryMan.setNib( dto.getNib() );
        deliveryMan.setBirthday( dto.getBirthday() );
        deliveryMan.setAddress( dto.getAddress() );
        deliveryMan.setPhoto( dto.getPhoto() );
        deliveryMan.setOpeningTime( dto.getOpeningTime() );
        deliveryMan.setNumberOfDeliveries( dto.getNumberOfDeliveries() );
        deliveryMan.setNumberOfKm( dto.getNumberOfKm() );
        deliveryMan.setReceivedValue( dto.getReceivedValue() );
        deliveryMan.setValueToReceive( dto.getValueToReceive() );
        deliveryMan.setRanking( dto.getRanking() );
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
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getNif() != null ) {
            entity.setNif( dto.getNif() );
        }
        if ( dto.getNib() != null ) {
            entity.setNib( dto.getNib() );
        }
        if ( dto.getBirthday() != null ) {
            entity.setBirthday( dto.getBirthday() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getPhoto() != null ) {
            entity.setPhoto( dto.getPhoto() );
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

        deliveryManDTO.setPoint( pointMapper.toDtoId( s.getPoint() ) );
        deliveryManDTO.setId( s.getId() );
        deliveryManDTO.setName( s.getName() );
        deliveryManDTO.setEmail( s.getEmail() );
        deliveryManDTO.setPhoneNumber( s.getPhoneNumber() );
        deliveryManDTO.setNif( s.getNif() );
        deliveryManDTO.setNib( s.getNib() );
        deliveryManDTO.setBirthday( s.getBirthday() );
        deliveryManDTO.setAddress( s.getAddress() );
        deliveryManDTO.setPhoto( s.getPhoto() );
        deliveryManDTO.setOpeningTime( s.getOpeningTime() );
        deliveryManDTO.setNumberOfDeliveries( s.getNumberOfDeliveries() );
        deliveryManDTO.setNumberOfKm( s.getNumberOfKm() );
        deliveryManDTO.setReceivedValue( s.getReceivedValue() );
        deliveryManDTO.setValueToReceive( s.getValueToReceive() );
        deliveryManDTO.setRanking( s.getRanking() );

        return deliveryManDTO;
    }
}
