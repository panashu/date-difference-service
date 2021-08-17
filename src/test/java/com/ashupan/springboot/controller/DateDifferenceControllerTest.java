package com.ashupan.springboot.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.ashupan.springboot.model.DateDifference;
import com.ashupan.springboot.service.DateDifferenceService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DateDifferenceController.class)
public class DateDifferenceControllerTest {

  @MockBean
  private DateDifferenceService dateDifferenceService;

  @Autowired
  private MockMvc mockMvc;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void contextLoads() throws Exception {
    assertThat(dateDifferenceService).isNotNull();
  }

  @Test
  public void calculateDateDifferenceTest() throws Exception {
    LocalDate inputDate = LocalDate.of(2021, 07, 31);
    LocalDate dateToday = LocalDate.now();
    int duration = dateToday.getDayOfMonth();
    DateDifference dateDifference = new DateDifference();
    dateDifference.setDateDifference(Long.valueOf(duration));
    when(dateDifferenceService.calculateDateDifference(inputDate)).thenReturn(dateDifference);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/datedifference")
                .param("inputDate", String.valueOf(inputDate))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.dateDifference", Is.is(duration)));
  }
}
