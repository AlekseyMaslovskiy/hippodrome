import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

public class HorseTest {
    Horse horse;

    @Test
    public void constructorFirstArgNotNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> horse = new Horse(null, 1));
    }

    @Test
    public void constructorFirstArgExceptionStringTest() {
        try {
            horse = new Horse(null, 1);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Name cannot be null.", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    public void constructorFirstArgTest(String str) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> horse = new Horse(str, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   "})
    public void constructorFirstArgExceptionStringTest(String str) {
        try {
            horse = new Horse(str, 1);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void constructorSecondArgTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> horse = new Horse("null", -2));
    }

    @Test
    public void constructorSecondArgExceptionStringTest() {
        try {
            horse = new Horse("null", -2);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void constructorThirdArgTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> horse = new Horse("null", 2, -3));
    }

    @Test
    public void constructorThirdArgExceptionStringTest() {
        try {
            horse = new Horse("null", 2, -3);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getNameTest() {
        horse = new Horse("testHorse", 2);
        Assertions.assertEquals("testHorse", horse.getName());
    }

    @Test
    public void getSpeedTest() {
        horse = new Horse("testHorse", 2);
        Assertions.assertEquals(2, horse.getSpeed());
    }

    @Test
    public void getDistanceTest() {
        horse = new Horse("testHorse", 2, 3);
        Assertions.assertEquals(3, horse.getDistance());
        horse = new Horse("testHorse", 2);
        Assertions.assertEquals(0, horse.getDistance());
    }

    @Test
    public void moveTest() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("null", 1, 1).move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 999.999, 0.0})
    public void moveDistanceTest(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            horse = new Horse("null", 1, 1);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            Assertions.assertEquals(1 + 1 * random, horse.getDistance());
        }
    }
}
