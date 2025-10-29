package lotto.application.dto.request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lotto.domain.constant.LottoConstant.MAX_LOTTO_NUMBER;
import static lotto.domain.constant.LottoConstant.MIN_LOTTO_NUMBER;
import static lotto.domain.constant.LottoConstant.LOTTO_NUMBER_SIZE;

public record WinningNumberRequest(
        List<Integer> winningNumbers,
        int bonusNumber
) {

    public WinningNumberRequest {
        validate(winningNumbers, bonusNumber);
    }

    public static WinningNumberRequest of(List<Integer> winningNumbers, int bonusNumber) {
        return new WinningNumberRequest(winningNumbers, bonusNumber);
    }

    private void validate(List<Integer> winningNumbers, int bonusNumber) {
        validateNotNull(winningNumbers);
        validateWinningNumbersSize(winningNumbers);
        validateWinningNumbers(winningNumbers);
        validateBonusNumberRange(winningNumbers, bonusNumber);
    }

    private void validateNotNull(List<Integer> winningNumbers) {
        if (winningNumbers == null) {
            throw new NullPointerException("당첨 번호는 null일 수 없습니다.");
        }
    }

    private void validateWinningNumbersSize(List<Integer> winningNumbers) {
        if (winningNumbers.size() != LOTTO_NUMBER_SIZE) {
            throw new IllegalArgumentException(String.format("당첨 번호는 %d개여야 합니다.", LOTTO_NUMBER_SIZE));
        }
    }

    private void validateWinningNumbers(List<Integer> winningNumbers) {
        Set<Integer> uniqueNumbers = new HashSet<>();

        for (Integer number : winningNumbers) {
            if (number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER) {
                throw new IllegalArgumentException(
                        String.format("당첨 번호는 %d부터 %d 사이의 숫자여야 합니다.", MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER)
                );
            }

            if (!uniqueNumbers.add(number)) {
                throw new IllegalArgumentException("당첨 번호에 중복된 숫자가 있을 수 없습니다.");
            }
        }
    }

    private void validateBonusNumberRange(List<Integer> winningNumbers, int bonusNumber) {
        if (bonusNumber < MIN_LOTTO_NUMBER || bonusNumber > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException(
                    String.format("보너스 번호는 %d부터 %d 사이의 숫자여야 합니다.", MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER)
            );
        }

        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException("보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
