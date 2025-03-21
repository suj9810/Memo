package com.example.memo2.entity;


import com.example.memo2.dto.MemoRequestDto;
import com.example.memo2.dto.MemoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 모든 필드의 생성자를 자동으로 생성해주는 기능
public class Memo {

    private Long id;
    private String title;
    private String contents;

    public void update(MemoRequestDto dto) {
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
}
