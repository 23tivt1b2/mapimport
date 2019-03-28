import java.time.LocalTime;

public class Clock {

    private int seconds;
    private int minutes;
    private int hours;

    private static Clock clock;

    private LocalTime endTime;

    private Clock() {
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
    }

    public void setStartTime(int seconds, int minutes, int hours) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void addSecond() {
        seconds++;
        if(seconds >= 60) {
            seconds = 1;
            addMinute();
        }
    }

    private void addMinute() {
        minutes++;
        if(minutes >= 60) {
            minutes = 1;
            addHour();
        }
    }

    private void addHour() {
        hours++;
        if(hours >= 24) {
            hours = 0;
        }
    }

    public boolean isTimeUp() {
        return hasTimePassed(endTime);
    }

    public boolean hasTimePassed(LocalTime localTime) {
        if(this.hours >= localTime.getHour()) {
            if(this.minutes >= localTime.getMinute()) {
                if(this.seconds >= localTime.getSecond()) {
                    return true;
                }
            }
        }
        return false;
    }

    public LocalTime getLocalTime() {
        return LocalTime.of(hours,minutes,seconds);
    }

    public String getTime() {
        return this.hours + ":" + this.minutes + ":" + this.seconds;
    }

    public static Clock getInstance() {
        if(clock == null) {
            clock = new Clock();
        }
        return clock;
    }
}
