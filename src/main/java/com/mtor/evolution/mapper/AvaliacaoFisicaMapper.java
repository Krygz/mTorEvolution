package com.mtor.evolution.mapper;

import com.mtor.evolution.dto.request.AvaliacaoFisicaRequestDTO;
import com.mtor.evolution.dto.response.AvaliacaoFisicaResponseDTO;
import com.mtor.evolution.model.AvaliacaoFisica;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AvaliacaoFisicaMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    AvaliacaoFisica toEntity(AvaliacaoFisicaRequestDTO dto);
    
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nomeCompleto", target = "clienteNome")
    AvaliacaoFisicaResponseDTO toResponseDTO(AvaliacaoFisica entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    void updateEntityFromDTO(AvaliacaoFisicaRequestDTO dto, @MappingTarget AvaliacaoFisica entity);
}