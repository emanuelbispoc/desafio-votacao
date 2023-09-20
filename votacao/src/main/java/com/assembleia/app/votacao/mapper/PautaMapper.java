package com.assembleia.app.votacao.mapper;

import com.assembleia.app.votacao.dto.request.PautaRequest;
import com.assembleia.app.votacao.dto.response.PautaResponse;
import com.assembleia.app.votacao.model.Pauta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PautaMapper {
    Pauta requestToModel(PautaRequest pautaRequest);
    @Mapping(source = "relator.nome", target = "relatorNome")
    PautaResponse modelToResponse(Pauta pauta);
}
