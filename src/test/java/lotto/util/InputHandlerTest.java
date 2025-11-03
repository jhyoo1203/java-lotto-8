package lotto.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("InputHandler 테스트")
class InputHandlerTest {

    @Nested
    @DisplayName("readWithRetry 메서드는")
    class ReadWithRetryMethod {

        @Test
        @DisplayName("정상적인 입력이면 결과를 반환한다")
        void returnResultWhenSuccessful() {
            //given
            Supplier<Integer> supplier = () -> 100;

            //when
            Integer result = InputHandler.readWithRetry(supplier);

            //then
            assertThat(result).isEqualTo(100);
        }

        @Test
        @DisplayName("IllegalArgumentException 발생 시 재시도한다")
        void retryOnIllegalArgumentException() {
            //given
            AtomicInteger attemptCount = new AtomicInteger(0);
            Supplier<Integer> supplier = () -> {
                if (attemptCount.incrementAndGet() < 3) {
                    throw new IllegalArgumentException("잘못된 입력입니다.");
                }
                return 100;
            };

            //when
            Integer result = InputHandler.readWithRetry(supplier);

            //then
            assertThat(result).isEqualTo(100);
            assertThat(attemptCount.get()).isEqualTo(3);
        }

        @Test
        @DisplayName("IllegalStateException 발생 시 재시도한다")
        void retryOnIllegalStateException() {
            //given
            AtomicInteger attemptCount = new AtomicInteger(0);
            Supplier<Integer> supplier = () -> {
                if (attemptCount.incrementAndGet() < 2) {
                    throw new IllegalStateException("잘못된 상태입니다.");
                }
                return 100;
            };

            //when
            Integer result = InputHandler.readWithRetry(supplier);

            //then
            assertThat(result).isEqualTo(100);
            assertThat(attemptCount.get()).isEqualTo(2);
        }

        @Test
        @DisplayName("다른 예외는 재시도하지 않고 던진다")
        void throwOtherExceptions() {
            //given
            Supplier<Integer> supplier = () -> {
                throw new RuntimeException("예상치 못한 오류");
            };

            //when
            //then
            assertThatThrownBy(() -> InputHandler.readWithRetry(supplier))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("예상치 못한 오류");
        }
    }
}
