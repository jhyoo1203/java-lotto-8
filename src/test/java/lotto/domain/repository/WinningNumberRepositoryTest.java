package lotto.domain.repository;

import lotto.domain.WinningNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("WinningNumberRepository 테스트")
class WinningNumberRepositoryTest {

    private final WinningNumberRepository winningNumberRepository = new WinningNumberRepository();

    @AfterEach
    void clearRepository() throws Exception {
        Field winningNumberField = WinningNumberRepository.class.getDeclaredField("winningNumber");
        winningNumberField.setAccessible(true);
        winningNumberField.set(null, null);
    }

    @Nested
    @DisplayName("save 메서드는")
    class SaveMethod {

        @Test
        @DisplayName("당첨 번호를 저장한다")
        void saveWinningNumber() {
            //given
            WinningNumber winningNumber = WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7);

            //when
            winningNumberRepository.save(winningNumber);

            //then
            WinningNumber savedWinningNumber = winningNumberRepository.findWinningNumber();
            assertThat(savedWinningNumber).isEqualTo(winningNumber);
        }

        @Test
        @DisplayName("null을 저장하려고 하면 예외가 발생한다")
        void throwExceptionWhenSaveNull() {
            //given
            WinningNumber winningNumber = null;

            //when
            //then
            assertThatThrownBy(() -> winningNumberRepository.save(winningNumber))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("null");
        }

        @Test
        @DisplayName("당첨 번호를 덮어쓸 수 있다")
        void overwriteWinningNumber() {
            //given
            WinningNumber firstWinningNumber = WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7);
            WinningNumber secondWinningNumber = WinningNumber.of(List.of(10, 20, 30, 40, 41, 42), 43);
            winningNumberRepository.save(firstWinningNumber);

            //when
            winningNumberRepository.save(secondWinningNumber);

            //then
            WinningNumber savedWinningNumber = winningNumberRepository.findWinningNumber();
            assertThat(savedWinningNumber).isEqualTo(secondWinningNumber);
        }
    }

    @Nested
    @DisplayName("findWinningNumber 메서드는")
    class FindWinningNumberMethod {

        @Test
        @DisplayName("저장된 당첨 번호를 반환한다")
        void returnWinningNumber() {
            //given
            WinningNumber winningNumber = WinningNumber.of(List.of(1, 2, 3, 4, 5, 6), 7);
            winningNumberRepository.save(winningNumber);

            //when
            WinningNumber foundWinningNumber = winningNumberRepository.findWinningNumber();

            //then
            assertThat(foundWinningNumber).isEqualTo(winningNumber);
            assertThat(foundWinningNumber.getWinningNumbers()).isEqualTo(List.of(1, 2, 3, 4, 5, 6));
            assertThat(foundWinningNumber.getBonusNumber()).isEqualTo(7);
        }

        @Test
        @DisplayName("저장된 당첨 번호가 없으면 예외가 발생한다")
        void throwExceptionWhenNoWinningNumber() {
            //given
            //when
            //then
            assertThatThrownBy(winningNumberRepository::findWinningNumber)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("당첨 번호가 등록되지 않았습니다");
        }
    }
}
