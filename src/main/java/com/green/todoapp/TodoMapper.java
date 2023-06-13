package com.green.todoapp;

import com.green.todoapp.model.TodoEntity;
import com.green.todoapp.model.TodoInsDto;
import com.green.todoapp.model.TodoSelDto;
import com.green.todoapp.model.TodoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
    int insTodo(TodoEntity entity);
    List<TodoVo> selTodo();
    int finTodo(TodoEntity entity);
}
