import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class TimetableTest {

    @Test
    public void testEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<Timetable.CounterTraining> result = timetable.getCountByCoaches();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testOneCoachOneTraining() {
        Timetable timetable = new Timetable();
        Coach coach = new Coach("Иван", "Иванов");

        timetable.addNewTrainingSession(
                new TrainingSession(
                        new Group("Йога", Age.ADULT, 60),
                        coach,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(10, 0)
                )
        );

        List<Timetable.CounterTraining> result = timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(coach, result.get(0).getCoach());
        Assertions.assertEquals(1, result.get(0).getCount());
    }

    @Test
    public void testTwoCoachesDifferentCounts() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иван", "Иванов");
        Coach coach2 = new Coach("Петр", "Петров");
        Group group = new Group("Йога", Age.ADULT, 60);

        // Тренер 1: 3 тренировки
        timetable.addNewTrainingSession(
                new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(
                new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0))
        );
        timetable.addNewTrainingSession(
                new TrainingSession(group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(10, 0))
        );

        // Тренер 2: 1 тренировка
        timetable.addNewTrainingSession(
                new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(10, 0))
        );

        List<Timetable.CounterTraining> result = timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        // Проверка сортировки
        Assertions.assertEquals(coach1, result.get(0).getCoach());
        Assertions.assertEquals(3, result.get(0).getCount());

        Assertions.assertEquals(coach2, result.get(1).getCoach());
        Assertions.assertEquals(1, result.get(1).getCount());
    }
}