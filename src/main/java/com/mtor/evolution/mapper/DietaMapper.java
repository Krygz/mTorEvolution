package com.mtor.evolution.mapper;

import com.athletetrack.dto.request.DietaRequestDTO;
import com.athletetrack.dto.response.DietaResponseDTO;
import com.athletetrack.model.Dieta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DietaMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "refeicoes", ignore = true)
    @Mapping(target = "suplementos", ignore = true)
    Dieta toEntity(DietaRequestDTO dto);
    
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nomeCompleto", target = "clienteNome")
    DietaResponseDTO toResponseDTO(Dieta entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "refeicoes", ignore = true)
    @Mapping(target = "suplementos", ignore = true)
    void updateEntityFromDTO(DietaRequestDTO dto, @MappingTarget Dieta entity);
}