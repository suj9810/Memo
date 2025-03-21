package com.example.memo2.controller;

import com.example.memo2.dto.MemoRequestDto;
import com.example.memo2.dto.MemoResponseDto;
import com.example.memo2.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>(); // memoList라는 Map 형태로 초기화
    
    // 메모 등록
    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto dto) { // ResponseEntity => 상태 코드를 반환하는 코드
        
        // 식별자가 1씩 증가하도록 만들기 / MemoId 식별자 계산
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1;
        // Collections.max => 안의 값중 최대값을 꺼내기 / memoList.keySet() => memoList의 모든 key 값 꺼내기

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, dto.getTitle(), dto.getContents());
        //첫번째 인자로 Id 값을 전달해주고, dto 안에 있는 title과 content를 꺼내준다.
        // memo 타입의 변수로 받기

        //Inmemory DB에 Memo 메모
        memoList.put(memoId, memo);
        //key = memoId, value = memo(저장될 객체 형태)

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED); // 저장된 객체를 response 형태로 바꿔서 응답
    }

    // 메모 목록 조회
    @GetMapping
    public List<MemoResponseDto> findAllMemos() {

        // init List / 리스트 초기화
        List<MemoResponseDto> responseList = new ArrayList<>(); // List는 인터페이스로 구현체 사용해야함

        //HashMap<Memo> -> List<MemoResponseDto>
        for (Memo memo : memoList.values()) { //memoList.values를 통해 모든 memo를 꺼낸 후 그걸 memo에 담는다.
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            responseList.add(responseDto); //for문 안에서 Memo memo가 responseDto로 변하고, 그 dto가 add된다.
        }

        // Map To List
//        responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        return responseList;
    }

    // 메모 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemoResponseDto> findMemoById(@PathVariable Long id) {

        // 식별자의 Memor가 없다면?
        Memo memo = memoList.get(id); //get으로 key 값 전달
    
        // null값 방지
        if (memo == null) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK); //responsedto 형태로 반환 하는 것

    }
    
    // 메모 단건 수정
    @PutMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateMemoById(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ) {
        Memo memo = memoList.get(id);

        // null값 방지
        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        //필수값 검증
        if (dto.getTitle() == null || dto.getContents() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // memo 수정 / 응답
        memo.update(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }

    // 메모 단건 일부 수정 (제목)
    @PatchMapping("/{id}")
    public ResponseEntity<MemoResponseDto> updateTitle(
            @PathVariable Long id,
            @RequestBody MemoRequestDto dto
    ) {
        Memo memo = memoList.get(id); // 메모 꺼내오기
        
        // null 방지
        if (memo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 필수값 검증
        if (dto.getTitle() == null || dto.getContents() != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        memo.updateTitle(dto);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.OK);
    }
    
    // 메모 단건 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) { // 응답 데이터가 필요 없으므로 void

        //void, Void의 차이점
        //void => 기본 데이터 타입, 반환값 없음 예약어를 뜻함, 직접 객체로 만들 수 없음
        //Void => 객체처럼 다룰 수 있음, 제네릭에서 반환 타입 없을 시 사용, 객체를 생성할 수 없음

        // memoListdp Key 값에 id를 포함하고 있다면
        if (memoList.containsKey(id)) { // containsKey => 특정 키가 Map에 존재하는지 확인
            memoList.remove(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        // 포함하고 있지 않은 경우
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
