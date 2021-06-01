package com.leb.app.service.mapper;

import com.leb.app.domain.Dimensions;
import com.leb.app.service.dto.DimensionsDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-20T00:48:49+0100",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.3.1300.v20210331-0708, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class DimensionsMapperImpl implements DimensionsMapper {

    @Override
    public Dimensions toEntity(DimensionsDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Dimensions dimensions = new Dimensions();

        dimensions.id( dto.getId() );
        dimensions.setHeight( dto.getHeight() );
        dimensions.setWidth( dto.getWidth() );
        dimensions.setDepth( dto.getDepth() );

        return dimensions;
    }

    @Override
    public DimensionsDTO toDto(Dimensions entity) {
        if ( entity == null ) {
            return null;
        }

        DimensionsDTO dimensionsDTO = new DimensionsDTO();

        dimensionsDTO.setId( entity.getId() );
        dimensionsDTO.setHeight( entity.getHeight() );
        dimensionsDTO.setWidth( entity.getWidth() );
        dimensionsDTO.setDepth( entity.getDepth() );

        return dimensionsDTO;
    }

    @Override
    public List<Dimensions> toEntity(List<DimensionsDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Dimensions> list = new ArrayList<Dimensions>( dtoList.size() );
        for ( DimensionsDTO dimensionsDTO : dtoList ) {
            list.add( toEntity( dimensionsDTO ) );
        }

        return list;
    }

    @Override
    public List<DimensionsDTO> toDto(List<Dimensions> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DimensionsDTO> list = new ArrayList<DimensionsDTO>( entityList.size() );
        for ( Dimensions dimensions : entityList ) {
            list.add( toDto( dimensions ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Dimensions entity, DimensionsDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getHeight() != null ) {
            entity.setHeight( dto.getHeight() );
        }
        if ( dto.getWidth() != null ) {
            entity.setWidth( dto.getWidth() );
        }
        if ( dto.getDepth() != null ) {
            entity.setDepth( dto.getDepth() );
        }
    }

    @Override
    public DimensionsDTO toDtoId(Dimensions dimensions) {
        if ( dimensions == null ) {
            return null;
        }

        DimensionsDTO dimensionsDTO = new DimensionsDTO();

        dimensionsDTO.setId( dimensions.getId() );

        return dimensionsDTO;
    }
}
