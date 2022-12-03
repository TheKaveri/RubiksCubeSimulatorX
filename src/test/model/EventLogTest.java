package model;

import model.Event;
import model.EventLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the EventLog class
 *
 * Citation: <a href="https://github.students.cs.ubc.ca/CPSC210/AlarmSystem">...</a>
 */
public class EventLogTest {
    private Event e1;
    private Event e2;
    private Event e3;

    @BeforeEach
    public void setup() {
        e1 = new Event("Event1");
        e2 = new Event("Event2");
        e3 = new Event("Event3");
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(e1);
        eventLog.logEvent(e2);
        eventLog.logEvent(e3);
    }

    @Test
    public void logEventTest() {
        ArrayList<Event> eventArrayList = new ArrayList<>();
        EventLog eventLog = EventLog.getInstance();

        for (Event next : eventLog) {
            eventArrayList.add(next);
        }

        assertTrue(eventArrayList.contains(e1));
        assertTrue(eventArrayList.contains(e2));
        assertTrue(eventArrayList.contains(e3));
    }

    @Test
    public void ClearTest() {
        EventLog eventLog = EventLog.getInstance();
        eventLog.clear();
        Iterator<Event> itr = eventLog.iterator();
        assertTrue(itr.hasNext());   // After log is cleared, the clear log event is added
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
