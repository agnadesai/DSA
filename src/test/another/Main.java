package test.another;

/*
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
 */
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.time.Instant;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.stream.Collectors;















/*
Doorkeeper
Write a program that behaves as a door keeper.  A doorkeeper is at the only entrance of a room and is presented with the following information when a guest enters or exits the room:

Guest {
  id: string; 
  firstName: string;
  lastName: string;
}


Doorkeeper {
enter(guest: Guest): Boolean;
exit(guest: Guest): void;
didAttend(guestId: string): Boolean;
getGuestCountAt(timestamp): Number;
getHourlyReport(): Map<Hour, Count>;
}


 Aside from recording guests, the doorkeeper has these responsibilities
Ensure that the room capacity is not exceeded
Acknowledge if a guest with the provided id attended the event
Retrieve the number of guests in the room at the given time
Provide a report that includes the maximum number of guests for each hour
 */

public class Main {
    static HashMap<String, Guest> guestHashMap = new HashMap<>();
    static HashMap<String, Guest> leftGuests = new HashMap<>();

    static int capacity = 100;

    public static void main(String[] args) {

        List<Guest> guestList = new ArrayList<>();

        Guest aGuest = new Guest();
        aGuest.setId("1");
        aGuest.setFirstName("John");
        aGuest.setFirstName("Doe");
        aGuest.setEnteredDateTime(Instant.now()); // TODO


        Guest bGuest = new Guest();
        aGuest.setId("2");
        aGuest.setFirstName("Mary");
        aGuest.setFirstName("Jane");
        aGuest.setEnteredDateTime(Instant.now()); // TODO

       // guestList.add(aGuest);
        //guestList.add(bGuest);
        enterGuest(aGuest);
        enterGuest(bGuest);

    }

    public static boolean enterGuest(Guest guest) {
        if(guestHashMap.size() < capacity) {
            guestHashMap.put(guest.id, guest);
        }
        return true;
    }

    public void exit(Guest guest) {
        guest.setLeftDateTime(Instant.now());
        leftGuests.put(guest.id, guest);
    }

    public boolean didAttend(Guest guest) {
        if(guestHashMap.get(guest.getId()) != null) {
            return true;
        } else {
            return false;
        }
    }
    //getGuestCountAt(timestamp): Number;
    //getHourlyReport(): Map<Hour, Count>;

    public int getGuestCountAt(Instant date) { //11 :30
       // Set<Guest> stillAtTheeventSet = guestHashMap.entrySet().stream().filter(g->g.getKey().equals(leftGuests.keySet())).collect(Collectors.toSet());
        int count = 0;
        for(Map.Entry<String, Guest> guestEntry : guestHashMap.entrySet()) { //
            Guest guest = guestHashMap.get(guestEntry.getKey());
            if(guest.getLeftDateTime().isBefore(date) && guest.getEnteredDateTime().isAfter(date)) { // left time 11 - 11:30
                count =  count + 1;
            }

        }
        return count;
    }



}