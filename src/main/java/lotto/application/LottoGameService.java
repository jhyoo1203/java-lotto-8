package lotto.application;

import lotto.application.dto.response.LottoMatchResult;
import lotto.application.dto.response.LottoWinningResponse;
import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.domain.WinningNumber;
import lotto.domain.repository.LottoRepository;
import lotto.domain.repository.WinningNumberRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoGameService {

    private static final int LOTTO_PRICE = 1000;
    private static final int PERCENT = 100;

    private final LottoRepository lottoRepository;
    private final WinningNumberRepository winningNumberRepository;

    public LottoGameService(LottoRepository lottoRepository, WinningNumberRepository winningNumberRepository) {
        this.lottoRepository = lottoRepository;
        this.winningNumberRepository = winningNumberRepository;
    }

    public LottoWinningResponse matchLottoNumbers() {
        List<Lotto> lottos = lottoRepository.findAll();
        WinningNumber winningNumber = winningNumberRepository.findWinningNumber();

        Map<LottoRank, Integer> rankCount = countRanks(lottos, winningNumber);
        List<LottoMatchResult> matchResults = createMatchResults(rankCount);
        int totalPrize = calculateTotalPrize(rankCount);
        double yield = calculateYield(lottos.size(), totalPrize);

        return LottoWinningResponse.of(matchResults, yield);
    }

    private Map<LottoRank, Integer> countRanks(List<Lotto> lottos, WinningNumber winningNumber) {
        Map<LottoRank, Integer> rankCount = new HashMap<>();

        for (Lotto lotto : lottos) {
            int matchCount = lotto.countMatches(winningNumber.getWinningNumbers());
            boolean bonusMatch = lotto.containsBonus(winningNumber.getBonusNumber());

            LottoRank rank = LottoRank.from(matchCount, bonusMatch);
            if (rank != null) {
                rankCount.put(rank, rankCount.getOrDefault(rank, 0) + 1);
            }
        }

        return rankCount;
    }

    private List<LottoMatchResult> createMatchResults(Map<LottoRank, Integer> rankCount) {
        List<LottoMatchResult> matchResults = new ArrayList<>();

        for (LottoRank rank : LottoRank.values()) {
            int count = rankCount.getOrDefault(rank, 0);
            matchResults.add(LottoMatchResult.of(
                    rank.getMatchCount(),
                    rank.hasBonus(),
                    rank.getPrize(),
                    count
            ));
        }

        return matchResults;
    }

    private int calculateTotalPrize(Map<LottoRank, Integer> rankCount) {
        return rankCount.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrize() * entry.getValue())
                .sum();
    }

    private double calculateYield(int lottoCount, int totalPrize) {
        if (totalPrize == 0) {
            return 0;
        }

        return (double) totalPrize * PERCENT / (lottoCount * LOTTO_PRICE);
    }
}
