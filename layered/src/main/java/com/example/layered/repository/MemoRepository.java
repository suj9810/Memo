package com.example.layered.repository;

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;

import java.util.List;

import java.util.Optional;

public interface MemoRepository {

    // 메모 단건 등록
    MemoResponseDto saveMemo(Memo memo);

    // 메모 목록 조회
    List<MemoResponseDto> findAllMemos();

    // 메모 단건 조회
    Optional<Memo> findMemoById(Long id);
    
    // id 검증
    Memo findMemoByIdOrElseThrow(Long id);

    // 메모 전체 수정
    int updateMemo(Long id, String title, String contents);

    // 메모 제목 수정
    int updateTitle(Long id, String title);

    // 메모 삭제
    int deleteMemo(Long id);
}
