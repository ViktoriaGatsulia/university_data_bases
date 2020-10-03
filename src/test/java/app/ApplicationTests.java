package app;

import app.entity.Student;
import app.entity.StudentGroup;
import app.service.StudentGroupService;
import app.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Класс для тестирований Spring приложения
 *
 * @author ViktoriaGatsulia
 * @version 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
    /**
     * Способ тестирования конечных точек:
     * имитационная среда класс MockMvc
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Аннотация @MockBean, описывает имитационный объект Mockito
     * для компонента studentGroupService в Applicationcontext
     */
    @MockBean
    private StudentGroupService studentGroupService;

    /**
     * Аннотация @MockBean, описывает имитационный объект Mockito
     * для компонента studentService в Applicationcontext
     */
    @MockBean
    private StudentService studentService;

    /**
     * Тест для studentService для метода findById
     */
    @Test
    public void testFindStudentById() {
        given(this.studentService.findById(1L)).willReturn(java.util.Optional.of(new Student("Viktoria")));
        assertThat(this.studentService.findById(1L).get().getFio()).isEqualTo("Viktoria");
    }

    /**
     * Тест для studentGroupService для метода findById
     */
    @Test
    public void testFindStudentGroupServiceById() {
        given(this.studentGroupService.findById(1L)).willReturn(java.util.Optional.of(new StudentGroup("10-11")));
        assertThat(this.studentGroupService.findById(1L).get().getNum()).isEqualTo("10-11");
    }


    /**
     * Тест для MainController
     * Проверка корректности сохранения нового студента (PostMapping)
     *
     * @throws Exception - отлавливаются любые исключения
     */
    @Test
    public void testViewSaveStudent() throws Exception {
        this.mvc
                .perform(post("/saveStudent").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"fio\": \"Viktoria\", \n" +
                        "    \"master_group\": \"3\"\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    /**
     * Тест для MainController
     * Проверка корректности поиска группы по id (GetMapping)
     *
     * @throws Exception - отлавливаются любые исключения
     */
    @Test
    public void testViewStudentFindById() throws Exception {
        StudentGroup studentGroup = new StudentGroup();

        given(this.studentGroupService.findById(1L))
                .willReturn(java.util.Optional.of(studentGroup));
        this.mvc.perform(get("/getGroupById/id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Тест для MainController
     * Проверка корректности поиска студентов из несуществующей группы (GetMapping)
     *
     * @throws Exception - отлавливаются любые исключения
     */
    @Test
    public void testViewStudentFindByIdNotFound() throws Exception {
        this.mvc.perform(get("/findStudentByGroup/id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

// .andExpect(status().isOk()) - код возврата был 200?
