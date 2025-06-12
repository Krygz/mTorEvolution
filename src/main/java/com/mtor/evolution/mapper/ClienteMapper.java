package com.mtor.evolution.mapper;

import com.athletetrack.dto.request.ClienteRequestDTO;
import com.athletetrack.dto.response.ClienteResponseDTO;
import com.athletetrack.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "avaliacoesFisicas", ignore = true)
    @Mapping(target = "exames", ignore = true)
    @Mapping(target = "dietas", ignore = true)
    @Mapping(target = "treinos", ignore = true)
    @Mapping(target = "ciclosHormonais", ignore = true)
    Cliente toEntity(ClienteRequestDTO dto);
    
    ClienteResponseDTO toResponseDTO(Cliente entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "avaliacoesFisicas", ignore = true)
    @Mapping(target = "exames", ignore = true)
    @Mapping(target = "dietas", ignore = true)
    @Mapping(target = "treinos", ignore = true)
    @Mapping(target = "ciclosHormonais", ignore = true)
    void updateEntityFromDTO(ClienteRequestDTO dto, @MappingTarget Cliente entity);
}