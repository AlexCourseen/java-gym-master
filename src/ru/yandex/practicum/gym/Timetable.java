package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek trainingDay = trainingSession.getDayOfWeek();
        TimeOfDay trainingTime = trainingSession.getTimeOfDay();
        Coach trainigCoach = trainingSession.getCoach();
        ArrayList<TrainingSession> trainingSessionsPerTime = new ArrayList<>();
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsPerDay = new TreeMap<>();
        if (!timetable.isEmpty() && timetable.containsKey(trainingDay)) {
            trainingsPerDay = timetable.get(trainingDay);
            if (trainingsPerDay.containsKey(trainingTime)) {
                trainingSessionsPerTime = trainingsPerDay.get(trainingTime);
                int index = 0;
                for (TrainingSession training : trainingSessionsPerTime) {
                    if (training.getCoach().equals(trainigCoach)) {
                        trainingSessionsPerTime.set(index, trainingSession);
                        return;
                    }
                    index++;
                }
            }
        }
        trainingSessionsPerTime.add(trainingSession);
        trainingsPerDay.put(trainingTime, trainingSessionsPerTime);
        timetable.put(trainingDay, trainingsPerDay);
    }


    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionsForDay;
        if (!timetable.isEmpty() && timetable.get(dayOfWeek) != null) {
            trainingSessionsForDay = timetable.get(dayOfWeek);
        } else {
            trainingSessionsForDay = new TreeMap<>();
        }
        return trainingSessionsForDay;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        ArrayList<TrainingSession> trainingSessions;
        if (timetable.containsKey(dayOfWeek) && timetable.get(dayOfWeek).containsKey(timeOfDay)) {
            trainingSessions = timetable.get(dayOfWeek).get(timeOfDay);
        } else {
            System.out.println("Нет тренировок за указанные Дата+Время");
            trainingSessions = new ArrayList<>();
        }
        return trainingSessions;
    }


    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> coachTrainings = new HashMap<>();
        ArrayList<CounterOfTrainings> countersOfTrainings = new ArrayList<>();
        if (!timetable.isEmpty()) {
            for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainigsPerDay : timetable.values()) {
                for (ArrayList<TrainingSession> trainings : trainigsPerDay.values()) {
                    for (TrainingSession trainingSession : trainings) {
                        Coach currentTrainigCoach = trainingSession.getCoach();
                        int countOfTrainings;
                        if (coachTrainings.containsKey(currentTrainigCoach)) {
                            countOfTrainings = coachTrainings.get(currentTrainigCoach) + 1;
                        } else {
                            countOfTrainings = 1;
                        }
                        coachTrainings.put(currentTrainigCoach, countOfTrainings);
                    }
                }
            }
            for (Coach coach : coachTrainings.keySet()) {
                CounterOfTrainings counterOfTrainings = new CounterOfTrainings(coach, coachTrainings.get(coach));
                countersOfTrainings.add(counterOfTrainings);
                Collections.sort(countersOfTrainings);
            }
        } else {
            System.out.println("Расписание пустое");
        }
        return countersOfTrainings;
    }
}
