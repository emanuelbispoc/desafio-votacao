package com.assembleia.app.votacao.service.impl;

import com.assembleia.app.votacao.dto.request.SessaoRequest;
import com.assembleia.app.votacao.dto.request.VotoRequest;
import com.assembleia.app.votacao.dto.response.SessaoCriadaResponse;
import com.assembleia.app.votacao.dto.response.SessaoResponse;
import com.assembleia.app.votacao.dto.response.VotoSessaoResponse;
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
        return mapper.modelToSimpleResponse(buscarSeSessaoExistePorId(id));
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
        Sessao sessao = buscarSeSessaoExistePorId(sessaoId);

        sessao.validarSeEstaEmAndamento();
        verificarSeVotoEValido(sessao, associado);

        sessao.adicionarVoto(new VotoSessao(request.voto(), associado));

        return mapper.toVotoResponse(repository.save(sessao));
    }

    private Sessao buscarSeSessaoExistePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sessão não encontrada."));
    }

    private void verificarSeVotoEValido(Sessao sessao, Associado associado) {
        if (votoJaFoiRegistrado(sessao.getId(), associado.getId())) {
            throw new UnprocessableEntityException("O associado já registrou voto na sessão.");
        }

        cpfService.verificarSeSituacaoEstaRegular(associado.getCpf());
    }

    private boolean votoJaFoiRegistrado(Long sessaoId, Long associadoId) {
       return repository.existsByVotosIdSessaoIdAndVotosIdAssociadoId(sessaoId, associadoId);
    }
}
