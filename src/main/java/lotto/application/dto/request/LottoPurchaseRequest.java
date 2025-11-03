package lotto.application.dto.request;

import static lotto.domain.constant.LottoConstant.LOTTO_PRIZE;

public record LottoPurchaseRequest(int amount) {

    private static final int MAX_PURCHASE_AMOUNT = 1_000_000;

    public LottoPurchaseRequest {
        validate(amount);
    }

    public static LottoPurchaseRequest from(int amount) {
        return new LottoPurchaseRequest(amount);
    }

    private void validate(int amount) {
        validatePositive(amount);
        validateMaximum(amount);
        validateDivisible(amount);
    }

    private void validatePositive(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("구입 금액은 0보다 커야 합니다.");
        }
    }

    private void validateMaximum(int amount) {
        if (amount > MAX_PURCHASE_AMOUNT) {
            throw new IllegalArgumentException(String.format("구입 금액은 최대 %d원까지 가능합니다.", MAX_PURCHASE_AMOUNT));
        }
    }

    private void validateDivisible(int amount) {
        if (amount % LOTTO_PRIZE != 0) {
            throw new IllegalArgumentException(String.format("구입 금액은 %d원 단위여야 합니다.", LOTTO_PRIZE));
        }
    }
}
