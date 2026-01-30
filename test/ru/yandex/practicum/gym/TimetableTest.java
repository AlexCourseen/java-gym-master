package ru.yandex.practicum.gym;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimetableTest {
    private Timetable timetable;
    private Coach coach;
    private Group groupChild;
    private Group groupAdult;

    @BeforeEach
    void beforeEach() {
        timetable = new Timetable();
        coach = new Coach("Васильев", "Николай", "Сергеевич");
        groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());//Проверить, что за понедельник вернулось одно занятие
        assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());//Проверить, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        assertEquals(new TimeOfDay(13, 0), timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).
                firstKey());
        assertEquals(new TimeOfDay(20, 0), timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).
                lastKey());
        // Проверить, что за вторник не вернулось занятий
        assertEquals(0,timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        TrainingSession singleTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertEquals(0,timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0)).size());
    }

    @Test
    void testGetTwoTrainingSessionsOfDayAndSameTime() {
        TrainingSession trainingSession1 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Coach coach1 = new Coach("sds", "dsds", "dsds");
        TrainingSession trainingSession2 = new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        assertEquals(2, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());
    }

    @Test
    void testAddTwoTrainingSessionWithSameCoachOfOneDayWithDifferentTime() {
        TrainingSession trainingSession1 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession trainingSession2 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 10));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        System.out.println(timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));

        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());
        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 10)).size());
    }

    @Test
    void testCantAddTwoSameTrainingSessionToCoach() {
        TrainingSession trainingSession1 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        TrainingSession trainingSession2 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        System.out.println(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)));

        assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)).size());
    }

    @Test
    void testGetTwoTrainigsOfOneCoachByGetCountByCoaches() {

        TrainingSession trainingSession1 = new TrainingSession(groupChild, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0));
        TrainingSession trainingSession2 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        assertEquals(1, timetable.getCountByCoaches().size());
        assertEquals(2, timetable.getCountByCoaches().get(0).getCountOfTrainigs());
    }

    @Test
    void testGetDescOrderGetCountByCoaches() {
        Coach coach1 = new Coach("Тренер2", "Иван", "Иванович");
        Coach coach2 = new Coach("Тренер3", "Петр", "Пертрович");

        TrainingSession trainingSession1 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(17, 0));

        TrainingSession trainingSession3 = new TrainingSession(groupChild, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(10, 0));

        TrainingSession trainingSession4 = new TrainingSession(groupChild, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(8, 0));
        TrainingSession trainingSession5 = new TrainingSession(groupChild, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(10, 0));
        TrainingSession trainingSession6 = new TrainingSession(groupChild, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(17, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);
        timetable.addNewTrainingSession(trainingSession4);
        timetable.addNewTrainingSession(trainingSession5);
        timetable.addNewTrainingSession(trainingSession6);

        assertEquals(3, timetable.getCountByCoaches().size());

        assertEquals(3, timetable.getCountByCoaches().get(0).getCountOfTrainigs());
        assertEquals(2, timetable.getCountByCoaches().get(1).getCountOfTrainigs());
        assertEquals(1, timetable.getCountByCoaches().get(2).getCountOfTrainigs());
    }
}
