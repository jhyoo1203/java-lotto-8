package lotto.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Serializer 테스트")
class SerializerTest {

    @Nested
    @DisplayName("parseToIntegers 메서드는")
    class ParseToIntegersMethod {

        @ParameterizedTest
        @MethodSource("provideValidInputs")
        @DisplayName("쉼표로 구분된 문자열을 정수 리스트로 변환한다")
        void parseCommaSeparatedString(String input, List<Integer> expected) {
            //given
            //when
            List<Integer> result = Serializer.parseToIntegers(input);

            //then
            assertThat(result).isEqualTo(expected);
        }

        private static Stream<Arguments> provideValidInputs() {
            return Stream.of(
                    Arguments.of("1,2,3,4,5,6", List.of(1, 2, 3, 4, 5, 6)),
                    Arguments.of("10,20,30", List.of(10, 20, 30)),
                    Arguments.of("1", List.of(1))
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "   "})
        @DisplayName("빈 문자열이면 예외가 발생한다")
        void throwExceptionWhenBlankString(String input) {
            //given
            //when
            //then
            assertThatThrownBy(() -> Serializer.parseToIntegers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("비어 있을 수 없습니다");
        }

        @Test
        @DisplayName("null이면 예외가 발생한다")
        void throwExceptionWhenNull() {
            //given
            String input = null;

            //when
            //then
            assertThatThrownBy(() -> Serializer.parseToIntegers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("비어 있을 수 없습니다");
        }

        @ParameterizedTest
        @ValueSource(strings = {"a,b,c", "1,2,abc", "1.5,2.5"})
        @DisplayName("숫자가 아닌 문자열이 포함되면 예외가 발생한다")
        void throwExceptionWhenInvalidFormat(String input) {
            //given
            //when
            //then
            assertThatThrownBy(() -> Serializer.parseToIntegers(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("숫자 형식이 올바르지 않습니다");
        }
    }

    @Nested
    @DisplayName("parseInt 메서드는")
    class ParseIntMethod {

        @ParameterizedTest
        @MethodSource("provideValidNumbers")
        @DisplayName("문자열을 정수로 변환한다")
        void parseStringToInteger(String input, int expected) {
            //given
            //when
            int result = Serializer.parseInt(input);

            //then
            assertThat(result).isEqualTo(expected);
        }

        private static Stream<Arguments> provideValidNumbers() {
            return Stream.of(
                    Arguments.of("1", 1),
                    Arguments.of("100", 100),
                    Arguments.of("5000", 5000),
                    Arguments.of("-1", -1)
            );
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "   "})
        @DisplayName("빈 문자열이면 예외가 발생한다")
        void throwExceptionWhenBlankString(String input) {
            //given
            //when
            //then
            assertThatThrownBy(() -> Serializer.parseInt(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("비어 있을 수 없습니다");
        }

        @Test
        @DisplayName("null이면 예외가 발생한다")
        void throwExceptionWhenNull() {
            //given
            String input = null;

            //when
            //then
            assertThatThrownBy(() -> Serializer.parseInt(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("비어 있을 수 없습니다");
        }

        @ParameterizedTest
        @ValueSource(strings = {"abc", "1.5", "one", "12.34"})
        @DisplayName("숫자 형식이 아니면 예외가 발생한다")
        void throwExceptionWhenInvalidFormat(String input) {
            //given
            //when
            //then
            assertThatThrownBy(() -> Serializer.parseInt(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("숫자 형식이 올바르지 않습니다");
        }
    }
}
