package lotto.application;

import camp.nextstep.edu.missionutils.Randoms;
import lotto.application.dto.request.LottoPurchaseRequest;
import lotto.application.dto.response.LottoPurchaseResponse;
import lotto.domain.Lotto;
import lotto.domain.repository.LottoRepository;

import java.util.ArrayList;
import java.util.List;

import static lotto.domain.constant.LottoConstant.LOTTO_NUMBER_SIZE;
import static lotto.domain.constant.LottoConstant.MAX_LOTTO_NUMBER;
import static lotto.domain.constant.LottoConstant.MIN_LOTTO_NUMBER;

public class LottoOrderService {

    private static final int LOTTO_PRIZE = 1000;

    private final LottoRepository lottoRepository;

    public LottoOrderService(LottoRepository lottoRepository) {
        this.lottoRepository = lottoRepository;
    }

    public LottoPurchaseResponse purchase(LottoPurchaseRequest request) {
        int lottoCount = request.amount() / LOTTO_PRIZE;

        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < lottoCount; i++) {
            List<Integer> lottoNumbers = generateLottoNumbers();
            lottos.add(new Lotto(lottoNumbers));
        }

        lottoRepository.saveAll(lottos);

        return LottoPurchaseResponse.of(lottoCount, lottos);
    }

    public List<Integer> generateLottoNumbers() {
        return Randoms.pickUniqueNumbersInRange(MIN_LOTTO_NUMBER, MAX_LOTTO_NUMBER, LOTTO_NUMBER_SIZE)
                .stream()
                .sorted()
                .toList();
    }
}
