package com.example.calendar.Controller;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CalendarController {

    //Jdbc 템플릿 생성
    private final JdbcTemplate jdbcTemplate;

    public CalendarController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //일정 생성
//    @PostMapping("/calendae")

    //일정 조회
//    @GetMapping("/calendae/{id}")

    //일정 목록 조회
//    @GetMapping("/calendae")

    //일정 수정
//    @PutMapping("/calendae/{id}")

    //일정 삭제
//    @PutMapping("/calendae/{id}")

}
