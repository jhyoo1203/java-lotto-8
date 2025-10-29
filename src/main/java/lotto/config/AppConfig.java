package lotto.config;

import lotto.application.LottoCommandService;
import lotto.application.LottoGameService;
import lotto.application.LottoOrderService;
import lotto.domain.repository.LottoRepository;
import lotto.domain.repository.WinningNumberRepository;
import lotto.presentation.LottoController;
import lotto.view.InputView;
import lotto.view.OutputView;

public class AppConfig {

    // 뷰 DI
    private static final InputView inputView = new InputView();
    private static final OutputView outputView = new OutputView();

    // 레포지토리 DI
    private static final LottoRepository lottoRepository = new LottoRepository();
    private static final WinningNumberRepository winningNumberRepository = new WinningNumberRepository();

    // 서비스 DI
    private static final LottoCommandService lottoCommandService = new LottoCommandService(
            winningNumberRepository
    );
    private static final LottoGameService lottoGameService = new LottoGameService(
            lottoRepository,
            winningNumberRepository
    );
    private static final LottoOrderService lottoOrderService = new LottoOrderService(
            lottoRepository
    );


    // 컨트롤러 DI
    private static final LottoController lottoController = new LottoController(
            inputView,
            outputView,
            lottoOrderService,
            lottoCommandService,
            lottoGameService
    );

    public LottoController getLottoController() {
        return lottoController;
    }
}
