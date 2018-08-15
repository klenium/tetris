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
    private void assertLater(int delay, Runnable task) {
        try {
            Thread.sleep(delay);
        }
        catch (InterruptedException e) {
            task.run();
        }
    }
    @Test void nonStarted() {
        assertLater(150, () -> assertEquals(foo, 1));
    }
    @Test void multiplePeriods() {
        assertLater(50, () -> assertEquals(foo, 1));
        assertLater(150, () -> assertEquals(foo, 2));
        assertLater(250, () -> assertEquals(foo, 3));
    }
    @Test void stoped() {
        assertLater(50, () -> {
            assertEquals(foo, 1);
            clock.stop();
        });
        assertLater(150, () -> assertEquals(foo, 1));
        assertLater(250, () -> assertEquals(foo, 1));
    }
    @Test void resetPeriodTime() {
        assertLater(50, () -> {
            assertEquals(foo, 1);
            clock.reset();
        });
        assertLater(125, () -> assertEquals(foo, 1));
        assertLater(225, () -> assertEquals(foo, 2));
    }
}