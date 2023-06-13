package com.green.todoapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoVo {
    private int itodo;
    private String ctnt;
    private String createdAt;
    private String pic;
    private int finishYn;
    private String finishedAt;
}
