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

    @Mappings({
            @Mapping(source = "pauta.relator.nome", target = "pauta.relatorNome"),
            @Mapping(target = "status",  expression = "java(sessao.calcularStatus())"),
            @Mapping(target = "resultado",  expression = "java(sessao.obterResultado())"),
            @Mapping(target = "votosSim",  expression = "java(sessao.obterTotalVotosSim())"),
            @Mapping(target = "votosNao",  expression = "java(sessao.obterTotalVotosNao())"),
    })
    SessaoResponse modelToSimpleResponse(Sessao sessao);
    @Mappings({
            @Mapping(source = "id", target = "sessaoId"),
            @Mapping(target = "totalVotosSim",  expression = "java(sessao.obterTotalVotosSim())"),
            @Mapping(target = "totalVotosNao",  expression = "java(sessao.obterTotalVotosNao())"),
            @Mapping(source = "dataFim", target = "dataTerminoSessao")
    })
    VotoSessaoResponse toVotoResponse(Sessao sessao);
}