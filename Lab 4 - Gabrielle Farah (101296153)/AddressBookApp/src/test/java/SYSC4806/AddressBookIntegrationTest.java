package SYSC4806;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookIntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void homePageLoads() {
        ResponseEntity<String> response = rest.getForEntity("/", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Address Books"));
    }

    @Test
    void createAddressBookViaRest() {
        ResponseEntity<AddressBook> response = rest.postForEntity("/addressbooks", null, AddressBook.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getId() > 0);
    }

    @Test
    void createBuddyWithAddressViaRest() {
        BuddyInfo buddy = new BuddyInfo("Ned", "613-555-0200", "744 Evergreen Terrace");
        ResponseEntity<BuddyInfo> response = rest.postForEntity("/buddies", buddy, BuddyInfo.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Ned", response.getBody().getName());
        assertEquals("744 Evergreen Terrace", response.getBody().getAddress());
    }
}