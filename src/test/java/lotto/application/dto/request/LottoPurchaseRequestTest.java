package lotto.application.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("LottoPurchaseRequest 테스트")
class LottoPurchaseRequestTest {

    @Nested
    @DisplayName("from 메서드는")
    class FromMethod {

        @Test
        @DisplayName("정상적인 금액으로 요청 객체를 생성한다")
        void createRequestWithValidAmount() {
            //given
            int amount = 5000;

            //when
            LottoPurchaseRequest request = LottoPurchaseRequest.from(amount);

            //then
            assertThat(request.amount()).isEqualTo(5000);
        }

        @ParameterizedTest
        @ValueSource(ints = {0, -1000, -100})
        @DisplayName("0 이하의 금액이면 예외가 발생한다")
        void throwExceptionWhenNonPositiveAmount(int amount) {
            //given
            //when
            //then
            assertThatThrownBy(() -> LottoPurchaseRequest.from(amount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("0보다 커야");
        }

        @ParameterizedTest
        @ValueSource(ints = {1500, 2300, 999, 1})
        @DisplayName("1000원 단위가 아니면 예외가 발생한다")
        void throwExceptionWhenNotDivisibleByThousand(int amount) {
            //given
            //when
            //then
            assertThatThrownBy(() -> LottoPurchaseRequest.from(amount))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("1,000원 단위");
        }
    }
}
