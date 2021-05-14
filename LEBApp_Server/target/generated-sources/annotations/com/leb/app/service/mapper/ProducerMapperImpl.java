package com.leb.app.service.mapper;

import com.leb.app.domain.Producer;
import com.leb.app.service.dto.ProducerDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-05-14T22:42:45+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ProducerMapperImpl implements ProducerMapper {

    @Override
    public Producer toEntity(ProducerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Producer producer = new Producer();

        producer.id( dto.getId() );
        producer.setName( dto.getName() );
        producer.setMail( dto.getMail() );
        producer.setPhoneNumber( dto.getPhoneNumber() );
        producer.setNib( dto.getNib() );
        producer.setNif( dto.getNif() );
        producer.setBirthday( dto.getBirthday() );
        producer.setAdress( dto.getAdress() );
        producer.setPhoto( dto.getPhoto() );
        producer.setLinkSocial( dto.getLinkSocial() );
        producer.setNumberRequests( dto.getNumberRequests() );
        producer.setPayedValue( dto.getPayedValue() );
        producer.setValueToPay( dto.getValueToPay() );
        producer.setRanking( dto.getRanking() );

        return producer;
    }

    @Override
    public ProducerDTO toDto(Producer entity) {
        if ( entity == null ) {
            return null;
        }

        ProducerDTO producerDTO = new ProducerDTO();

        producerDTO.setId( entity.getId() );
        producerDTO.setName( entity.getName() );
        producerDTO.setMail( entity.getMail() );
        producerDTO.setPhoneNumber( entity.getPhoneNumber() );
        producerDTO.setNib( entity.getNib() );
        producerDTO.setNif( entity.getNif() );
        producerDTO.setBirthday( entity.getBirthday() );
        producerDTO.setAdress( entity.getAdress() );
        producerDTO.setPhoto( entity.getPhoto() );
        producerDTO.setLinkSocial( entity.getLinkSocial() );
        producerDTO.setNumberRequests( entity.getNumberRequests() );
        producerDTO.setPayedValue( entity.getPayedValue() );
        producerDTO.setValueToPay( entity.getValueToPay() );
        producerDTO.setRanking( entity.getRanking() );

        return producerDTO;
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
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getMail() != null ) {
            entity.setMail( dto.getMail() );
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
        if ( dto.getBirthday() != null ) {
            entity.setBirthday( dto.getBirthday() );
        }
        if ( dto.getAdress() != null ) {
            entity.setAdress( dto.getAdress() );
        }
        if ( dto.getPhoto() != null ) {
            entity.setPhoto( dto.getPhoto() );
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
