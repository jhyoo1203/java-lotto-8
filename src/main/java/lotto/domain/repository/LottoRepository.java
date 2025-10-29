package lotto.domain.repository;

import lotto.domain.Lotto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoRepository {

    private static final Map<Integer, Lotto> lottoMap = new HashMap<>();

    private static int idx = 1;

    public void saveAll(List<Lotto> lottos) {
        if (lottos == null || lottos.isEmpty()) {
            throw new IllegalArgumentException("저장할 로또가 없습니다.");
        }

        for (Lotto lotto : lottos) {
            lottoMap.put(idx++, lotto);
        }
    }

    public List<Lotto> findAll() {
        if (lottoMap.isEmpty()) {
            throw new IllegalStateException("구매한 로또가 없습니다.");
        }

        return lottoMap.keySet().stream()
                .sorted()
                .map(lottoMap::get)
                .toList();
    }
}
