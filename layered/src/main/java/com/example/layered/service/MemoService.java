package com.example.layered.service;

import com.example.layered.dto.MemoRequestDto;
import com.example.layered.dto.MemoResponseDto;

import java.util.List;

public interface MemoService {

    // 메모 단건 등록
    MemoResponseDto saveMemo(MemoRequestDto dto);

    // 메모 목록 조회
    List<MemoResponseDto> findAllMemos();

    // 메모 단건 조회
    MemoResponseDto findMemoById(Long id);

    // 메모 전체 수정
    MemoResponseDto updateMemo(Long id, String title, String contents);

    // 메모 제목 수정
    MemoResponseDto updateTitle(Long id, String title, String contents);

    // 메모 삭제
    void deleteMemo(Long id);
}
