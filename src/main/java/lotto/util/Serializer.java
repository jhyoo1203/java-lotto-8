package lotto.util;

import java.util.List;

public final class Serializer {

    private static final String COMMA = ",";

    private Serializer() {
        throw new UnsupportedOperationException("Serializer 클래스는 인스턴스화할 수 없습니다.");
    }

    public static List<Integer> parseToIntegers(String input) {
        validateBlank(input);
        List<String> parts = splitByComma(input);

        return parts.stream()
                .map(Serializer::parseInt)
                .toList();
    }

    private static List<String> splitByComma(String input) {
        return List.of(input.split(COMMA));
    }

    public static int parseInt(String input) {
        validateBlank(input);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("숫자 형식이 올바르지 않습니다. input: %s", input));
        }
    }

    private static void validateBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("입력 값이 비어 있을 수 없습니다.");
        }
    }
}
