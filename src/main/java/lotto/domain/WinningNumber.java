package lotto.domain;

import java.util.List;

public class WinningNumber {

    private static final int WINNING_NUMBER_SIZE = 6;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    private final List<Integer> winningNumbers;
    private final int bonusNumber;

    private WinningNumber(List<Integer> winningNumbers, int bonusNumber) {
        validate(winningNumbers, bonusNumber);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    public static WinningNumber of(List<Integer> winningNumbers, int bonusNumber) {
        return new WinningNumber(winningNumbers, bonusNumber);
    }

    private void validate(List<Integer> winningNumbers, int bonusNumber) {
        validateNotNull(winningNumbers);
        validateWinningNumbersSize(winningNumbers);
        validateWinningNumbersInSinglePass(winningNumbers);
        validateBonusNumberRange(bonusNumber);
        validateBonusNumberNotInWinningNumbers(winningNumbers, bonusNumber);
    }

    private void validateNotNull(List<Integer> winningNumbers) {
        if (winningNumbers == null) {
            throw new NullPointerException("당첨 번호는 null일 수 없습니다.");
        }
    }

    private void validateWinningNumbersSize(List<Integer> winningNumbers) {
        if (winningNumbers.size() != WINNING_NUMBER_SIZE) {
            throw new IllegalArgumentException("당첨 번호는 6개여야 합니다.");
        }
    }

    private void validateWinningNumbersInSinglePass(List<Integer> winningNumbers) {
        java.util.Set<Integer> uniqueNumbers = new java.util.HashSet<>();

        for (Integer number : winningNumbers) {
            if (number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER) {
                throw new IllegalArgumentException("당첨 번호는 1부터 45 사이의 숫자여야 합니다.");
            }
            if (!uniqueNumbers.add(number)) {
                throw new IllegalArgumentException("당첨 번호에 중복된 숫자가 있을 수 없습니다.");
            }
        }
    }

    private void validateBonusNumberRange(int bonusNumber) {
        if (bonusNumber < MIN_LOTTO_NUMBER || bonusNumber > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException("보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private void validateBonusNumberNotInWinningNumbers(List<Integer> winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }
}
