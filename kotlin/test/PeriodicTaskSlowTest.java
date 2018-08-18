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
        TestUtil.runLater(OFFSET + PERIOD, () -> assertEquals(foo, 1));
    }
    @Test void multiplePeriods() {
        TestUtil.runLater(OFFSET, () -> assertEquals(foo, 1));
        TestUtil.runLater(PERIOD, () -> assertEquals(foo, 2));
        TestUtil.runLater(PERIOD, () -> assertEquals(foo, 3));
    }
    @Test void stoped() {
        TestUtil.runLater(OFFSET, () -> {
            assertEquals(foo, 1);
            clock.stop();
        });
        TestUtil.runLater(PERIOD, () -> assertEquals(foo, 1));
        TestUtil.runLater(PERIOD, () -> assertEquals(foo, 1));
    }
    @Test void resetPeriodTime() {
        TestUtil.runLater(OFFSET, () -> {
            assertEquals(foo, 1);
            clock.reset();
        });
        TestUtil.runLater(OFFSET, () -> assertEquals(foo, 1));
        TestUtil.runLater(PERIOD, () -> assertEquals(foo, 2));
    }
}