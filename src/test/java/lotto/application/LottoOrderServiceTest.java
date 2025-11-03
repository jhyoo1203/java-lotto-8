package lotto.application;

import lotto.application.dto.request.LottoPurchaseRequest;
import lotto.application.dto.response.LottoPurchaseResponse;
import lotto.domain.Lotto;
import lotto.domain.repository.LottoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LottoOrderService 테스트")
class LottoOrderServiceTest {

    private final LottoRepository lottoRepository = new LottoRepository();
    private final LottoOrderService lottoOrderService = new LottoOrderService(lottoRepository);

    @AfterEach
    void clearRepository() throws Exception {
        Field lottoMapField = LottoRepository.class.getDeclaredField("lottoMap");
        lottoMapField.setAccessible(true);
        Map<?, ?> lottoMap = (Map<?, ?>) lottoMapField.get(null);
        lottoMap.clear();

        Field idxField = LottoRepository.class.getDeclaredField("idx");
        idxField.setAccessible(true);
        idxField.set(null, 1);
    }

    @Nested
    @DisplayName("purchase 메서드는")
    class PurchaseMethod {

        @Test
        @DisplayName("구입 금액에 맞는 로또를 구입하고 응답을 반환한다")
        void purchaseLottos() {
            //given
            LottoPurchaseRequest request = LottoPurchaseRequest.from(5000);

            //when
            LottoPurchaseResponse response = lottoOrderService.purchase(request);

            //then
            assertThat(response.lottoCount()).isEqualTo(5);
            assertThat(response.lottos()).hasSize(5);
        }

        @Test
        @DisplayName("구입한 로또가 저장소에 저장된다")
        void saveLottosToRepository() {
            //given
            LottoPurchaseRequest request = LottoPurchaseRequest.from(3000);

            //when
            lottoOrderService.purchase(request);

            //then
            List<Lotto> savedLottos = lottoRepository.findAll();
            assertThat(savedLottos).hasSize(3);
        }

        @Test
        @DisplayName("여러 번 구입하면 누적되어 저장된다")
        void accumulatePurchases() {
            //given
            LottoPurchaseRequest firstRequest = LottoPurchaseRequest.from(2000);
            LottoPurchaseRequest secondRequest = LottoPurchaseRequest.from(3000);

            //when
            lottoOrderService.purchase(firstRequest);
            lottoOrderService.purchase(secondRequest);

            //then
            List<Lotto> savedLottos = lottoRepository.findAll();
            assertThat(savedLottos).hasSize(5);
        }
    }

    @Nested
    @DisplayName("generateLottoNumbers 메서드는")
    class GenerateLottoNumbersMethod {

        @Test
        @DisplayName("6개의 로또 번호를 생성한다")
        void generateSixNumbers() {
            //given
            //when
            List<Integer> numbers = lottoOrderService.generateLottoNumbers();

            //then
            assertThat(numbers).hasSize(6);
        }

        @Test
        @DisplayName("1부터 45 사이의 숫자로 로또 번호를 생성한다")
        void generateNumbersInRange() {
            //given
            //when
            List<Integer> numbers = lottoOrderService.generateLottoNumbers();

            //then
            assertThat(numbers).allMatch(number -> number >= 1 && number <= 45);
        }

        @Test
        @DisplayName("중복되지 않는 번호를 생성한다")
        void generateUniqueNumbers() {
            //given
            //when
            List<Integer> numbers = lottoOrderService.generateLottoNumbers();

            //then
            assertThat(numbers).doesNotHaveDuplicates();
        }

        @Test
        @DisplayName("정렬된 번호를 생성한다")
        void generateSortedNumbers() {
            //given
            //when
            List<Integer> numbers = lottoOrderService.generateLottoNumbers();

            //then
            assertThat(numbers).isSorted();
        }
    }
}
