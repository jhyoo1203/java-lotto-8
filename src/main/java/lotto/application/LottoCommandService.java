package lotto.application;

import lotto.application.dto.request.WinningNumberRequest;
import lotto.domain.WinningNumber;
import lotto.domain.repository.WinningNumberRepository;

public class LottoCommandService {

    private final WinningNumberRepository winningNumberRepository;

    public LottoCommandService(WinningNumberRepository winningNumberRepository) {
        this.winningNumberRepository = winningNumberRepository;
    }

    public void registerWinningNumbers(WinningNumberRequest request) {
        winningNumberRepository.save(
                WinningNumber.of(
                        request.winningNumbers(),
                        request.bonusNumber()
                )
        );
    }
}
