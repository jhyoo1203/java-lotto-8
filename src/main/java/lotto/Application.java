package lotto;

import lotto.config.AppConfig;
import lotto.presentation.LottoController;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        LottoController lottoController = appConfig.getLottoController();

        lottoController.start();
    }
}
