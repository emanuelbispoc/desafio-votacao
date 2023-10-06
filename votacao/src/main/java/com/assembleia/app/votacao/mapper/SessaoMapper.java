package com.assembleia.app.votacao.mapper;

import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.model.Sessao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessaoMapper {
    SessaoResponse modelToResponse(Sessao sessao);
}
