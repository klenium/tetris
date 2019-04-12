import helpers.TestUtil;
import hu.klenium.tetris.util.PeriodicTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeriodicTaskSlowTest {
    private static final int PERIOD = 500;
    private static final int OFFSET = PERIOD / 2;
    private PeriodicTask clock = null;
    private int foo = 1;
    @BeforeEach void setUp() {
        clock = new PeriodicTask(() -> ++foo, PERIOD);
    }

    @Test void nonStarted() {
        TestUtil.runLater(OFFSET + PERIOD, () -> assertEquals(1, foo));
    }
    @Test void multiplePeriods() {
        clock.start();
        TestUtil.runLater(OFFSET, () -> assertEquals(1, foo));
        TestUtil.runLater(PERIOD, () -> assertEquals(2, foo));
        clock.start();
        TestUtil.runLater(PERIOD, () -> assertEquals(3, foo));
        TestUtil.runLater(PERIOD, () -> assertEquals(4, foo));
    }
    @Test void stoped() {
        clock.start();
        TestUtil.runLater(OFFSET, () -> {
            assertEquals(1, foo);
            clock.stop();
        });
        TestUtil.runLater(PERIOD, () -> assertEquals(1, foo));
        TestUtil.runLater(PERIOD, () -> assertEquals(1, foo));
        TestUtil.runLater(OFFSET, () -> clock.stop());
        TestUtil.runLater(PERIOD, () -> assertEquals(1, foo));
    }
    @Test void resetPeriodTime() {
        clock.start();
        TestUtil.runLater(OFFSET, () -> {
            assertEquals(1, foo);
            clock.reset();
        });
        TestUtil.runLater(OFFSET, () -> assertEquals(1, foo));
        TestUtil.runLater(PERIOD, () -> assertEquals(2, foo));
    }
}