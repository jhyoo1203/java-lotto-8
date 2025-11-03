package lotto.application;

import lotto.application.dto.request.WinningNumberRequest;
import lotto.domain.WinningNumber;
import lotto.domain.repository.WinningNumberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LottoCommandService 테스트")
class LottoCommandServiceTest {

    private final WinningNumberRepository winningNumberRepository = new WinningNumberRepository();
    private final LottoCommandService lottoCommandService = new LottoCommandService(winningNumberRepository);

    @AfterEach
    void clearRepository() throws Exception {
        Field winningNumberField = WinningNumberRepository.class.getDeclaredField("winningNumber");
        winningNumberField.setAccessible(true);
        winningNumberField.set(null, null);
    }

    @Nested
    @DisplayName("registerWinningNumbers 메서드는")
    class RegisterWinningNumbersMethod {

        @Test
        @DisplayName("당첨 번호를 등록한다")
        void registerWinningNumbers() {
            //given
            WinningNumberRequest request = WinningNumberRequest.of(List.of(1, 2, 3, 4, 5, 6), 7);

            //when
            lottoCommandService.registerWinningNumbers(request);

            //then
            WinningNumber savedWinningNumber = winningNumberRepository.findWinningNumber();
            assertThat(savedWinningNumber).isNotNull();
            assertThat(savedWinningNumber.getWinningNumbers()).isEqualTo(List.of(1, 2, 3, 4, 5, 6));
            assertThat(savedWinningNumber.getBonusNumber()).isEqualTo(7);
        }

        @Test
        @DisplayName("새로운 당첨 번호를 등록하면 기존 당첨 번호가 대체된다")
        void overwriteWinningNumbers() {
            //given
            WinningNumberRequest firstRequest = WinningNumberRequest.of(List.of(1, 2, 3, 4, 5, 6), 7);
            WinningNumberRequest secondRequest = WinningNumberRequest.of(List.of(10, 20, 30, 40, 41, 42), 43);
            lottoCommandService.registerWinningNumbers(firstRequest);

            //when
            lottoCommandService.registerWinningNumbers(secondRequest);

            //then
            WinningNumber savedWinningNumber = winningNumberRepository.findWinningNumber();
            assertThat(savedWinningNumber.getWinningNumbers()).isEqualTo(List.of(10, 20, 30, 40, 41, 42));
            assertThat(savedWinningNumber.getBonusNumber()).isEqualTo(43);
        }
    }
}
