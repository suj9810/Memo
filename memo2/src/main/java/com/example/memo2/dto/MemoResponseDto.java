package com.example.memo2.dto;

import com.example.memo2.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto { // 응답 받아야 하는 정보

    private Long id;
    private String title;
    private String contents;

    public MemoResponseDto(Memo memo) { // memo 객체 그대로 받기
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.contents = memo.getContents();
    }
}
