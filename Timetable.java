import java.util.*;

public class Timetable {

    private Map<DayOfWeek, List<TrainingSession>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
        for (DayOfWeek day : DayOfWeek.values()) {
            timetable.put(day, new ArrayList<>());
        }
    }


    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek day = trainingSession.getDayOfWeek();
        List<TrainingSession> daySession = timetable.get(day);
        daySession.add(trainingSession);

    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        List<TrainingSession> allSessions = timetable.get(dayOfWeek);
        List<TrainingSession> result = new ArrayList<>();

        for (TrainingSession session : allSessions) {
            if (session.getTimeOfDay().equals(timeOfDay)) {
                result.add(session);
            }
        }
        return result;
    }

    public List<CounterTraining> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            List<TrainingSession> sessionsForDay = timetable.get(day);
            for (TrainingSession session : sessionsForDay) {
                Coach currentCoach = session.getCoach();
                if (coachCounts.containsKey(currentCoach)) {
                    int currentCount = coachCounts.get(currentCoach);
                    coachCounts.put(currentCoach, currentCount + 1);
                } else {
                    coachCounts.put(currentCoach, 1);
                }
            }
        }

        List<CounterTraining> result = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCounts.entrySet()) {
            Coach coach = entry.getKey();
            int count = entry.getValue();

            CounterTraining counter = new CounterTraining(coach, count);
            result.add(counter);
        }

        for (int i = 0; i < result.size() - 1; i++) {
            for (int j = 0; j < result.size() - 1 - i; j++) {
                CounterTraining first = result.get(j);
                CounterTraining second = result.get(j + 1);
                if (first.getCount() < second.getCount()) {
                    result.set(j, second);
                    result.set(j + 1, first);
                }
            }
        }

        return result;
    }

    public class CounterTraining {
        private Coach coach;
        private int count;

        public CounterTraining(Coach coach, int count) {
            this.coach = coach;
            this.count = count;
        }

        public Coach getCoach() {
            return coach;
        }

        public int getCount() {
            return count;
        }

        @Override
        public String toString() {
            return coach + ": " + count + " тренировок";
        }
    }


}