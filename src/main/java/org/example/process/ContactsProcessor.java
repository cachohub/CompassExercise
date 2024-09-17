package org.example.process;

import org.example.model.Contact;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactsProcessor implements Comparator<Contact> {

    // Threshold indicating when two contacts are considered duplicates
    public static final int THRESHOLD = 3;

    public List<Matching> findDuplicates(List<Contact> contacts){
    List<Matching> result = new ArrayList<>();
    int i = 0;
    while(i< contacts.size()){
        int j = i + 1;
        while(j< contacts.size()){
            Contact contactFrom = contacts.get(i);
            Contact contactTo = contacts.get(j);
            Accuracy acc = calculateAccuracy(contactFrom, contactTo);
            Matching m = new Matching();
            m.setFrom(contactFrom.getId());
            m.setTo(contactTo.getId());
            m.setAccuracy(acc);
            result.add(m);
            j++;
        }
        i++;
    }
    return result;
    }

    private Accuracy calculateAccuracy(Contact contactFrom, Contact contactTo) {
        int distance = compare(contactFrom, contactTo);
        // Distance is how different are the compared contacts, in case this is near to zero
        // that means thar they are likely to be duplicates
        if(distance >= 0 && distance <= THRESHOLD){
            return Accuracy.HIGH;
        }
        return Accuracy.LOW;
    }

    /**
     *  Compares two contacts to return a difference between them, i.e,
     *  this number is closer to zero when more fields are equal
     * @param contact1 the first contact to compare
     * @param contact the second contact to compare
     * @return a difference between contacts
     */
    @Override
    public int compare(Contact contact1, Contact contact) {
        if(contact == null){
            return -1;
        }
        int matchingFields = 0;

        if(contact1.getId() == contact.getId()){
            matchingFields++;
        }

        if(isNotEmpty(contact1.getFirstName() ) && contact1.getFirstName().equals(contact.getFirstName())){
            matchingFields++;
        }

        if(isNotEmpty(contact1.getLastName() ) && contact1.getLastName().equals(contact.getLastName())){
            matchingFields++;
        }

        if(isNotEmpty(contact1.getEmail() ) && contact1.getEmail().equals(contact.getEmail())){
            matchingFields++;
        }

        if(isNotEmpty(contact1.getZipCode() ) && contact1.getZipCode().equals(contact.getZipCode())){
            matchingFields++;
        }

        if(isNotEmpty(contact1.getAddress() ) && contact1.getAddress().equals(contact.getAddress())){
            matchingFields++;
        }
        return 6 - matchingFields;
    }

    private boolean isNotEmpty(String field) {
        return field != null && !field.isEmpty();
    }
}
