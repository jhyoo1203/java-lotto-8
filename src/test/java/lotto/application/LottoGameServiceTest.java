package lotto.application;

import lotto.application.dto.response.LottoMatchResult;
import lotto.application.dto.response.LottoWinningResponse;
import lotto.domain.Lotto;
import lotto.domain.WinningNumber;
import lotto.domain.repository.LottoRepository;
import lotto.domain.repository.WinningNumberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LottoGameService 테스트")
class LottoGameServiceTest {

    private final LottoRepository lottoRepository = new LottoRepository();
    private final WinningNumberRepository winningNumberRepository = new WinningNumberRepository();
    private final LottoGameService lottoGameService = new LottoGameService(lottoRepository, winningNumberRepository);

    @AfterEach
    void clearRepository() throws Exception {
        Field lottoMapField = LottoRepository.class.getDeclaredField("lottoMap");
        lottoMapField.setAccessible(true);
        Map<?, ?> lottoMap = (Map<?, ?>) lottoMapField.get(null);
        lottoMap.clear();

        Field idxField = LottoRepository.class.getDeclaredField("idx");
        idxField.setAccessible(true);
        idxField.set(null, 1);

        Field winningNumberField = WinningNumberRepository.class.getDeclaredField("winningNumber");
        winningNumberField.setAccessible(true);
        winningNumberField.set(null, null);
    }

    @Nested
    @DisplayName("matchLottoNumbers 메서드는")
    class MatchLottoNumbersMethod {

        @Test
        @DisplayName("1등 당첨 결과를 반환한다")
        void returnFirstRankResult() {
            //given
            lottoRepository.saveAll(List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6))
            ));
            winningNumberRepository.save(WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7));

            //when
            LottoWinningResponse response = lottoGameService.matchLottoNumbers();

            //then
            List<LottoMatchResult> matchResults = response.matchResults();
            LottoMatchResult firstRank = matchResults.stream()
                    .filter(result -> result.matchCount() == 6 && !result.isBonusMatch())
                    .findFirst()
                    .orElseThrow();
            assertThat(firstRank.winnerCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("2등 당첨 결과를 반환한다")
        void returnSecondRankResult() {
            //given
            lottoRepository.saveAll(List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 7))
            ));
            winningNumberRepository.save(WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7));

            //when
            LottoWinningResponse response = lottoGameService.matchLottoNumbers();

            //then
            List<LottoMatchResult> matchResults = response.matchResults();
            LottoMatchResult secondRank = matchResults.stream()
                    .filter(result -> result.matchCount() == 5 && result.isBonusMatch())
                    .findFirst()
                    .orElseThrow();
            assertThat(secondRank.winnerCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("3등 당첨 결과를 반환한다")
        void returnThirdRankResult() {
            //given
            lottoRepository.saveAll(List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 8))
            ));
            winningNumberRepository.save(WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7));

            //when
            LottoWinningResponse response = lottoGameService.matchLottoNumbers();

            //then
            List<LottoMatchResult> matchResults = response.matchResults();
            LottoMatchResult thirdRank = matchResults.stream()
                    .filter(result -> result.matchCount() == 5 && !result.isBonusMatch())
                    .findFirst()
                    .orElseThrow();
            assertThat(thirdRank.winnerCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("당첨되지 않은 경우 수익률 0을 반환한다")
        void returnZeroYieldWhenNoWinner() {
            //given
            lottoRepository.saveAll(List.of(
                    new Lotto(List.of(10, 11, 12, 13, 14, 15))
            ));
            winningNumberRepository.save(WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7));

            //when
            LottoWinningResponse response = lottoGameService.matchLottoNumbers();

            //then
            assertThat(response.yield()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("여러 로또의 당첨 결과를 집계한다")
        void aggregateMultipleLottos() {
            //given
            lottoRepository.saveAll(List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                    new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                    new Lotto(List.of(1, 2, 3, 10, 11, 12)),
                    new Lotto(List.of(10, 11, 12, 13, 14, 15))
            ));
            winningNumberRepository.save(WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7));

            //when
            LottoWinningResponse response = lottoGameService.matchLottoNumbers();

            //then
            List<LottoMatchResult> matchResults = response.matchResults();
            assertThat(matchResults).hasSize(5);
        }

        @Test
        @DisplayName("당첨되지 않은 경우 수익률이 0이다")
        void returnZeroYieldWhenNoMatch() {
            //given
            lottoRepository.saveAll(List.of(
                    new Lotto(List.of(10, 11, 12, 13, 14, 15)),
                    new Lotto(List.of(20, 21, 22, 23, 24, 25))
            ));
            winningNumberRepository.save(WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7));

            //when
            LottoWinningResponse response = lottoGameService.matchLottoNumbers();

            //then
            assertThat(response.yield()).isEqualTo(0.0);
        }

        @Test
        @DisplayName("5등 당첨 시 수익률을 올바르게 계산한다")
        void calculateYieldWithFifthRank() {
            //given
            lottoRepository.saveAll(List.of(
                    new Lotto(List.of(1, 2, 3, 10, 11, 12))
            ));
            winningNumberRepository.save(WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7));

            //when
            LottoWinningResponse response = lottoGameService.matchLottoNumbers();

            //then
            double expectedYield = (5000.0 * 100) / 1000;
            assertThat(response.yield()).isEqualTo(expectedYield);
        }
    }
}
