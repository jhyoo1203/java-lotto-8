package lotto.presentation;

import lotto.application.LottoCommandService;
import lotto.application.LottoGameService;
import lotto.application.LottoOrderService;
import lotto.application.dto.request.LottoPurchaseRequest;
import lotto.application.dto.request.WinningNumberRequest;
import lotto.application.dto.response.LottoPurchaseResponse;
import lotto.application.dto.response.LottoWinningResponse;
import lotto.util.InputHandler;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoOrderService lottoOrderService;
    private final LottoCommandService lottoCommandService;
    private final LottoGameService lottoGameService;

    public LottoController(InputView inputView,
                           OutputView outputView,
                           LottoOrderService lottoOrderService,
                           LottoCommandService lottoCommandService,
                           LottoGameService lottoGameService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoOrderService = lottoOrderService;
        this.lottoCommandService = lottoCommandService;
        this.lottoGameService = lottoGameService;
    }

    public void start() {
        initializeLotto();
    }

    private void initializeLotto() {
        purchaseLottos();
        registerWinningNumbers();
        announceResult();
    }

    private void purchaseLottos() {
        LottoPurchaseRequest request = InputHandler.readWithRetry(() -> {
            int purchaseAmount = inputView.readPurchaseAmount();
            return LottoPurchaseRequest.from(purchaseAmount);
        });
        LottoPurchaseResponse response = lottoOrderService.purchase(request);
        outputView.printPurchasedLottos(response);
    }

    private void registerWinningNumbers() {
        WinningNumberRequest request = InputHandler.readWithRetry(() -> {
            List<Integer> winningNumbers = inputView.readWinningNumbers();
            int bonusNumber = inputView.readBonusNumber();
            return WinningNumberRequest.of(winningNumbers, bonusNumber);
        });
        lottoCommandService.registerWinningNumbers(request);
    }

    private void announceResult() {
        LottoWinningResponse lottoWinningResponse = lottoGameService.matchLottoNumbers();
        outputView.printLottoResults(lottoWinningResponse);
    }
}
