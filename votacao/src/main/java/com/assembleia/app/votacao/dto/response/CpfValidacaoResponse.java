package com.assembleia.app.votacao.dto.response;

import com.assembleia.app.votacao.model.enums.SituacaoCpf;

public record CpfValidacaoResponse(
        SituacaoCpf status
) {}
