package be.brussel.schoolgrappling.controller;

import be.brussel.schoolgrappling.model.SchoolEntity;
import be.brussel.schoolgrappling.service.ContactService;
import be.brussel.schoolgrappling.service.SchoolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(MockitoExtension.class)
class SchoolControllerTest {

    @Mock
    private SchoolService schoolService;
    @Mock
    private ContactService contactService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        SchoolController schoolController = new SchoolController(schoolService, contactService);
        mockMvc = MockMvcBuilders.standaloneSetup(schoolController).build();
    }

    @Test
    void initCreatSchoolForm() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(get("/schools/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("schools/createOrUpdateSchoolsForm"))
                .andExpect(model().attributeExists("school"));
    }

    @Test
    void processCreationForm() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(post("/schools/new")
               .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param()
                 )
                .andExpect(status().isOk())
                .andExpect(view().name("schools/createOrUpdateSchoolsForm"))
                .andExpect(model().attributeExists("school"));
    }

    @Test
    void getSchoolById() throws Exception {
        //given
        SchoolEntity school = SchoolEntity.builder().build();
        String id = "1";

        given(schoolService.getSchoolById(anyLong())).willReturn(school);

        //when
        //then
        mockMvc.perform(get("/schools/findSchool/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("schools/findSchoolById"))
                .andExpect(model().attributeExists("school"));
    }

    @Test
    void getSchools() throws Exception{
        //given
        String keyword = "keyword";


        //when
        //then
        mockMvc.perform(get("/schools/listSchools")
                .param("keyword", keyword)
                )
                .andExpect(status().isOk())
                .andExpect(view().name("schools/listOfSchools"))
                .andExpect(model().attributeExists("schools"))
                .andExpect(model().attributeExists("keyword"));
    }

    @Test
    void updateSchool() throws Exception{
        //given
        SchoolEntity school = SchoolEntity.builder().build();
        String id = "1";

        given(schoolService.getSchoolById(anyLong())).willReturn(school);

        //when
        //then
        mockMvc.perform(get("/schools/update/{id}", id))
                .andExpect(status().isOk())
                .andExpect(view().name("schools/createOrUpdateSchoolsForm"))
                .andExpect(model().attributeExists("school"));
    }


    @Test
    void deleteSchool() throws Exception{
        //given
        String id = "1";

        //when
        //then
        mockMvc.perform(get("/schools/delete/{id}", id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/schools/listSchools"));

        verify(schoolService, times(1)).deleteById(Long.parseLong(id));
    }
}