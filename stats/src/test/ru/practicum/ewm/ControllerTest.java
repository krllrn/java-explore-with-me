package ru.practicum.ewm;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.ewm.controller.StatisticController;
import ru.practicum.ewm.controller.StatisticService;
import ru.practicum.ewm.model.ViewStats;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(StatisticController.class)
public class ControllerTest {
    @MockBean
    private StatisticService statisticService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    private final ViewStats viewStats = new ViewStats("Get app", "/test/uri", 10L);

    @Test
    public void testViewStats() {
        List<ViewStats> viewStatsList = new ArrayList<>(List.of(viewStats));
        Mockito.when(statisticService.viewStats(any(String.class), any(String.class), any(String.class),
                any(String.class))).thenReturn(viewStatsList);

        List<ViewStats> founded = statisticService.viewStats("test", "test", "test", "test");

        Assertions.assertEquals(viewStatsList.size(), founded.size());
        Assertions.assertEquals(viewStatsList.get(0).getApp(), founded.get(0).getApp());
        Assertions.assertEquals(viewStatsList.get(0).getUri(), founded.get(0).getUri());
    }
}
