package io.github.qlain;


import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;

public class Sprint implements SprintInterface {

    private int sprintDuration;
    private LocalDate nextSprint;
    private LocalDate now;
    private ZoneId timezone;

    Sprint(LocalDate firstSprint, int sprintDuration) {
        this.nextSprint = firstSprint;
        this.sprintDuration = sprintDuration;
        this.timezone = ZoneId.of("GMT");
    }

    public boolean isReportNow() {
        setNow(LocalDate.now(getTimezone()));

        long diff = Duration.between(getNextSprint().atTime(0, 0, 0), getNow().atTime(0, 0, 0)).toDays();

        return diff >= getSprintDuration();
    }

    void updateNextSprint() {
        if (isReportNow()) {
            setNextSprint(getNow().plusDays(getSprintDuration()));
            System.out.println("Change to NextSprint:" + getNextSprint());
        }
    }

    private int getSprintDuration() {
        return this.sprintDuration;
    }

    private LocalDate getNextSprint() {
        return this.nextSprint;
    }

    private void setNextSprint(LocalDate nextSprint) {
        this.nextSprint = nextSprint;
    }

    private LocalDate getNow() {
        return this.now;
    }

    private void setNow(LocalDate now) {
        this.now = now;
    }

    private ZoneId getTimezone() {
        return this.timezone;
    }
}
