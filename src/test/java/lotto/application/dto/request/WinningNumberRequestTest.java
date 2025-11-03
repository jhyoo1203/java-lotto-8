package lotto.application.dto.request;

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

@DisplayName("WinningNumberRequest 테스트")
class WinningNumberRequestTest {

    @Nested
    @DisplayName("of 메서드는")
    class OfMethod {

        @Test
        @DisplayName("정상적인 당첨 번호와 보너스 번호로 요청 객체를 생성한다")
        void createRequestWithValidNumbers() {
            //given
            List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
            int bonusNumber = 7;

            //when
            WinningNumberRequest request = WinningNumberRequest.of(winningNumbers, bonusNumber);

            //then
            assertThat(request.winningNumbers()).isEqualTo(winningNumbers);
            assertThat(request.bonusNumber()).isEqualTo(bonusNumber);
        }

        @ParameterizedTest
        @MethodSource("provideInvalidSizeNumbers")
        @DisplayName("당첨 번호가 6개가 아니면 예외가 발생한다")
        void throwExceptionWhenInvalidSize(List<Integer> winningNumbers) {
            //given
            int bonusNumber = 7;

            //when
            //then
            assertThatThrownBy(() -> WinningNumberRequest.of(winningNumbers, bonusNumber))
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
        @DisplayName("당첨 번호에 중복이 있으면 예외가 발생한다")
        void throwExceptionWhenDuplicateWinningNumbers() {
            //given
            List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 5);
            int bonusNumber = 7;

            //when
            //then
            assertThatThrownBy(() -> WinningNumberRequest.of(winningNumbers, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("중복");
        }

        @ParameterizedTest
        @MethodSource("provideInvalidRangeWinningNumbers")
        @DisplayName("당첨 번호가 1부터 45 범위를 벗어나면 예외가 발생한다")
        void throwExceptionWhenWinningNumbersOutOfRange(List<Integer> winningNumbers) {
            //given
            int bonusNumber = 7;

            //when
            //then
            assertThatThrownBy(() -> WinningNumberRequest.of(winningNumbers, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1부터 45 사이");
        }

        private static Stream<Arguments> provideInvalidRangeWinningNumbers() {
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
            assertThatThrownBy(() -> WinningNumberRequest.of(winningNumbers, bonusNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1부터 45 사이");
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
            assertThatThrownBy(() -> WinningNumberRequest.of(winningNumbers, bonusNumber))
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
            assertThatThrownBy(() -> WinningNumberRequest.of(winningNumbers, bonusNumber))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessageContaining("null");
        }
    }
}
