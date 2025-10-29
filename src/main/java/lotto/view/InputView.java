package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.util.Serializer;

import java.util.List;

public class InputView {

    private static final String INPUT_PURCHASE_AMOUNT = "구입금액을 입력해 주세요.";
    private static final String INPUT_WINNING_NUMBERS = "\n당첨 번호를 입력해 주세요.";
    private static final String INPUT_BONUS_NUMBER = "\n보너스 번호를 입력해 주세요.";

    public int readPurchaseAmount() {
        System.out.println(INPUT_PURCHASE_AMOUNT);
        return Serializer.parseInt(Console.readLine());
    }

    public List<Integer> readWinningNumbers() {
        System.out.println(INPUT_WINNING_NUMBERS);
        return Serializer.parseToIntegers(Console.readLine());
    }

    public int readBonusNumber() {
        System.out.println(INPUT_BONUS_NUMBER);
        return Serializer.parseInt(Console.readLine());
    }
}
