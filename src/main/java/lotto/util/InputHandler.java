package lotto.util;

import java.util.function.Supplier;

public final class InputHandler {

    private static final String ERROR_PREFIX = "[ERROR] ";

    private InputHandler() {
        throw new UnsupportedOperationException("InputHandler 클래스는 인스턴스화할 수 없습니다.");
    }

    public static <T> T readWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(ERROR_PREFIX + e.getMessage());
            } catch (Exception e) {
                System.out.println(ERROR_PREFIX + "알 수 없는 오류가 발생했습니다.");
            }
        }
    }
}
