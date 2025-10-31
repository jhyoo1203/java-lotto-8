package lotto.application.dto.request;

import static lotto.domain.constant.LottoConstant.LOTTO_PRIZE;

public record LottoPurchaseRequest(int amount) {

    public LottoPurchaseRequest {
        validate(amount);
    }

    public static LottoPurchaseRequest from(int amount) {
        return new LottoPurchaseRequest(amount);
    }

    private void validate(int amount) {
        validatePositive(amount);
        validateDivisible(amount);
    }

    private void validatePositive(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("구입 금액은 0보다 커야 합니다.");
        }
    }

    private void validateDivisible(int amount) {
        if (amount % LOTTO_PRIZE != 0) {
            throw new IllegalArgumentException("구입 금액은 1,000원 단위로 입력해야 합니다.");
        }
    }
}
