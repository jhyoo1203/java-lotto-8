package lotto.application.dto.response;

import lotto.domain.Lotto;

import java.util.List;

public record LottoPurchaseResponse(
        int lottoCount,
        List<Lotto> lottos
) {
    public static LottoPurchaseResponse of(int lottoCount, List<Lotto> lottos) {
        return new LottoPurchaseResponse(lottoCount, lottos);
    }
}
