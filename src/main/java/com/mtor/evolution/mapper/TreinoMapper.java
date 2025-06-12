package com.mtor.evolution.mapper;

import com.mtor.evolution.dto.request.TreinoRequestDTO;
import com.mtor.evolution.dto.response.TreinoResponseDTO;
import com.mtor.evolution.model.Treino;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TreinoMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "diasTreino", ignore = true)
    Treino toEntity(TreinoRequestDTO dto);
    
    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nomeCompleto", target = "clienteNome")
    TreinoResponseDTO toResponseDTO(Treino entity);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "diasTreino", ignore = true)
    void updateEntityFromDTO(TreinoRequestDTO dto, @MappingTarget Treino entity);
}