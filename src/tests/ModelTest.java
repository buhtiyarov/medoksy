package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import model.Client;
import model.Visit;

@RunWith(JUnit4.class)
public class ModelTest {
    @Test
    public void testClientReturnsEmptyVisits() {
        Client client = new Client();
        assertTrue(client.getVisits() instanceof ArrayList);
        assertEquals(client.getVisits().size(), 0);
    }

    @Test
    public void testClientRecievesVisit() {
        Client client = new Client();
        client.addVisit(new Visit());
        assertEquals(client.getVisits().size(), 1);
    }

    @Test
    public void testClientRecievesVisits() {
        Client client = new Client();
        ArrayList<Visit> visits = new ArrayList<Visit>();
        visits.add(new Visit());
        client.setVisits(visits);
        assertEquals(client.getVisits().size(), 1);
    }
}
