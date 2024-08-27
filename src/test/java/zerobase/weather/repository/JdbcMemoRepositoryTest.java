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
public class JdbcMemoRepositoryTest {

    @Autowired
    JdbcMemoRepository jdbcMemoRepository;

    @Test
    void insertMemo() {
        // given
        Memo memo = new Memo(1, "this is new memo~");

        // when
        jdbcMemoRepository.save(memo);

        // then
        Optional<Memo> resultMemo = jdbcMemoRepository.findById(1);
        assertEquals(memo.getText(), resultMemo.get().getText());
    }

    @Test
    void findAllMemo() {
        // given
        List<Memo> memoList = jdbcMemoRepository.findAll();

        //when
        //then
        assertNotNull(memoList);
    }
}