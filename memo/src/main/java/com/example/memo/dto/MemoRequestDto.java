package com.example.memo.dto;

import lombok.Getter;

@Getter
public class MemoRequestDto { //클라이언트로 전달 받아야하는 데이터

    private String title;
    private String contents;

}
