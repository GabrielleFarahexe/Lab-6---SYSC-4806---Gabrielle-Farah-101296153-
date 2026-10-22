package SYSC4806;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AddressBookViewController {

    private final AddressBookRepository addressBookRepository;

    public AddressBookViewController(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "index";
    }

    @PostMapping("/view/addressbooks")
    public String createAddressBook() {
        addressBookRepository.save(new AddressBook());
        return "redirect:/";
    }

    @GetMapping("/view/addressbooks/{id}")
    public String viewAddressBook(@PathVariable int id, Model model) {
        model.addAttribute("addressBook", findOr404(id));
        return "addressbook";
    }

    @PostMapping("/view/addressbooks/{id}/buddies")
    public String addBuddy(@PathVariable int id,
                           @RequestParam String name,
                           @RequestParam String phoneNumber,
                           @RequestParam(defaultValue = "") String address) {
        AddressBook book = findOr404(id);
        book.addBuddy(new BuddyInfo(name, phoneNumber, address));
        addressBookRepository.save(book);
        return "redirect:/view/addressbooks/" + id;
    }

    private AddressBook findOr404(int id) {
        AddressBook book = addressBookRepository.findById(id);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address book not found");
        }
        return book;
    }
}