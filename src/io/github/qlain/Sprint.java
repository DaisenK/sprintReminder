package io.github.qlain;


import java.time.Duration;
import java.time.LocalDate;

public class Sprint implements SprintInterface {

    private int sprintDuration;
    private LocalDate lastSprint;
    private LocalDate now;

    Sprint(LocalDate firstSprint, int sprintDuration) {
        this.lastSprint = firstSprint;
        this.sprintDuration = sprintDuration;
    }

    public boolean isReportNow() {
        setNow(LocalDate.now());

        long diff = Duration.between(getLastSprint().atTime(9, 0, 0), getNow().atTime(9, 0, 0)).toDays();

        return diff >= getSprintDuration();
    }

    public void updateLastSprint() {
        if (isReportNow()) {
            setLastSprint(getNow().plusDays(getSprintDuration()));
            System.out.println("Change to LastSprint:" + getLastSprint());
        }
    }

    private int getSprintDuration() {
        return this.sprintDuration;
    }

    private LocalDate getLastSprint() {
        return this.lastSprint;
    }

    private void setLastSprint(LocalDate lastSprint) {
        this.lastSprint = lastSprint;
    }

    private LocalDate getNow() {
        return this.now;
    }

    private void setNow(LocalDate now) {
        this.now = now;
    }
}
