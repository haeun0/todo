package com.green.todoapp;

import com.green.todoapp.model.TodoEntity;
import com.green.todoapp.model.TodoVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoMapperTest {

    @Autowired
    private TodoMapper mapper;

    @Test
    void insTodo() {
        //given
        TodoEntity entity = new TodoEntity();
        entity.setCtnt("테스트");

        int result = mapper.insTodo(entity);
        System.out.println(entity.getItodo());

        assertEquals(5,entity.getItodo());
        assertEquals(1,result);
    }

    @Test
    void selTodo() {
        List<TodoVo> list = mapper.selTodo();

        assertEquals(4, list.size());
        TodoVo vo = list.get(0);
        assertEquals(1,vo.getItodo());
        assertEquals("첫번째",vo.getCtnt());


    }

    @Test
    void updfinish() {
        TodoEntity entity = new TodoEntity();
        entity.setItodo(2);

        int result = mapper.updfinish(entity);

        assertEquals(1, result); //없는 pk값일경우 여기서 에러?
    }

    @Test
    @DisplayName("TodoMapper - Todo 삭제")
    void delTodo() {
        int expectedResult = 1;
        TodoEntity entity = new TodoEntity();
        entity.setItodo(3);

        int actualResultresult = mapper.delTodo(entity);
        assertEquals(expectedResult,actualResultresult);
    }
}