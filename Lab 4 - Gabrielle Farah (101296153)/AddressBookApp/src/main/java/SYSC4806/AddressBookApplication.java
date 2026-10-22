package SYSC4806;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AddressBookApplication {

    private static final Logger log = LoggerFactory.getLogger(AddressBookApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AddressBookApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BuddyInfoRepository buddyRepo, AddressBookRepository bookRepo) {
        return (args) -> {
            // save a few buddies on their own
            buddyRepo.save(new BuddyInfo("Homer", "613-555-0100"));
            buddyRepo.save(new BuddyInfo("Marge", "613-555-0101"));
            buddyRepo.save(new BuddyInfo("Bart", "613-555-0102"));

            // fetch all buddies
            log.info("Buddies found with findAll():");
            log.info("-------------------------------");
            for (BuddyInfo buddy : buddyRepo.findAll()) {
                log.info(buddy.toString());
            }
            log.info("");

            // fetch an individual buddy by ID
            BuddyInfo first = buddyRepo.findAll().iterator().next();
            BuddyInfo byId = buddyRepo.findById(first.getId());
            log.info("Buddy found with findById({}):", first.getId());
            log.info("--------------------------------");
            log.info(byId.toString());
            log.info("");

            // fetch buddies by name
            log.info("Buddy found with findByName('Homer'):");
            log.info("--------------------------------------------");
            buddyRepo.findByName("Homer").forEach(b -> log.info(b.toString()));
            log.info("");

            // save an address book (buddies are cascaded)
            AddressBook book = new AddressBook();
            book.addBuddy(new BuddyInfo("Lisa", "613-555-0103"));
            book.addBuddy(new BuddyInfo("Maggie", "613-555-0104"));
            bookRepo.save(book);

            // fetch all address books
            log.info("Address books found with findAll():");
            log.info("-------------------------------");
            for (AddressBook ab : bookRepo.findAll()) {
                log.info("AddressBook {} -> {}", ab.getId(), ab.getBuddies());
            }
            log.info("");

            // fetch an address book by ID
            AddressBook savedBook = bookRepo.findAll().get(0);
            AddressBook foundBook = bookRepo.findById(savedBook.getId());
            log.info("Address book found with findById({}):", savedBook.getId());
            log.info("--------------------------------");
            log.info("AddressBook {} -> {}", foundBook.getId(), foundBook.getBuddies());
            log.info("");
        };
    }
}