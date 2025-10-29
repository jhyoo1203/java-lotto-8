package lotto.application.dto.response;

import java.util.List;

public record LottoWinningResponse(
        List<LottoMatchResult> matchResults,
        double yield
) {
    public static LottoWinningResponse of(List<LottoMatchResult> matchResults, double yield) {
        return new LottoWinningResponse(matchResults, yield);
    }
}
