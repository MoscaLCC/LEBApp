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
    date = "2021-11-02T22:49:42+0000",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.50.v20210914-1429, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class RequestMapperImpl implements RequestMapper {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Request toEntity(RequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Request request = new Request();

        request.setId( dto.getId() );
        request.setProductValue( dto.getProductValue() );
        request.setProductName( dto.getProductName() );
        request.setSource( dto.getSource() );
        request.setDestination( dto.getDestination() );
        request.setDestinationContact( dto.getDestinationContact() );
        request.setInitDate( dto.getInitDate() );
        request.setExpirationDate( dto.getExpirationDate() );
        request.setDescription( dto.getDescription() );
        request.setSpecialCharacteristics( dto.getSpecialCharacteristics() );
        request.setWeight( dto.getWeight() );
        request.setHight( dto.getHight() );
        request.setWidth( dto.getWidth() );
        request.setStatus( dto.getStatus() );
        request.setEstimatedDate( dto.getEstimatedDate() );
        request.setDeliveryTime( dto.getDeliveryTime() );
        request.setShippingCosts( dto.getShippingCosts() );
        request.setRating( dto.getRating() );
        request.ownerRequest( userInfoMapper.toEntity( dto.getOwnerRequest() ) );
        request.tranporter( userInfoMapper.toEntity( dto.getTranporter() ) );

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
            entity.setId( dto.getId() );
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
        if ( dto.getWeight() != null ) {
            entity.setWeight( dto.getWeight() );
        }
        if ( dto.getHight() != null ) {
            entity.setHight( dto.getHight() );
        }
        if ( dto.getWidth() != null ) {
            entity.setWidth( dto.getWidth() );
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
        if ( dto.getOwnerRequest() != null ) {
            entity.ownerRequest( userInfoMapper.toEntity( dto.getOwnerRequest() ) );
        }
        if ( dto.getTranporter() != null ) {
            entity.tranporter( userInfoMapper.toEntity( dto.getTranporter() ) );
        }
    }

    @Override
    public RequestDTO toDto(Request s) {
        if ( s == null ) {
            return null;
        }

        RequestDTO requestDTO = new RequestDTO();

        requestDTO.setOwnerRequest( userInfoMapper.toDtoId( s.getOwnerRequest() ) );
        requestDTO.setTranporter( userInfoMapper.toDtoId( s.getTranporter() ) );
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
        requestDTO.setWeight( s.getWeight() );
        requestDTO.setHight( s.getHight() );
        requestDTO.setWidth( s.getWidth() );
        requestDTO.setStatus( s.getStatus() );
        requestDTO.setEstimatedDate( s.getEstimatedDate() );
        requestDTO.setDeliveryTime( s.getDeliveryTime() );
        requestDTO.setShippingCosts( s.getShippingCosts() );
        requestDTO.setRating( s.getRating() );

        return requestDTO;
    }
}
