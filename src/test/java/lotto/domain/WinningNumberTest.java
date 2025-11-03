package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("WinningNumber 테스트")
class WinningNumberTest {

    @Nested
    @DisplayName("of 메서드는")
    class OfMethod {

        @Test
        @DisplayName("정상적인 당첨 번호와 보너스 번호로 객체를 생성한다")
        void createWinningNumberWithValidNumbers() {
            //given
            List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
            int bonusNumber = 7;

            //when
            WinningNumber winningNumber = WinningNumber.of(winningNumbers, bonusNumber);

            //then
            assertThat(winningNumber).isNotNull();
            assertThat(winningNumber.getWinningNumbers()).isEqualTo(winningNumbers);
            assertThat(winningNumber.getBonusNumber()).isEqualTo(bonusNumber);
        }

        @ParameterizedTest
        @MethodSource("provideInvalidSizeNumbers")
        @DisplayName("당첨 번호의 개수가 6개가 아니면 예외가 발생한다")
        void throwExceptionWhenInvalidSize(List<Integer> winningNumbers) {
            //given
            int bonusNumber = 7;

            //when
            //then
            assertThatThrownBy(() -> WinningNumber.of(winningNumbers, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("6개");
        }

        private static Stream<Arguments> provideInvalidSizeNumbers() {
            return Stream.of(
                    Arguments.of(List.of(1, 2, 3, 4, 5)),
                    Arguments.of(List.of(1, 2, 3, 4, 5, 6, 7)),
                    Arguments.of(List.of())
            );
        }

        @Test
        @DisplayName("당첨 번호에 중복된 숫자가 있으면 예외가 발생한다")
        void throwExceptionWhenDuplicateNumbers() {
            //given
            List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 5);
            int bonusNumber = 7;

            //when
            //then
            assertThatThrownBy(() -> WinningNumber.of(winningNumbers, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("중복");
        }

        @ParameterizedTest
        @MethodSource("provideInvalidRangeNumbers")
        @DisplayName("당첨 번호가 1부터 45 범위를 벗어나면 예외가 발생한다")
        void throwExceptionWhenWinningNumbersOutOfRange(List<Integer> winningNumbers) {
            //given
            int bonusNumber = 7;

            //when
            //then
            assertThatThrownBy(() -> WinningNumber.of(winningNumbers, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1부터 45");
        }

        private static Stream<Arguments> provideInvalidRangeNumbers() {
            return Stream.of(
                    Arguments.of(List.of(0, 1, 2, 3, 4, 5)),
                    Arguments.of(List.of(1, 2, 3, 4, 5, 46))
            );
        }

        @ParameterizedTest
        @MethodSource("provideInvalidBonusNumbers")
        @DisplayName("보너스 번호가 1부터 45 범위를 벗어나면 예외가 발생한다")
        void throwExceptionWhenBonusNumberOutOfRange(int bonusNumber) {
            //given
            List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);

            //when
            //then
            assertThatThrownBy(() -> WinningNumber.of(winningNumbers, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1부터 45");
        }

        private static Stream<Arguments> provideInvalidBonusNumbers() {
            return Stream.of(
                    Arguments.of(0),
                    Arguments.of(46),
                    Arguments.of(-1)
            );
        }

        @ParameterizedTest
        @MethodSource("provideDuplicateBonusNumbers")
        @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다")
        void throwExceptionWhenBonusNumberDuplicatesWinningNumbers(int bonusNumber) {
            //given
            List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);

            //when
            //then
            assertThatThrownBy(() -> WinningNumber.of(winningNumbers, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("보너스 번호는 당첨 번호와 중복");
        }

        private static Stream<Arguments> provideDuplicateBonusNumbers() {
            return Stream.of(
                    Arguments.of(1),
                    Arguments.of(3),
                    Arguments.of(6)
            );
        }

        @Test
        @DisplayName("당첨 번호가 null이면 예외가 발생한다")
        void throwExceptionWhenNull() {
            //given
            List<Integer> winningNumbers = null;
            int bonusNumber = 7;

            //when
            //then
            assertThatThrownBy(() -> WinningNumber.of(winningNumbers, bonusNumber))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("null");
        }
    }
}
