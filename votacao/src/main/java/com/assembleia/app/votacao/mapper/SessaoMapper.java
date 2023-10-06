package com.assembleia.app.votacao.mapper;

import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.model.Sessao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessaoMapper {
    @Mapping(source = "pauta.relator.nome", target = "pauta.relatorNome")
    SessaoResponse modelToResponse(Sessao sessao);
}
