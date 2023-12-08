package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.request.VotoRequest;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.dto.response.VotoSessaoResponse;
import com.assembleia.app.votacao.enums.SessaoStatus;
import com.assembleia.app.votacao.enums.Voto;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.mapper.SessaoMapper;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.model.Pauta;
import com.assembleia.app.votacao.model.Sessao;
import com.assembleia.app.votacao.model.VotoSessao;
import com.assembleia.app.votacao.repository.SessaoRepository;
import com.assembleia.app.votacao.service.AssociadoService;
import com.assembleia.app.votacao.service.PautaService;
import com.assembleia.app.votacao.service.SessaoService;
import com.assembleia.app.votacao.service.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SessaoServiceImpl implements SessaoService {
    private final PautaService pautaService;
    private final AssociadoService associadoService;
    private final VotoService votoService;
    private final SessaoRepository repository;
    private final SessaoMapper mapper;

    @Override
    public Sessao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sessão não encontrada."));
    }

    @Override
    public SessaoResponse salvar(SessaoRequest request) {
        Pauta pautaEncontrada = pautaService.validaPorId(request.pautaId());

        LocalDateTime dataAtual = LocalDateTime.now();
        Sessao novaSessao = new Sessao(
                dataAtual, dataAtual.plusMinutes(request.duracao()), pautaEncontrada
        );

        return mapper.modelToResponse(repository.save(novaSessao));
    }

    @Override
    public VotoSessaoResponse receberVoto(Long sessaoId, VotoRequest request) {
        Associado associado = associadoService.buscarPorCpf(request.associadoCpf());
        Sessao sessao = verificaSeSessaoPodeReceberVotos(sessaoId, associado.getId());

        sessao.adicionaVoto(new VotoSessao(request.voto(), associado));
        Sessao sessaoAtualizada = repository.save(sessao);
        return new VotoSessaoResponse(
                sessaoAtualizada.getId(),
                sessaoAtualizada.obterTotalVotosSim(),
                sessaoAtualizada.obterTotalVotosNao(),
                sessaoAtualizada.getDataFim()
        );
    }

    private Sessao verificaSeSessaoPodeReceberVotos(Long sessaoId, Long associadoId) {
        Sessao sessao = buscarPorId(sessaoId);

        if(sessao.getStatus() != SessaoStatus.EM_ANDAMENTO) {
            throw new UnprocessableEntityException("Sessão não está em andamento.");
        }

        verificaSePodeReceberVotoAssociado(sessao.getId(), associadoId);
        return sessao;
    }

    private void verificaSePodeReceberVotoAssociado(Long sessaoId, Long associadoId) {
        votoService.verificaSeVotoJaExiste(sessaoId, associadoId);
    }
}
