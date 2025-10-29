package lotto.application.dto.response;

public record LottoMatchResult(
        int matchCount,
        boolean isBonusMatch,
        int prize,
        int winnerCount
) {
    public static LottoMatchResult of(int matchCount, boolean isBonusMatch, int prize, int winnerCount) {
        return new LottoMatchResult(matchCount, isBonusMatch, prize, winnerCount);
    }
}
