package ru.practicum.ewm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.ewm.general.admin.categories.AdmCategoriesService;

@SpringBootTest
public class ExploreWithMeServerTest {

    private final AdmCategoriesService admCategoriesService;

    @Autowired
    public ExploreWithMeServerTest(AdmCategoriesService admCategoriesService) {
        this.admCategoriesService = admCategoriesService;
    }

    @Test
    public void main() {
        ExploreWithMeServer.main(new String[] {});
    }
}
