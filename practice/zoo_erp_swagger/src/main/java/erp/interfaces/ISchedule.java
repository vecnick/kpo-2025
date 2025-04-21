package erp.interfaces;

public interface ISchedule {

    int getId();

    void setSchedule(String time);
    void markCompleted(String date);
}
