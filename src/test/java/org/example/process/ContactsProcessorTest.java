package org.example.process;

import junit.framework.TestCase;
import org.example.model.Contact;


import java.util.ArrayList;
import java.util.List;

public class ContactsProcessorTest extends TestCase {
    private ContactsProcessor contactsProcessor = new ContactsProcessor();
    public void testFindDuplicates() {

        List<Contact> contacts = new ArrayList<>();
        Contact contact;

        //Add contact 1
        contact = new Contact();
        contact.setId(1001);
        contact.setFirstName("C");
        contact.setLastName("F");
        contact.setEmail("mollis.lectus.pede@outlook.net");
        contact.setZipCode("");
        contact.setAddress("449-6990 Tellus. Rd.");
        contacts.add(contact);

        //Add contact 2
        contact = new Contact();
        contact.setId(1002);
        contact.setFirstName("C");
        contact.setLastName("French");
        contact.setEmail("mollis.lectus.pede@outlook.net");
        contact.setZipCode("39746");
        contact.setAddress("449-6990 Tellus. Rd.");
        contacts.add(contact);

        //Add contact 3
        contact = new Contact();
        contact.setId(1003);
        contact.setFirstName("Ciara");
        contact.setLastName("French");
        contact.setEmail("non.lacinia.at@zoho.ca");
        contact.setZipCode("39746");
        contact.setAddress("");
        contacts.add(contact);

        List<Matching> duplicates = contactsProcessor.findDuplicates(contacts);
        assertNotNull("Duplicates list should not be null", duplicates);
        assertFalse("Duplicates list should not be empty", duplicates.isEmpty());
        assertEquals("Wrong duplicates list size", 3, duplicates.size());


        Matching d1 = findById(1001, 1002, duplicates);
        assertNotNull("1001,1002 entry should be found in duplicates", d1);
        assertEquals("Wrong accuracy for 1001, 1002", Accuracy.HIGH, d1.getAccuracy());

        Matching d2 = findById(1001, 1003, duplicates);
        assertNotNull("1001,1003 entry should be found in duplicates", d2);
        assertEquals("Wrong accuracy for 1001, 1003", Accuracy.LOW, d2.getAccuracy());

        Matching d3 = findById(1002, 1003, duplicates);
        assertNotNull("1002,1003 entry should be found in duplicates", d2);
        assertEquals("Wrong accuracy for 1002, 1003", Accuracy.LOW, d2.getAccuracy());
    }

    public void testFindDuplicatesExactMatch() {

        List<Contact> contacts = new ArrayList<>();
        Contact contact;

        //Add contact 1
        contact = new Contact();
        contact.setId(1001);
        contact.setFirstName("C");
        contact.setLastName("F");
        contact.setEmail("mollis.lectus.pede@outlook.net");
        contact.setZipCode("");
        contact.setAddress("449-6990 Tellus. Rd.");
        contacts.add(contact);

        //Add contact 2
        contact = new Contact();
        contact.setId(1001);
        contact.setFirstName("C");
        contact.setLastName("F");
        contact.setEmail("mollis.lectus.pede@outlook.net");
        contact.setZipCode("");
        contact.setAddress("449-6990 Tellus. Rd.");
        contacts.add(contact);



        List<Matching> duplicates = contactsProcessor.findDuplicates(contacts);
        assertNotNull("Duplicates list should not be null", duplicates);
        assertFalse("Duplicates list should not be empty", duplicates.isEmpty());
        assertEquals("Wrong duplicates list size", 1, duplicates.size());


        Matching d1 = findById(1001, 1001, duplicates);
        assertNotNull("1001,1001 entry should be found in duplicates", d1);
        assertEquals("Wrong accuracy for 1001, 1002", Accuracy.HIGH, d1.getAccuracy());


    }


    public void testFindDuplicatesSomeNullFields() {

        List<Contact> contacts = new ArrayList<>();
        Contact contact;

        //Add contact 1
        contact = new Contact();
        contact.setId(1001);
        contact.setFirstName(null);
        contact.setLastName("F");
        contact.setEmail(null);
        contact.setZipCode(null);
        contact.setAddress("449-6990 Tellus. Rd.");
        contacts.add(contact);

        //Add contact 2
        contact = new Contact();
        contact.setId(1002);
        contact.setFirstName(null);
        contact.setLastName(null);
        contact.setEmail(null);
        contact.setZipCode(null);
        contact.setAddress("449-6990 Tellus. Rd.");
        contacts.add(contact);

        //Add contact 3
        contact = new Contact();
        contact.setId(1003);
        contact.setFirstName(null);
        contact.setLastName("French");
        contact.setEmail("non.lacinia.at@zoho.ca");
        contact.setZipCode(null);
        contact.setAddress(null);
        contacts.add(contact);

        List<Matching> duplicates = contactsProcessor.findDuplicates(contacts);
        assertNotNull("Duplicates list should not be null", duplicates);
        assertFalse("Duplicates list should not be empty", duplicates.isEmpty());
        assertEquals("Wrong duplicates list size", 3, duplicates.size());


        Matching d1 = findById(1001, 1002, duplicates);
        assertNotNull("1001,1002 entry should be found in duplicates", d1);

        //We expect accuracy to be low because we are discarding null fields, this could be changed if needed
        assertEquals("Wrong accuracy for 1001, 1002", Accuracy.LOW, d1.getAccuracy());

        Matching d2 = findById(1001, 1003, duplicates);
        assertNotNull("1001,1003 entry should be found in duplicates", d2);
        assertEquals("Wrong accuracy for 1001, 1003", Accuracy.LOW, d2.getAccuracy());

        Matching d3 = findById(1002, 1003, duplicates);
        assertNotNull("1002,1003 entry should be found in duplicates", d2);
        assertEquals("Wrong accuracy for 1002, 1003", Accuracy.LOW, d2.getAccuracy());
    }

    private Matching findById(long from, long to,  List<Matching> duplicates) {
        for (Matching duplicate : duplicates) {
            if(from == duplicate.getFrom() && to == duplicate.getTo()){
                return duplicate;
            }
        }
        return null;
    }
}