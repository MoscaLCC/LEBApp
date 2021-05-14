package com.leb.app.service.mapper;

import com.leb.app.domain.RidePath;
import com.leb.app.service.dto.RidePathDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-14T22:42:45+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class RidePathMapperImpl implements RidePathMapper {

    @Override
    public RidePath toEntity(RidePathDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RidePath ridePath = new RidePath();

        ridePath.id( dto.getId() );
        ridePath.setSource( dto.getSource() );
        ridePath.setDestination( dto.getDestination() );
        ridePath.setDistance( dto.getDistance() );
        ridePath.setEstimatedTime( dto.getEstimatedTime() );

        return ridePath;
    }

    @Override
    public RidePathDTO toDto(RidePath entity) {
        if ( entity == null ) {
            return null;
        }

        RidePathDTO ridePathDTO = new RidePathDTO();

        ridePathDTO.setId( entity.getId() );
        ridePathDTO.setSource( entity.getSource() );
        ridePathDTO.setDestination( entity.getDestination() );
        ridePathDTO.setDistance( entity.getDistance() );
        ridePathDTO.setEstimatedTime( entity.getEstimatedTime() );

        return ridePathDTO;
    }

    @Override
    public List<RidePath> toEntity(List<RidePathDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RidePath> list = new ArrayList<RidePath>( dtoList.size() );
        for ( RidePathDTO ridePathDTO : dtoList ) {
            list.add( toEntity( ridePathDTO ) );
        }

        return list;
    }

    @Override
    public List<RidePathDTO> toDto(List<RidePath> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RidePathDTO> list = new ArrayList<RidePathDTO>( entityList.size() );
        for ( RidePath ridePath : entityList ) {
            list.add( toDto( ridePath ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RidePath entity, RidePathDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getSource() != null ) {
            entity.setSource( dto.getSource() );
        }
        if ( dto.getDestination() != null ) {
            entity.setDestination( dto.getDestination() );
        }
        if ( dto.getDistance() != null ) {
            entity.setDistance( dto.getDistance() );
        }
        if ( dto.getEstimatedTime() != null ) {
            entity.setEstimatedTime( dto.getEstimatedTime() );
        }
    }

    @Override
    public RidePathDTO toDtoId(RidePath ridePath) {
        if ( ridePath == null ) {
            return null;
        }

        RidePathDTO ridePathDTO = new RidePathDTO();

        ridePathDTO.setId( ridePath.getId() );

        return ridePathDTO;
    }

    @Override
    public Set<RidePathDTO> toDtoIdSet(Set<RidePath> ridePath) {
        if ( ridePath == null ) {
            return null;
        }

        Set<RidePathDTO> set = new HashSet<RidePathDTO>( Math.max( (int) ( ridePath.size() / .75f ) + 1, 16 ) );
        for ( RidePath ridePath1 : ridePath ) {
            set.add( toDto( ridePath1 ) );
        }

        return set;
    }
}
