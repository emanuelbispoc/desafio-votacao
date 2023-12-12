package com.assembleia.app.votacao.mapper;

import com.assembleia.app.votacao.dto.response.SessaoCriadaResponse;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.dto.response.VotoSessaoResponse;
import com.assembleia.app.votacao.model.Sessao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SessaoMapper {
    @Mapping(source = "pauta.relator.nome", target = "pauta.relatorNome")
    SessaoCriadaResponse modelToResponse(Sessao sessao);
    @Mapping(source = "pauta.relator.nome", target = "pauta.relatorNome")
    SessaoResponse modelToSimpleResponse(Sessao sessao);
    @Mappings({
            @Mapping(source = "id", target = "sessaoId"),
            @Mapping(source = "votosSim", target = "totalVotosSim"),
            @Mapping(source = "votosNao", target = "totalVotosNao"),
            @Mapping(source = "dataFim", target = "dataTerminoSessao")
    })
    VotoSessaoResponse toVotoResponse(Sessao sessao);
}