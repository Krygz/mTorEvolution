package com.mtor.evolution.mapper;

import com.athletetrack.dto.request.CicloHormonalRequestDTO;
import com.athletetrack.dto.response.CicloHormonalResponseDTO;
import com.athletetrack.model.CicloHormonal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CicloHormonalMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "substancias", ignore = true)
    @Mapping(target = "protetores", ignore = true)
    CicloHormonal toEntity(CicloHormonalRequestDTO dto);
    
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nomeCompleto", target = "clienteNome")
    CicloHormonalResponseDTO toResponseDTO(CicloHormonal entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "substancias", ignore = true)
    @Mapping(target = "protetores", ignore = true)
    void updateEntityFromDTO(CicloHormonalRequestDTO dto, @MappingTarget CicloHormonal entity);
}