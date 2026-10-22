package SYSC4806;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AddressBookRepository extends CrudRepository<AddressBook, Integer> {

    List<AddressBook> findAll();

    AddressBook findById(int id);
}
