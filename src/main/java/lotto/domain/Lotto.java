package lotto.domain;

import java.util.List;

public class Lotto {

    private static final int LOTTO_NUMBER_SIZE = 6;
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        validateNotNull(numbers);
        validateSize(numbers);
        validateNumbersInSinglePass(numbers);
    }

    private void validateNotNull(List<Integer> numbers) {
        if (numbers == null) {
            throw new NullPointerException("로또 번호는 null일 수 없습니다.");
        }
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_SIZE) {
            throw new IllegalArgumentException("로또 번호는 6개여야 합니다.");
        }
    }

    private void validateNumbersInSinglePass(List<Integer> numbers) {
        java.util.Set<Integer> uniqueNumbers = new java.util.HashSet<>();

        for (Integer number : numbers) {
            if (number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER) {
                throw new IllegalArgumentException("로또 번호는 1부터 45 사이의 숫자여야 합니다.");
            }
            if (!uniqueNumbers.add(number)) {
                throw new IllegalArgumentException("로또 번호에 중복된 숫자가 있을 수 없습니다.");
            }
        }
    }

    public int countMatches(List<Integer> winningNumbers) {
        return (int) numbers.stream()
                .filter(winningNumbers::contains)
                .count();
    }

    public boolean containsBonus(int bonusNumber) {
        return numbers.contains(bonusNumber);
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}
