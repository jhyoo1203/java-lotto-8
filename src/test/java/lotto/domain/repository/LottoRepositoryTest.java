package lotto.domain.repository;

import lotto.domain.Lotto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("LottoRepository 테스트")
class LottoRepositoryTest {

    private final LottoRepository lottoRepository = new LottoRepository();

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
    @DisplayName("saveAll 메서드는")
    class SaveAllMethod {

        @Test
        @DisplayName("로또 리스트를 저장한다")
        void saveLottos() {
            //given
            List<Lotto> lottos = List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                    new Lotto(List.of(7, 8, 9, 10, 11, 12))
            );

            //when
            lottoRepository.saveAll(lottos);

            //then
            List<Lotto> savedLottos = lottoRepository.findAll();
            assertThat(savedLottos).hasSize(2);
        }

        @Test
        @DisplayName("빈 리스트를 저장하려고 하면 예외가 발생한다")
        void throwExceptionWhenSaveEmptyList() {
            //given
            List<Lotto> lottos = List.of();

            //when
            //then
            assertThatThrownBy(() -> lottoRepository.saveAll(lottos))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("저장할 로또가 없습니다");
        }

        @Test
        @DisplayName("null을 저장하려고 하면 예외가 발생한다")
        void throwExceptionWhenSaveNull() {
            //given
            List<Lotto> lottos = null;

            //when
            //then
            assertThatThrownBy(() -> lottoRepository.saveAll(lottos))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("저장할 로또가 없습니다");
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class FindAllMethod {

        @Test
        @DisplayName("저장된 모든 로또를 순서대로 반환한다")
        void returnAllLottosInOrder() {
            //given
            List<Lotto> lottos = List.of(
                    new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                    new Lotto(List.of(7, 8, 9, 10, 11, 12)),
                    new Lotto(List.of(13, 14, 15, 16, 17, 18))
            );
            lottoRepository.saveAll(lottos);

            //when
            List<Lotto> foundLottos = lottoRepository.findAll();

            //then
            assertThat(foundLottos).hasSize(3);
            assertThat(foundLottos).containsExactlyElementsOf(lottos);
        }

        @Test
        @DisplayName("저장된 로또가 없으면 예외가 발생한다")
        void throwExceptionWhenNoLottos() {
            //given
            //when
            //then
            assertThatThrownBy(lottoRepository::findAll)
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining("구매한 로또가 없습니다");
        }
    }
}
