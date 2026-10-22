package SYSC4806;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressBookTest {

    @Test
    void addBuddy() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Homer", "613-555-0100");
        BuddyInfo buddy2 = new BuddyInfo("Homer", "613-555-0100");

        addressBook.addBuddy(buddy);
        // override .equals method in buddyinfo
        // Verify that the size increased to 1 and the list contains the buddy
        assertEquals(1, addressBook.getBuddies().size());
        assertTrue(addressBook.getBuddies().contains(buddy));
    }

    @Test
    void removeBuddy() {
        AddressBook addressBook = new AddressBook();
        BuddyInfo buddy = new BuddyInfo("Homer", "613-555-0100");

        addressBook.addBuddy(buddy);
        addressBook.removeBuddy(buddy);

        // Verify that the list is empty after removal
        assertEquals(0, addressBook.getBuddies().size());
        assertFalse(addressBook.getBuddies().contains(buddy));
    }
}