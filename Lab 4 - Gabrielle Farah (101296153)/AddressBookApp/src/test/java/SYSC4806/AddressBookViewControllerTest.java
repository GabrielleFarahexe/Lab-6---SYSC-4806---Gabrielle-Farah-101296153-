package SYSC4806;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Lighter version: no real server, requests go straight to the controllers
@SpringBootTest
@AutoConfigureMockMvc
class AddressBookViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Test
    void rootListsAddressBooks() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Create new address book")));
    }

    @Test
    void createAddressBookRedirectsToRoot() throws Exception {
        long before = addressBookRepository.count();
        mockMvc.perform(post("/view/addressbooks"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
        assertEquals(before + 1, addressBookRepository.count());
    }

    @Test
    void addBuddyShowsUpOnTheAddressBookPage() throws Exception {
        int id = addressBookRepository.save(new AddressBook()).getId();

        mockMvc.perform(post("/view/addressbooks/" + id + "/buddies")
                        .param("name", "Ned")
                        .param("phoneNumber", "613-555-0200")
                        .param("address", "744 Evergreen Terrace"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/addressbooks/" + id));

        mockMvc.perform(get("/view/addressbooks/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ned")))
                .andExpect(content().string(containsString("744 Evergreen Terrace")));
    }

    @Test
    void unknownAddressBookIs404() throws Exception {
        mockMvc.perform(get("/view/addressbooks/999999"))
                .andExpect(status().isNotFound());
    }
}