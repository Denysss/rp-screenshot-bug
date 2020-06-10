package demo;

import com.epam.reportportal.listeners.ItemStatus;
import com.epam.reportportal.message.ReportPortalMessage;
import com.epam.reportportal.service.Launch;
import com.epam.reportportal.service.ReportPortal;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
public class RpScreenshotBugTest {

    @Test
    public void myTest() {
        log.info("myTest log");
        assertThat(true).isEqualTo(true);
    }

    @Test
    public void screenshotTest() {
        File file = new File(getClass().getClassLoader().getResource("bagel.png").getFile());

        // 1
        try {
            ReportPortalMessage message = new ReportPortalMessage(file, "attached screenshot via logging ReportPortalMessage");
            log.info(message);
        } catch (IOException e) {
            log.warn("cannot send screenshot to ReportPortal as a ReportPortalMessage");
        }

        // 2
        boolean emitLogResult = ReportPortal.emitLog("attached screenshot - ReportPortal.emitLog", "INFO", Calendar.getInstance().getTime(), file);
        log.info("screenshot via ReportPortal.emitLog: {}", emitLogResult);

        // 3
        boolean result = ReportPortal.emitLaunchLog("attached screenshot - ReportPortal.emitLaunchLog", "INFO", Calendar.getInstance().getTime(), file);
        log.info("screenshot via ReportPortal.emitLaunchLog: {}", result);

        // 4
        Launch.currentLaunch().getStepReporter().sendStep(ItemStatus.FAILED, "attached screenshot - sendStep", file);
    }

    @Test
    public void failedTest() {
        log.error("failedTest log");
        assertThat(true).isEqualTo(false);
    }
}
