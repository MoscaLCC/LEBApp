package com.leb.app.service.mapper;

import com.leb.app.domain.Transporter;
import com.leb.app.domain.Zone;
import com.leb.app.service.dto.TransporterDTO;
import com.leb.app.service.dto.ZoneDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-12T23:29:30+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ZoneMapperImpl implements ZoneMapper {

    @Autowired
    private TransporterMapper transporterMapper;

    @Override
    public List<Zone> toEntity(List<ZoneDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Zone> list = new ArrayList<Zone>( dtoList.size() );
        for ( ZoneDTO zoneDTO : dtoList ) {
            list.add( toEntity( zoneDTO ) );
        }

        return list;
    }

    @Override
    public List<ZoneDTO> toDto(List<Zone> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ZoneDTO> list = new ArrayList<ZoneDTO>( entityList.size() );
        for ( Zone zone : entityList ) {
            list.add( toDto( zone ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Zone entity, ZoneDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( entity.getTransporters() != null ) {
            Set<Transporter> set = transporterDTOSetToTransporterSet( dto.getTransporters() );
            if ( set != null ) {
                entity.getTransporters().clear();
                entity.getTransporters().addAll( set );
            }
        }
        else {
            Set<Transporter> set = transporterDTOSetToTransporterSet( dto.getTransporters() );
            if ( set != null ) {
                entity.setTransporters( set );
            }
        }
    }

    @Override
    public ZoneDTO toDto(Zone s) {
        if ( s == null ) {
            return null;
        }

        ZoneDTO zoneDTO = new ZoneDTO();

        zoneDTO.setTransporters( transporterMapper.toDtoIdSet( s.getTransporters() ) );
        zoneDTO.setId( s.getId() );
        zoneDTO.setName( s.getName() );

        return zoneDTO;
    }

    @Override
    public ZoneDTO toDtoId(Zone zone) {
        if ( zone == null ) {
            return null;
        }

        ZoneDTO zoneDTO = new ZoneDTO();

        zoneDTO.setId( zone.getId() );

        return zoneDTO;
    }

    @Override
    public Zone toEntity(ZoneDTO zoneDTO) {
        if ( zoneDTO == null ) {
            return null;
        }

        Zone zone = new Zone();

        zone.id( zoneDTO.getId() );
        zone.setName( zoneDTO.getName() );
        zone.setTransporters( transporterDTOSetToTransporterSet( zoneDTO.getTransporters() ) );

        return zone;
    }

    protected Set<Transporter> transporterDTOSetToTransporterSet(Set<TransporterDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Transporter> set1 = new HashSet<Transporter>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( TransporterDTO transporterDTO : set ) {
            set1.add( transporterMapper.toEntity( transporterDTO ) );
        }

        return set1;
    }
}
