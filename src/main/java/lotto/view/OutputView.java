package lotto.view;

import lotto.application.dto.response.LottoMatchResult;
import lotto.application.dto.response.LottoPurchaseResponse;
import lotto.application.dto.response.LottoWinningResponse;
import lotto.domain.Lotto;

import java.util.List;

public class OutputView {

    private static final String PURCHASED_LOTTO_MESSAGE = "\n%d개를 구매했습니다.\n";
    private static final String LOTTO_NUMBER_MESSAGE = "%s\n";
    private static final String LOTTO_RESULT_MESSAGE = "\n당첨 통계\n---";
    private static final String LOTTO_RANK_MESSAGE = "%d개 일치%s (%,d원) - %d개\n";
    private static final String LOTTO_EARNING_RATE_MESSAGE = "총 수익률은 %.1f%%입니다.\n";
    private static final String EMPTY_STRING = "";
    private static final String BONUS_MARK = ", 보너스 볼 일치";

    public void printPurchasedLottos(LottoPurchaseResponse response) {
        System.out.printf(PURCHASED_LOTTO_MESSAGE, response.lottoCount());
        for (Lotto lotto : response.lottos()) {
            printLottoNumbers(lotto);
        }
    }

    private void printLottoNumbers(Lotto lotto) {
        System.out.printf(LOTTO_NUMBER_MESSAGE, lotto.toString());
    }

    public void printLottoResults(LottoWinningResponse response) {
        List<LottoMatchResult> matchResults = response.matchResults();
        System.out.println(LOTTO_RESULT_MESSAGE);

        for (LottoMatchResult matchResult : matchResults) {
            printLottoRankResult(matchResult);
        }

        System.out.printf(LOTTO_EARNING_RATE_MESSAGE, response.yield());
    }

    private void printLottoRankResult(LottoMatchResult matchResult) {
        String bonusMark = EMPTY_STRING;

        if (matchResult.isBonusMatch()) {
            bonusMark = BONUS_MARK;
        }

        System.out.printf(LOTTO_RANK_MESSAGE,
                matchResult.matchCount(), bonusMark, matchResult.prize(), matchResult.winnerCount()
        );
    }
}
