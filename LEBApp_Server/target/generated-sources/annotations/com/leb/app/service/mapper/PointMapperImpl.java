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
    date = "2021-11-02T22:49:44+0000",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.50.v20210914-1429, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class PointMapperImpl implements PointMapper {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Point toEntity(PointDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Point point = new Point();

        point.setId( dto.getId() );
        point.setOpeningTime( dto.getOpeningTime() );
        point.setClosingTime( dto.getClosingTime() );
        point.setAddress( dto.getAddress() );
        point.setNumberOfDeliveries( dto.getNumberOfDeliveries() );
        point.ownerPoint( userInfoMapper.toEntity( dto.getOwnerPoint() ) );

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
            entity.setId( dto.getId() );
        }
        if ( dto.getOpeningTime() != null ) {
            entity.setOpeningTime( dto.getOpeningTime() );
        }
        if ( dto.getClosingTime() != null ) {
            entity.setClosingTime( dto.getClosingTime() );
        }
        if ( dto.getAddress() != null ) {
            entity.setAddress( dto.getAddress() );
        }
        if ( dto.getNumberOfDeliveries() != null ) {
            entity.setNumberOfDeliveries( dto.getNumberOfDeliveries() );
        }
        if ( dto.getOwnerPoint() != null ) {
            entity.ownerPoint( userInfoMapper.toEntity( dto.getOwnerPoint() ) );
        }
    }

    @Override
    public PointDTO toDto(Point s) {
        if ( s == null ) {
            return null;
        }

        PointDTO pointDTO = new PointDTO();

        pointDTO.setOwnerPoint( userInfoMapper.toDtoId( s.getOwnerPoint() ) );
        pointDTO.setId( s.getId() );
        pointDTO.setOpeningTime( s.getOpeningTime() );
        pointDTO.setClosingTime( s.getClosingTime() );
        pointDTO.setAddress( s.getAddress() );
        pointDTO.setNumberOfDeliveries( s.getNumberOfDeliveries() );

        return pointDTO;
    }
}
