package SYSC4806;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;

@Entity
public class AddressBook {
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BuddyInfo> buddies;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    public AddressBook() {
        this.buddies = new ArrayList<>();
    }

    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            this.buddies.add(buddy);
        }
    }

    public void removeBuddy(BuddyInfo buddy) {
        this.buddies.remove(buddy);
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // update test
    public static void main(String[] args) {
        BuddyInfo buddy1 = new BuddyInfo("Homer", "613-555-0100");
        BuddyInfo buddy2 = new BuddyInfo("Marge", "613-555-0101");

        AddressBook addressBook = new AddressBook();
        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        for (BuddyInfo b : addressBook.getBuddies()) {
            System.out.println(b);
        }
    }
}