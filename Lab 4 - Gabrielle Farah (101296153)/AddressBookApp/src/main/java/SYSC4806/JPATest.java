package SYSC4806;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class JPATest {

    public void performJPA() {

        BuddyInfo buddy1 = new BuddyInfo("Homer", "613-555-0100");
        buddy1.setId(1);

        BuddyInfo buddy2 = new BuddyInfo("Marge", "613-555-0101");
        buddy2.setId(2);

        AddressBook addressBook = new AddressBook();
        addressBook.setId(1);
        addressBook.addBuddy(buddy1);
        addressBook.addBuddy(buddy2);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-test");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.persist(buddy1);
        em.persist(buddy2);
        em.persist(addressBook);

        tx.commit();

        Query q = em.createQuery("SELECT a FROM AddressBook a");

        @SuppressWarnings("unchecked")
        List<AddressBook> results = q.getResultList();

        System.out.println("List of buddies\n----------------");

        for (AddressBook a : results) {
            for (BuddyInfo b : a.getBuddies()) {
                System.out.println(b.getName() + " - " + b.getPhoneNumber());
            }
        }

        // Close the connection
        em.close();

        emf.close();
    }
    public static void main(String[] args) {
        JPATest test = new JPATest();
        test.performJPA();
    }
}