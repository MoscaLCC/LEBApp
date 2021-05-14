package com.leb.app.service.mapper;

import com.leb.app.domain.Point;
import com.leb.app.service.dto.PointDTO;
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
public class PointMapperImpl implements PointMapper {

    @Autowired
    private ZoneMapper zoneMapper;

    @Override
    public Point toEntity(PointDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Point point = new Point();

        point.id( dto.getId() );
        point.setName( dto.getName() );
        point.setEmail( dto.getEmail() );
        point.setPhoneNumber( dto.getPhoneNumber() );
        point.setNib( dto.getNib() );
        point.setNif( dto.getNif() );
        point.setAddress( dto.getAddress() );
        point.setOpeningTime( dto.getOpeningTime() );
        point.setNumberOfDeliveries( dto.getNumberOfDeliveries() );
        point.setReceivedValue( dto.getReceivedValue() );
        point.setValueToReceive( dto.getValueToReceive() );
        point.setRanking( dto.getRanking() );
        point.setZone( zoneMapper.toEntity( dto.getZone() ) );

        return point;
    }

    @Override
    public List<Point> toEntity(List<PointDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Point> list = new ArrayList<Point>( dtoList.size() );
        for ( PointDTO pointDTO : dtoList ) {
            list.add( toEntity( pointDTO ) );
        }

        return list;
    }

    @Override
    public List<PointDTO> toDto(List<Point> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PointDTO> list = new ArrayList<PointDTO>( entityList.size() );
        for ( Point point : entityList ) {
            list.add( toDto( point ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Point entity, PointDTO dto) {
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
        if ( dto.getNib() != null ) {
            entity.setNib( dto.getNib() );
        }
        if ( dto.getNif() != null ) {
            entity.setNif( dto.getNif() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getOpeningTime() != null ) {
            entity.setOpeningTime( dto.getOpeningTime() );
        }
        if ( dto.getNumberOfDeliveries() != null ) {
            entity.setNumberOfDeliveries( dto.getNumberOfDeliveries() );
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
        if ( dto.getZone() != null ) {
            entity.setZone( zoneMapper.toEntity( dto.getZone() ) );
        }
    }

    @Override
    public PointDTO toDto(Point s) {
        if ( s == null ) {
            return null;
        }

        PointDTO pointDTO = new PointDTO();

        pointDTO.setZone( zoneMapper.toDtoId( s.getZone() ) );
        pointDTO.setId( s.getId() );
        pointDTO.setName( s.getName() );
        pointDTO.setEmail( s.getEmail() );
        pointDTO.setPhoneNumber( s.getPhoneNumber() );
        pointDTO.setNib( s.getNib() );
        pointDTO.setNif( s.getNif() );
        pointDTO.setAddress( s.getAddress() );
        pointDTO.setOpeningTime( s.getOpeningTime() );
        pointDTO.setNumberOfDeliveries( s.getNumberOfDeliveries() );
        pointDTO.setReceivedValue( s.getReceivedValue() );
        pointDTO.setValueToReceive( s.getValueToReceive() );
        pointDTO.setRanking( s.getRanking() );

        return pointDTO;
    }

    @Override
    public PointDTO toDtoId(Point point) {
        if ( point == null ) {
            return null;
        }

        PointDTO pointDTO = new PointDTO();

        pointDTO.setId( point.getId() );

        return pointDTO;
    }
}
