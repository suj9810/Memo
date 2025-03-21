package com.example.memo2.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto { // 클라이언트에서 받는 정보

    private String title;
    private String contents;
}
