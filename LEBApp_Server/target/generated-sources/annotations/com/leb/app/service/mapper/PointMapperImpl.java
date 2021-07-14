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
    date = "2021-07-15T00:05:03+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210618-1653, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class PointMapperImpl implements PointMapper {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ZoneMapper zoneMapper;

    @Override
    public Point toEntity(PointDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Point point = new Point();

        point.id( dto.getId() );
        point.setOpeningTime( dto.getOpeningTime() );
        point.setNumberOfDeliveries( dto.getNumberOfDeliveries() );
        point.setReceivedValue( dto.getReceivedValue() );
        point.setValueToReceive( dto.getValueToReceive() );
        point.setRanking( dto.getRanking() );
        point.setUserInfo( userInfoMapper.toEntity( dto.getUserInfo() ) );
        point.setClosingTime( dto.getClosingTime() );
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
        if ( dto.getUserInfo() != null ) {
            entity.setUserInfo( userInfoMapper.toEntity( dto.getUserInfo() ) );
        }
        if ( dto.getClosingTime() != null ) {
            entity.setClosingTime( dto.getClosingTime() );
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

        pointDTO.setUserInfo( userInfoMapper.toDtoId( s.getUserInfo() ) );
        pointDTO.setZone( zoneMapper.toDtoId( s.getZone() ) );
        pointDTO.setId( s.getId() );
        pointDTO.setClosingTime( s.getClosingTime() );
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
