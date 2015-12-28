package IT;


import org.bahmni.module.eventlogservice.EventLogService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EventLogService.class)
public abstract class BaseIntegrationTest {
}
