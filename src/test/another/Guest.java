package test.another;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class Guest {

    public String id;

    public String firstName;

    public String lastName;

    public Instant enteredDateTime;

    public Instant leftDateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Instant getEnteredDateTime() {
        return enteredDateTime;
    }

    public void setEnteredDateTime(Instant enteredDateTime) {
        this.enteredDateTime = enteredDateTime;
    }

    public Instant getLeftDateTime() {
        return leftDateTime;
    }

    public void setLeftDateTime(Instant leftDateTime) {
        this.leftDateTime = leftDateTime;
    }
}
