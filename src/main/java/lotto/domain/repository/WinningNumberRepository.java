package lotto.domain.repository;

import lotto.domain.WinningNumber;

public class WinningNumberRepository {

    private static WinningNumber winningNumber;

    public void save(WinningNumber number) {
        if (number == null) {
            throw new IllegalArgumentException("당첨 번호 객체는 null일 수 없습니다.");
        }

        winningNumber = number;
    }

    public WinningNumber findWinningNumber() {
        if (winningNumber == null) {
            throw new IllegalStateException("당첨 번호가 등록되지 않았습니다.");
        }

        return winningNumber;
    }
}
