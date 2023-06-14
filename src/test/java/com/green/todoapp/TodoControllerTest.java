package com.green.todoapp;

import com.google.gson.Gson;
import com.green.todoapp.model.TodoDelDto;
import com.green.todoapp.model.TodoFinishDto;
import com.green.todoapp.model.TodoInsDto;
import com.green.todoapp.model.TodoVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mvc; // 테스트단에서 요청을 보내줌

    @MockBean
    private TodoService service;
    

    @Test
    @DisplayName("TODO - 등록")
    void postTodo() throws Exception {
        //given 환경설정 단계 - when 실제로 데이터를 가져오는 단계 - then 실제로 가져왔는지 검증하는 단계

        //given - 테스트 셋팅
        given(service.insTodo(any(TodoInsDto.class))).willReturn(3);  // BDDMockito.given의 줄임 / 가짜 Totoservice에 일을 줌

        //when - 실제 실행
        TodoInsDto dto = new TodoInsDto();
        dto.setCtnt("빨래개기");
        Gson gson = new Gson();
        String json = gson.toJson(dto);
//        String json = "{ \"ctnt\": \"빨래 개기\" }";
        ResultActions ra = mvc.perform(post("/api/todo") // controller에게 통신요청
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON));

        //then - 검증
        ra.andExpect(status().isOk())
           .andExpect(content().string("3"))
           .andDo(print());
        verify(service).insTodo(any()); // 여기에 쓰여있는부분이 실행되었는지 확인
    }

    @Test
    @DisplayName("TODO - 리스트")
    void getTodo() throws Exception {
        //given - when - then
        List<TodoVo> mockList = new ArrayList<>();
        mockList.add(new TodoVo(1,"테스트1","2023",null,0, null));
        mockList.add(new TodoVo(2,"테스트2","2024","abc.jpg",1, "2023-05-11"));

        //given
        given(service.selTodo()).willReturn(mockList);

        //when
        ResultActions ra = mvc.perform(get("/api/todo"));

        //then
        ra.andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(mockList.size())))
        .andExpect(jsonPath("$[*].itodo").exists())
        .andExpect(jsonPath("$[0].itodo").value(1))
        .andExpect(jsonPath("$[0].ctnt").value("테스트1"))
        .andDo(print());

        verify(service).selTodo();
    }

    @Test
    @DisplayName("TODO - 완료처리 토글")
    void patchTodo() throws Exception {
        given(service.updfinish(any())).willReturn(1);

        TodoFinishDto dto = new TodoFinishDto();
        dto.setItodo(1);
        Gson gson = new Gson();
        String json = gson.toJson(dto);

        ResultActions ra = mvc.perform(patch("/api/todo")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON));

        ra.andExpect(status().isOk())
        .andExpect(content().string("1"))
        .andDo(print());

        verify(service).updfinish(any());
    }

    @Test
    @DisplayName("TODO - 삭제처리")
    void patchDelTodo() throws Exception {
        int itodo = 10;

        given(service.delTodo(anyInt())).willReturn(itodo);

        ResultActions ra = mvc.perform(delete("/api/todo")
                                        .param("itodo",String.valueOf(itodo)));

        ra.andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(itodo)))
                .andDo(print());

        verify(service).delTodo(anyInt());


    }

}