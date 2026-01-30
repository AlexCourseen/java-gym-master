package ru.yandex.practicum.gym;

import java.util.Objects;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;
    private int countOfTrainigs;

    public CounterOfTrainings(Coach coach, int countOfTrainigs) {
        this.coach = coach;
        this.countOfTrainigs = countOfTrainigs;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public int getCountOfTrainigs() {
        return countOfTrainigs;
    }

    public void setCountOfTrainigs(int countOfTrainigs) {
        this.countOfTrainigs = countOfTrainigs;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CounterOfTrainings that = (CounterOfTrainings) o;
        return countOfTrainigs == that.countOfTrainigs && Objects.equals(coach, that.coach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, countOfTrainigs);
    }

    @Override
    public String toString() {
        return "CountOfTrainings{" +
                coach +
                ", count=" + countOfTrainigs +
                '}';
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return Integer.compare(o.countOfTrainigs, this.countOfTrainigs);
    }
}
