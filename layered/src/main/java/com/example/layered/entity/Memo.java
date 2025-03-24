package com.example.layered.entity;

import com.example.layered.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Memo {

    @Setter // class에 @Setter를 올릴 시 전체 필드를 사용하기 때문에 사용 필드에만 적용해야함.
    private Long id;
    private String title;
    private String contents;

    public Memo(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    // dto 자체로 받는게 아닌 title, contents로 나눠서 사용 시 재사용성 용이
    // 타입으로 만들 시 어디서든 이 타입으로 사용하기 좋음
    public void update(String title, String contents) {
        this.title = title; // 메개 변수로 가져온 title
        this.contents = contents; // 메개 변수로 가져온 contents
    }

    public void updateTitle(String title) {
        this.title = title; // 메개 변수로 가져온 title
    }
}
