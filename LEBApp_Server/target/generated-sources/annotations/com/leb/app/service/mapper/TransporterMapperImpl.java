package com.leb.app.service.mapper;

import com.leb.app.domain.RidePath;
import com.leb.app.domain.Transporter;
import com.leb.app.service.dto.RidePathDTO;
import com.leb.app.service.dto.TransporterDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-30T00:38:06+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.0.v20210618-1653, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class TransporterMapperImpl implements TransporterMapper {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RidePathMapper ridePathMapper;

    @Override
    public List<Transporter> toEntity(List<TransporterDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Transporter> list = new ArrayList<Transporter>( dtoList.size() );
        for ( TransporterDTO transporterDTO : dtoList ) {
            list.add( toEntity( transporterDTO ) );
        }

        return list;
    }

    @Override
    public List<TransporterDTO> toDto(List<Transporter> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TransporterDTO> list = new ArrayList<TransporterDTO>( entityList.size() );
        for ( Transporter transporter : entityList ) {
            list.add( toDto( transporter ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Transporter entity, TransporterDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getFavouriteTransport() != null ) {
            entity.setFavouriteTransport( dto.getFavouriteTransport() );
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
        if ( entity.getRidePaths() != null ) {
            Set<RidePath> set = ridePathDTOSetToRidePathSet( dto.getRidePaths() );
            if ( set != null ) {
                entity.getRidePaths().clear();
                entity.getRidePaths().addAll( set );
            }
        }
        else {
            Set<RidePath> set = ridePathDTOSetToRidePathSet( dto.getRidePaths() );
            if ( set != null ) {
                entity.setRidePaths( set );
            }
        }
    }

    @Override
    public TransporterDTO toDto(Transporter s) {
        if ( s == null ) {
            return null;
        }

        TransporterDTO transporterDTO = new TransporterDTO();

        transporterDTO.setUserInfo( userInfoMapper.toDtoId( s.getUserInfo() ) );
        transporterDTO.setRidePaths( ridePathMapper.toDtoIdSet( s.getRidePaths() ) );
        transporterDTO.setId( s.getId() );
        transporterDTO.setFavouriteTransport( s.getFavouriteTransport() );
        transporterDTO.setNumberOfDeliveries( s.getNumberOfDeliveries() );
        transporterDTO.setNumberOfKm( s.getNumberOfKm() );
        transporterDTO.setReceivedValue( s.getReceivedValue() );
        transporterDTO.setValueToReceive( s.getValueToReceive() );
        transporterDTO.setRanking( s.getRanking() );

        return transporterDTO;
    }

    @Override
    public Set<TransporterDTO> toDtoIdSet(Set<Transporter> transporter) {
        if ( transporter == null ) {
            return null;
        }

        Set<TransporterDTO> set = new HashSet<TransporterDTO>( Math.max( (int) ( transporter.size() / .75f ) + 1, 16 ) );
        for ( Transporter transporter1 : transporter ) {
            set.add( toDto( transporter1 ) );
        }

        return set;
    }

    @Override
    public Transporter toEntity(TransporterDTO transporterDTO) {
        if ( transporterDTO == null ) {
            return null;
        }

        Transporter transporter = new Transporter();

        transporter.id( transporterDTO.getId() );
        transporter.setFavouriteTransport( transporterDTO.getFavouriteTransport() );
        transporter.setNumberOfDeliveries( transporterDTO.getNumberOfDeliveries() );
        transporter.setNumberOfKm( transporterDTO.getNumberOfKm() );
        transporter.setReceivedValue( transporterDTO.getReceivedValue() );
        transporter.setValueToReceive( transporterDTO.getValueToReceive() );
        transporter.setRanking( transporterDTO.getRanking() );
        transporter.setUserInfo( userInfoMapper.toEntity( transporterDTO.getUserInfo() ) );
        transporter.setRidePaths( ridePathDTOSetToRidePathSet( transporterDTO.getRidePaths() ) );

        return transporter;
    }

    protected Set<RidePath> ridePathDTOSetToRidePathSet(Set<RidePathDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<RidePath> set1 = new HashSet<RidePath>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RidePathDTO ridePathDTO : set ) {
            set1.add( ridePathMapper.toEntity( ridePathDTO ) );
        }

        return set1;
    }
}
