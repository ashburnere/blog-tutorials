package de.rieckpil.blog.customers.control;

import de.rieckpil.blog.customers.entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Stateless
public class CustomerManager {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Customer> loadAllCustomers() {
        return this.entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public void delete(Customer customer) {
        if (entityManager.contains(customer)) {
            entityManager.remove(customer);
        } else {
            Customer managedCustomer = entityManager.find(Customer.class, customer.getId());
            if (managedCustomer != null) {
                entityManager.remove(managedCustomer);
            }
        }
    }

    public void addNewCustomer(String firstName, String lastName, String email, LocalDate dayOfBirth) {

        Customer customer = new Customer();
        customer.setDayOfBirth(dayOfBirth);
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setCustomerId(UUID.randomUUID().toString().substring(0, 8));

        this.entityManager.persist(customer);

    }
}