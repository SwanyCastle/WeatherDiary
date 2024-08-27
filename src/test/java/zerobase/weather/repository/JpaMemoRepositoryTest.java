package zerobase.weather.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.Memo;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JpaMemoRepositoryTest {

    @Autowired
    JpaMemoRepository jpaMemoRepository;

    @Test
    void insertMemo() {
        // given
        Memo memo = new Memo(10, "this is jpa memo");

        // when
        jpaMemoRepository.save(memo);

        // then
        List<Memo> memoList = jpaMemoRepository.findAll();
        assertFalse(memoList.isEmpty());
    }

    @Test
    void findByidMemo() {
        // given
        Memo memo = new Memo(11, "jpa");

        // when
        Memo savedMemo = jpaMemoRepository.save(memo);

        // then
        Optional<Memo> memo1 = jpaMemoRepository.findById(savedMemo.getId());
        assertEquals(memo1.get().getText(), "jpa");
    }
}