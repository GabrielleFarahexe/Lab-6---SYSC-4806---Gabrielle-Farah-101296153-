package SYSC4806;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressBookRestController {

    private final AddressBookRepository addressBookRepository;
    private final BuddyInfoRepository buddyInfoRepository;

    public AddressBookRestController(AddressBookRepository addressBookRepository,
                                     BuddyInfoRepository buddyInfoRepository) {
        this.addressBookRepository = addressBookRepository;
        this.buddyInfoRepository = buddyInfoRepository;
    }

    // create an address book
    @PostMapping(value = "/addressbooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressBook> createAddressBook() {
        AddressBook saved = addressBookRepository.save(new AddressBook());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // list all address books
    @GetMapping(value = "/addressbooks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AddressBook> getAddressBooks() {
        return addressBookRepository.findAll();
    }

    // get one address book
    @GetMapping(value = "/addressbooks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressBook> getAddressBook(@PathVariable int id) {
        AddressBook book = addressBookRepository.findById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    // create a buddy
    @PostMapping(value = "/buddies",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuddyInfo> createBuddy(@RequestBody BuddyInfo buddy) {
        buddy.setId(0);
        BuddyInfo saved = buddyInfoRepository.save(buddy);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // list all buddies
    @GetMapping(value = "/buddies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<BuddyInfo> getBuddies() {
        return buddyInfoRepository.findAll();
    }

    // add an existing buddy to an existing address book
    @PostMapping(value = "/addressbooks/{bookId}/buddies/{buddyId}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressBook> addBuddyToAddressBook(@PathVariable int bookId,
                                                             @PathVariable int buddyId) {
        AddressBook book = addressBookRepository.findById(bookId);
        BuddyInfo buddy = buddyInfoRepository.findById(buddyId);
        if (book == null || buddy == null) {
            return ResponseEntity.notFound().build();
        }

        boolean alreadyThere = book.getBuddies().stream().anyMatch(b -> b.getId() == buddyId);
        if (alreadyThere) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        book.addBuddy(buddy);
        try {
            return ResponseEntity.ok(addressBookRepository.save(book));
        } catch (DataIntegrityViolationException e) {
            // a buddy can only belong to one address book
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
