package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LottoRank 테스트")
class LottoRankTest {

    @Nested
    @DisplayName("from 메서드는")
    class FromMethod {

        @ParameterizedTest
        @CsvSource({
                "3, false, FIFTH",
                "4, false, FOURTH",
                "5, false, THIRD",
                "5, true, SECOND",
                "6, false, FIRST"
        })
        @DisplayName("일치 개수와 보너스 일치 여부에 따라 올바른 등수를 반환한다")
        void returnCorrectRankByMatchCountAndBonus(int matchCount, boolean bonusMatch, LottoRank expected) {
            //given
            //when
            LottoRank result = LottoRank.from(matchCount, bonusMatch);

            //then
            assertThat(result).isEqualTo(expected);
        }

        @ParameterizedTest
        @CsvSource({
                "0, false",
                "1, false",
                "2, false",
                "3, true",
                "4, true",
                "6, true"
        })
        @DisplayName("일치하는 순위가 없으면 null을 반환한다")
        void returnNullWhenNoRankMatches(int matchCount, boolean bonusMatch) {
            //given
            //when
            LottoRank result = LottoRank.from(matchCount, bonusMatch);

            //then
            assertThat(result).isNull();
        }
    }

    @Nested
    @DisplayName("getPrize 메서드는")
    class GetPrizeMethod {

        @ParameterizedTest
        @CsvSource({
                "FIFTH, 5000",
                "FOURTH, 50000",
                "THIRD, 1500000",
                "SECOND, 30000000",
                "FIRST, 2000000000"
        })
        @DisplayName("등수에 맞는 상금을 반환한다")
        void returnPrizeByRank(LottoRank rank, int expectedPrize) {
            //given
            //when
            int prize = rank.getPrize();

            //then
            assertThat(prize).isEqualTo(expectedPrize);
        }
    }

    @Nested
    @DisplayName("getMatchCount 메서드는")
    class GetMatchCountMethod {

        @ParameterizedTest
        @CsvSource({
                "FIFTH, 3",
                "FOURTH, 4",
                "THIRD, 5",
                "SECOND, 5",
                "FIRST, 6"
        })
        @DisplayName("등수에 맞는 일치 개수를 반환한다")
        void returnMatchCountByRank(LottoRank rank, int expectedMatchCount) {
            //given
            //when
            int matchCount = rank.getMatchCount();

            //then
            assertThat(matchCount).isEqualTo(expectedMatchCount);
        }
    }

    @Nested
    @DisplayName("hasBonus 메서드는")
    class HasBonusMethod {

        @ParameterizedTest
        @CsvSource({
                "FIFTH, false",
                "FOURTH, false",
                "THIRD, false",
                "SECOND, true",
                "FIRST, false"
        })
        @DisplayName("등수에 맞는 보너스 필요 여부를 반환한다")
        void returnHasBonusByRank(LottoRank rank, boolean expectedHasBonus) {
            //given
            //when
            boolean hasBonus = rank.hasBonus();

            //then
            assertThat(hasBonus).isEqualTo(expectedHasBonus);
        }
    }
}
