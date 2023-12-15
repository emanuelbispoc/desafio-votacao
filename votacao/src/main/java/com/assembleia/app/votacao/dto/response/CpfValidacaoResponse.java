package com.assembleia.app.votacao.dto.response;

import com.assembleia.app.votacao.enums.SituacaoCpf;

public record CpfValidacaoResponse(
        SituacaoCpf status
) {}
