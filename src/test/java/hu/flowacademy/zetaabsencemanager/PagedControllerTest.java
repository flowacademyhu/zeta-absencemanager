package hu.flowacademy.zetaabsencemanager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hu.flowacademy.zetaabsencemanager.controllers.AdminAbsencesController;
import hu.flowacademy.zetaabsencemanager.service.AdminAbsenceService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(controllers = AdminAbsencesController.class)
@AutoConfigureMockMvc
public class PagedControllerTest {

  @MockBean
  private AdminAbsenceService adminAbsenceService;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    AdminAbsencesController instance = new AdminAbsencesController();

    mockMvc = MockMvcBuilders.standaloneSetup(instance).build();
  }


  @Test
  public void evaluatesPageableParameter() throws Exception {
    mockMvc.perform(get("/admin/absence/page")
        .param("page", "5")
        .param("size", "10")
        .param("sort", "id,desc")
        .param("sort", "name,asc"))
        .andExpect(status().isOk());

    ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
    Mockito.verify(adminAbsenceService).findAllPage(pageableCaptor.capture());
    PageRequest pageable = (PageRequest) pageableCaptor.getValue();

    PageableAssert.assertThat(pageable).hasPageNumber(5);
    PageableAssert.assertThat(pageable).hasPageSize(10);
    PageableAssert.assertThat(pageable).hasSort("name", Direction.ASC);
    PageableAssert.assertThat(pageable).hasSort("id", Direction.DESC);

  }
}
