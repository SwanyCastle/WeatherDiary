package zerobase.weather.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.Memo;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcMemoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMemoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Memo save(Memo memo) {
        String sql = "insert into memo values(?, ?)";
        jdbcTemplate.update(sql, memo.getId(), memo.getText());
        return memo;
    }

    public List<Memo> findAll() {
        String sql = "select * from memo";
        return jdbcTemplate.query(sql, memoRowMapper());
    }

    public Optional<Memo> findById(Integer id) {
        String sql = "select * from memo where id = ?";
        return jdbcTemplate.query(sql,memoRowMapper(), id).stream().findFirst();
    }

    private RowMapper<Memo> memoRowMapper() {
        // JDBC를 통해서 DB 에서 Data 를 가져오면 그 값은 Result Set 이라는 형식의 데이터이다.
        // Result Set 의 형태는 다음과 같다 {id = 1, test = 'this is memo~'}
        // 이러한 Result Set 을 Memo 클래스로 맵핑을 해줘야하는데 이것을 해주는 얘가 RowMapper
        return (rs, rowNum) -> new Memo(
                rs.getInt("id"),
                rs.getString("text")
        );
    }
}
