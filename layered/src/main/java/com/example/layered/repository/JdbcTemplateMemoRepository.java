package com.example.layered.repository;

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateMemoRepository implements MemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 메모 등록
    @Override
    public MemoResponseDto saveMemo(Memo memo) {

        // Insert Query를 직접 작성하지 않아도됨.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        // jdbcInsert.withTableName("memo") => memo Table에 Insert 하겠다.
        // usingGeneratedKeyColumns("id") => 이 테이블의 key 값은 id이다.
        jdbcInsert.withTableName("memo").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", memo.getTitle());
        parameters.put("contents", memo.getContents());

        // 저장 후 생성된 Key 값 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new MemoResponseDto(key.longValue(), memo.getTitle(), memo.getContents());
    }

    // 메모 목록 조회
    @Override
    public List<MemoResponseDto> findAllMemos() {
        return jdbcTemplate.query("select * from memo", memoRowMapper());
    }

    // 메모 단건 조회
    @Override
    public Optional<Memo> findMemoById(Long id) {
        // 마지막 id 값이 ?의 id 값과 치환된다.
        List<Memo> result = jdbcTemplate.query("select * from memo where id = ?", memoRowMapperV2(), id);
        return result.stream().findAny(); // findAny 사용 시 Optional 형태의 memo 반환
    }

    // id 검증
    @Override
    public Memo findMemoByIdOrElseThrow(Long id) {

        List<Memo> result = jdbcTemplate.query("select * from memo where id = ?", memoRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    // 메모 전체 수정
    @Override
    public int updateMemo(Long id, String title, String contents) {
        return jdbcTemplate.update("update memo set  title = ?, contents = ? where id = ?", title, contents, id);
    }

    // 메모 제목 수정
    @Override
    public int updateTitle(Long id, String title) {
        return jdbcTemplate.update("update memo set title = ? where id = ?", title, id);
    }

    // 메모 삭제
    @Override
    public int deleteMemo(Long id) {

        return jdbcTemplate.update("delete from memo where id = ?", id);
    }

    private RowMapper<MemoResponseDto> memoRowMapper() {
        return new RowMapper<MemoResponseDto>() {
            @Override
            public MemoResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new MemoResponseDto(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }
        };
    }

    private RowMapper<Memo> memoRowMapperV2() {
        return new RowMapper<Memo>() {
            @Override
            public Memo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Memo(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("contents")
                );
            }
        };
    }
}
