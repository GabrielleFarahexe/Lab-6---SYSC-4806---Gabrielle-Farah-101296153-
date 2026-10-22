package SYSC4806;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    public void testBuddyInfoRepository() {
        BuddyInfo buddy = new BuddyInfo("Homer", "613-555-0100");

        BuddyInfo savedBuddy = buddyInfoRepository.save(buddy);

        assertNotNull(savedBuddy.getId());
        assertEquals("Homer", savedBuddy.getName());
        assertEquals("613-555-0100", savedBuddy.getPhoneNumber());
    }

    @Test
    public void testAddressBookRepository() {
        AddressBook addressBook = new AddressBook();

        addressBook.addBuddy(
                new BuddyInfo("Homer", "613-555-0100")
        );

        addressBook.addBuddy(
                new BuddyInfo("Marge", "613-555-0101")
        );

        AddressBook savedAddressBook =
                addressBookRepository.save(addressBook);

        assertNotNull(savedAddressBook.getId());
        assertEquals(2, savedAddressBook.getBuddies().size());
    }
}