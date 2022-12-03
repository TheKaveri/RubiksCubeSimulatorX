package model;

import model.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 *
 * Citation: <a href="https://github.students.cs.ubc.ca/CPSC210/AlarmSystem">...</a>
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: these tests might fail if time at which line (2) below is executed
    //is different from time that line (1) is executed.  Lines (1) and (2) must
    //run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void setup() {
        e = new Event("Event Description");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void EventEqualsTest() {
        assertEquals("Event Description", e.getDescription());
        assertEquals(d, e.getDate());

        assertNotEquals(null, e);
        assertNotEquals(e, new Object());
    }

    @Test
    public void ToStringTest() {
        assertEquals(d.toString() + "\n" + "Event Description", e.toString());
    }

    @Test
    public void TestHashCode() {
        Event e2 = new Event("Description");
        assertEquals(e.hashCode(), e.hashCode());
        assertNotEquals(e.hashCode(), e2.hashCode());
    }
}
