package com.leb.app.service.mapper;

import com.leb.app.domain.Request;
import com.leb.app.service.dto.RequestDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-12T23:29:29+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class RequestMapperImpl implements RequestMapper {

    @Autowired
    private DimensionsMapper dimensionsMapper;
    @Autowired
    private RidePathMapper ridePathMapper;
    @Autowired
    private ProducerMapper producerMapper;

    @Override
    public Request toEntity(RequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Request request = new Request();

        request.id( dto.getId() );
        request.setProductValue( dto.getProductValue() );
        request.setProductName( dto.getProductName() );
        request.setSource( dto.getSource() );
        request.setDestination( dto.getDestination() );
        request.setDestinationContact( dto.getDestinationContact() );
        request.setInitDate( dto.getInitDate() );
        request.setExpirationDate( dto.getExpirationDate() );
        request.setDescription( dto.getDescription() );
        request.setSpecialCharacteristics( dto.getSpecialCharacteristics() );
        request.setProductWeight( dto.getProductWeight() );
        request.setStatus( dto.getStatus() );
        request.setEstimatedDate( dto.getEstimatedDate() );
        request.setDeliveryTime( dto.getDeliveryTime() );
        request.setShippingCosts( dto.getShippingCosts() );
        request.setRating( dto.getRating() );
        request.setDimensions( dimensionsMapper.toEntity( dto.getDimensions() ) );
        request.setRidePath( ridePathMapper.toEntity( dto.getRidePath() ) );
        request.setProducer( producerMapper.toEntity( dto.getProducer() ) );

        return request;
    }

    @Override
    public List<Request> toEntity(List<RequestDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Request> list = new ArrayList<Request>( dtoList.size() );
        for ( RequestDTO requestDTO : dtoList ) {
            list.add( toEntity( requestDTO ) );
        }

        return list;
    }

    @Override
    public List<RequestDTO> toDto(List<Request> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RequestDTO> list = new ArrayList<RequestDTO>( entityList.size() );
        for ( Request request : entityList ) {
            list.add( toDto( request ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Request entity, RequestDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getProductValue() != null ) {
            entity.setProductValue( dto.getProductValue() );
        }
        if ( dto.getProductName() != null ) {
            entity.setProductName( dto.getProductName() );
        }
        if ( dto.getSource() != null ) {
            entity.setSource( dto.getSource() );
        }
        if ( dto.getDestination() != null ) {
            entity.setDestination( dto.getDestination() );
        }
        if ( dto.getDestinationContact() != null ) {
            entity.setDestinationContact( dto.getDestinationContact() );
        }
        if ( dto.getInitDate() != null ) {
            entity.setInitDate( dto.getInitDate() );
        }
        if ( dto.getExpirationDate() != null ) {
            entity.setExpirationDate( dto.getExpirationDate() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getSpecialCharacteristics() != null ) {
            entity.setSpecialCharacteristics( dto.getSpecialCharacteristics() );
        }
        if ( dto.getProductWeight() != null ) {
            entity.setProductWeight( dto.getProductWeight() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getEstimatedDate() != null ) {
            entity.setEstimatedDate( dto.getEstimatedDate() );
        }
        if ( dto.getDeliveryTime() != null ) {
            entity.setDeliveryTime( dto.getDeliveryTime() );
        }
        if ( dto.getShippingCosts() != null ) {
            entity.setShippingCosts( dto.getShippingCosts() );
        }
        if ( dto.getRating() != null ) {
            entity.setRating( dto.getRating() );
        }
        if ( dto.getDimensions() != null ) {
            entity.setDimensions( dimensionsMapper.toEntity( dto.getDimensions() ) );
        }
        if ( dto.getRidePath() != null ) {
            entity.setRidePath( ridePathMapper.toEntity( dto.getRidePath() ) );
        }
        if ( dto.getProducer() != null ) {
            entity.setProducer( producerMapper.toEntity( dto.getProducer() ) );
        }
    }

    @Override
    public RequestDTO toDto(Request s) {
        if ( s == null ) {
            return null;
        }

        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setDimensions( dimensionsMapper.toDtoId( s.getDimensions() ) );
        requestDTO.setRidePath( ridePathMapper.toDtoId( s.getRidePath() ) );
        requestDTO.setProducer( producerMapper.toDtoId( s.getProducer() ) );
        requestDTO.setId( s.getId() );
        requestDTO.setProductValue( s.getProductValue() );
        requestDTO.setProductName( s.getProductName() );
        requestDTO.setSource( s.getSource() );
        requestDTO.setDestination( s.getDestination() );
        requestDTO.setDestinationContact( s.getDestinationContact() );
        requestDTO.setInitDate( s.getInitDate() );
        requestDTO.setExpirationDate( s.getExpirationDate() );
        requestDTO.setDescription( s.getDescription() );
        requestDTO.setSpecialCharacteristics( s.getSpecialCharacteristics() );
        requestDTO.setProductWeight( s.getProductWeight() );
        requestDTO.setStatus( s.getStatus() );
        requestDTO.setEstimatedDate( s.getEstimatedDate() );
        requestDTO.setDeliveryTime( s.getDeliveryTime() );
        requestDTO.setShippingCosts( s.getShippingCosts() );
        requestDTO.setRating( s.getRating() );

        return requestDTO;
    }
}
