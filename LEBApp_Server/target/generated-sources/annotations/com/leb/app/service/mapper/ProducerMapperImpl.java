package com.leb.app.service.mapper;

import com.leb.app.domain.Producer;
import com.leb.app.service.dto.ProducerDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-02T22:39:12+0000",
    comments = "version: 1.4.2.Final, compiler: Eclipse JDT (IDE) 1.4.50.v20210914-1429, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ProducerMapperImpl implements ProducerMapper {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Producer toEntity(ProducerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Producer producer = new Producer();

        producer.id( dto.getId() );
        producer.setLinkSocial( dto.getLinkSocial() );
        producer.setNumberRequests( dto.getNumberRequests() );
        producer.setPayedValue( dto.getPayedValue() );
        producer.setValueToPay( dto.getValueToPay() );
        producer.setRanking( dto.getRanking() );
        producer.setUserInfo( userInfoMapper.toEntity( dto.getUserInfo() ) );

        return producer;
    }

    @Override
    public List<Producer> toEntity(List<ProducerDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Producer> list = new ArrayList<Producer>( dtoList.size() );
        for ( ProducerDTO producerDTO : dtoList ) {
            list.add( toEntity( producerDTO ) );
        }

        return list;
    }

    @Override
    public List<ProducerDTO> toDto(List<Producer> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProducerDTO> list = new ArrayList<ProducerDTO>( entityList.size() );
        for ( Producer producer : entityList ) {
            list.add( toDto( producer ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Producer entity, ProducerDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.id( dto.getId() );
        }
        if ( dto.getLinkSocial() != null ) {
            entity.setLinkSocial( dto.getLinkSocial() );
        }
        if ( dto.getNumberRequests() != null ) {
            entity.setNumberRequests( dto.getNumberRequests() );
        }
        if ( dto.getPayedValue() != null ) {
            entity.setPayedValue( dto.getPayedValue() );
        }
        if ( dto.getValueToPay() != null ) {
            entity.setValueToPay( dto.getValueToPay() );
        }
        if ( dto.getRanking() != null ) {
            entity.setRanking( dto.getRanking() );
        }
        if ( dto.getUserInfo() != null ) {
            entity.setUserInfo( userInfoMapper.toEntity( dto.getUserInfo() ) );
        }
    }

    @Override
    public ProducerDTO toDto(Producer s) {
        if ( s == null ) {
            return null;
        }

        ProducerDTO producerDTO = new ProducerDTO();

        producerDTO.setUserInfo( userInfoMapper.toDtoId( s.getUserInfo() ) );
        producerDTO.setId( s.getId() );
        producerDTO.setLinkSocial( s.getLinkSocial() );
        producerDTO.setNumberRequests( s.getNumberRequests() );
        producerDTO.setPayedValue( s.getPayedValue() );
        producerDTO.setValueToPay( s.getValueToPay() );
        producerDTO.setRanking( s.getRanking() );

        return producerDTO;
    }

    @Override
    public ProducerDTO toDtoId(Producer producer) {
        if ( producer == null ) {
            return null;
        }

        ProducerDTO producerDTO = new ProducerDTO();

        producerDTO.setId( producer.getId() );

        return producerDTO;
    }
}
