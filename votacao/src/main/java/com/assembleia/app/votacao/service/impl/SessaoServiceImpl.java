package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.request.VotoRequest;
import com.assembleia.app.votacao.dto.response.SessaoCriadaResponse;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.dto.response.VotoSessaoResponse;
import com.assembleia.app.votacao.enums.SessaoStatus;
import com.assembleia.app.votacao.enums.SituacaoCpf;
import com.assembleia.app.votacao.exception.NotFoundException;
import com.assembleia.app.votacao.exception.UnprocessableEntityException;
import com.assembleia.app.votacao.mapper.SessaoMapper;
import com.assembleia.app.votacao.model.Associado;
import com.assembleia.app.votacao.model.Pauta;
import com.assembleia.app.votacao.model.Sessao;
import com.assembleia.app.votacao.model.VotoSessao;
import com.assembleia.app.votacao.repository.SessaoRepository;
import com.assembleia.app.votacao.service.AssociadoService;
import com.assembleia.app.votacao.service.CpfService;
import com.assembleia.app.votacao.service.PautaService;
import com.assembleia.app.votacao.service.SessaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SessaoServiceImpl implements SessaoService {
    private final CpfService cpfService;
    private final PautaService pautaService;
    private final AssociadoService associadoService;
    private final SessaoRepository repository;
    private final SessaoMapper mapper;

    @Override
    public SessaoResponse buscarPorId(Long id) {
        return mapper.modelToSimpleResponse(verificaSeSessaoExistePorId(id));
    }

    @Override
    public SessaoCriadaResponse salvar(SessaoRequest request) {
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
        Sessao sessao = verificaSeSessaoPodeReceberVotos(sessaoId, associado);

        sessao.adicionaVoto(new VotoSessao(request.voto(), associado));
        Sessao sessaoAtualizada = repository.save(sessao);
        return mapper.toVotoResponse(sessaoAtualizada);
    }

    private Sessao verificaSeSessaoExistePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sessão não encontrada."));
    }

    private Sessao verificaSeSessaoPodeReceberVotos(Long sessaoId, Associado associado) {
        Sessao sessao = verificaSeSessaoExistePorId(sessaoId);

        if(sessao.getStatus() != SessaoStatus.EM_ANDAMENTO) {
            throw new UnprocessableEntityException("Sessão não está em andamento.");
        }

        verificaSePodeReceberVotoAssociado(sessao.getId(), associado);
        return sessao;
    }

    private void verificaSePodeReceberVotoAssociado(Long sessaoId, Associado associado) {
        if (repository.existsByVotosIdSessaoIdAndVotosIdAssociadoId(sessaoId, associado.getId())) {
            throw new UnprocessableEntityException("O associado já registrou voto na sessão.");
        }

        if(cpfService.verificaSituacao(associado.getCpf()).status() == SituacaoCpf.IRREGULAR) {
            throw new UnprocessableEntityException("UNABLE_TO_VOTE");
        }
    }
}
