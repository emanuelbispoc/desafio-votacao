package com.assembleia.app.votacao.mapper;

import com.assembleia.app.votacao.dto.request.AssociadoRequest;
import com.assembleia.app.votacao.dto.response.AssociadoResponse;
import com.assembleia.app.votacao.model.Associado;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AssociadoMapper {
    AssociadoResponse modelToResponse(Associado associado);
    Associado responseToModel(AssociadoResponse associadoResponse);
    Associado requestToModel(AssociadoRequest associadoRequest);
}
