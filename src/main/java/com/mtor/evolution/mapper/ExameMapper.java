package com.mtor.evolution.mapper;

import com.athletetrack.dto.request.ExameRequestDTO;
import com.athletetrack.dto.response.ExameResponseDTO;
import com.athletetrack.model.Exame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExameMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    Exame toEntity(ExameRequestDTO dto);
    
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nomeCompleto", target = "clienteNome")
    ExameResponseDTO toResponseDTO(Exame entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    void updateEntityFromDTO(ExameRequestDTO dto, @MappingTarget Exame entity);
}