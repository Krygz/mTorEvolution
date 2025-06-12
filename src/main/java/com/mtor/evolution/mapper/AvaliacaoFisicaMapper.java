package com.mtor.evolution.mapper;

import com.athletetrack.dto.request.AvaliacaoFisicaRequestDTO;
import com.athletetrack.dto.response.AvaliacaoFisicaResponseDTO;
import com.athletetrack.model.AvaliacaoFisica;
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