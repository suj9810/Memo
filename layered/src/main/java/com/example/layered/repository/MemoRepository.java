package com.example.layered.repository;

import com.example.layered.dto.MemoResponseDto;
import com.example.layered.entity.Memo;

import java.util.List;

public interface MemoRepository {

    // 메모 단건 등록
    Memo saveMemo(Memo memo);

    // 메모 목록 조회
    List<MemoResponseDto> findAllMemos();

    // 메모 단건 조회
    Memo findMemoById(Long id);

    // 메모 삭제
    void deleteMemo(Long id);
}
