package com.example.layered.repository;

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Annotation @Repository는 @Component와 같다, Spring Bean으로 등록한다는 뜻.
 * Spring Bean으로 등록되면 다른 클래스에서 주입하여 사용할 수 있다.
 * 명시적으로 Repository Layer 라는것을 나타낸다.
 * DB와 상호작용하여 데이터를 CRUD하는 작업을 수행한다.
 */
@Repository
public class MemoRepositoryImpl implements MemoRepository {

    // 데이터베이스(Repository)
    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모 단건 등록
    @Override
    public Memo saveMemo(Memo memo) {

        // MemoId 식별자 계산(Repository)
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;
        memo.setId(memoId);

        memoList.put(memoId, memo);

        return memo;
    }

    // 메모 목록 조회
    @Override
    public List<MemoResponseDto> findAllMemos() {

        // init List
        List<MemoResponseDto> allMemos = new ArrayList<>();

        // HashMap<Memo> -> List<MemoResponseDto>
        for (Memo memo : memoList.values()) {
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            allMemos.add(responseDto);
        }

        return allMemos;
    }

    // 메모 단건 조회
    @Override
    public Memo findMemoById(Long id) {

        return memoList.get(id);
    }

    // 메모 삭제
    @Override
    public void deleteMemo(Long id) {
        memoList.remove(id);
    }

}
