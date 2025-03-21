package com.example.memo2.controller;

import com.example.memo2.dto.MemoRequestDto;
import com.example.memo2.dto.MemoResponseDto;
import com.example.memo2.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>(); // memoList라는 Map 형태로 초기화
    
    // 메모 등록
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto dto) {
        
        // 식별자가 1씩 증가하도록 만들기
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;
        // Collections.max => 안의 값중 최대값을 꺼내기 / memoList.keySet() => memoList의 모든 key 값 꺼내기

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());
        //첫번째 인자로 Id 값을 전달해주고, dto 안에 있는 title과 content를 꺼내준다.
        // memo 타입의 변수로 받기

        //Inmemory DB에 Memo 메모
        memoList.put(memoId, memo);
        //key = memoId, value = memo(저장될 객체 형태)

        return new MemoResponseDto(memo); // 저장된 객체를 response 형태로 바꿔서 응답
    }

    //메모 단건 조회
    @GetMapping("/{id}")
    public MemoResponseDto findMemoById(@PathVariable Long id) {

        Memo memo = memoList.get(id); //get으로 key 값 전달

        return new MemoResponseDto(memo); //responsedto 형태로 반환 하는 것

    }
    
    //메모 단건 수정
    @PutMapping("/{id}")
    public MemoResponseDto updateMemoById(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ) {
        Memo memo = memoList.get(id);

        memo.update(dto);

        return new MemoResponseDto(memo);
    }
    
    // 메모 단건 삭제
    @DeleteMapping("/{id}")
    public void deleteMemo(@PathVariable Long id) {

        memoList.remove(id);
    }
}
