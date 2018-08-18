import helpers.TestUtil;
import hu.klenium.tetris.util.PeriodicTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeriodicTaskSlowTest {
    private PeriodicTask clock = null;
    private int foo = 1;
    @BeforeEach void setUp() {
        clock = new PeriodicTask(() -> ++foo, 100);
    }
    @Test void nonStarted() {
        TestUtil.runLater(150, () -> assertEquals(foo, 1));
    }
    @Test void multiplePeriods() {
        TestUtil.runLater(50, () -> assertEquals(foo, 1));
        TestUtil.runLater(150, () -> assertEquals(foo, 2));
        TestUtil.runLater(250, () -> assertEquals(foo, 3));
    }
    @Test void stoped() {
        TestUtil.runLater(50, () -> {
            assertEquals(foo, 1);
            clock.stop();
        });
        TestUtil.runLater(150, () -> assertEquals(foo, 1));
        TestUtil.runLater(250, () -> assertEquals(foo, 1));
    }
    @Test void resetPeriodTime() {
        TestUtil.runLater(50, () -> {
            assertEquals(foo, 1);
            clock.reset();
        });
        TestUtil.runLater(125, () -> assertEquals(foo, 1));
        TestUtil.runLater(225, () -> assertEquals(foo, 2));
    }
}