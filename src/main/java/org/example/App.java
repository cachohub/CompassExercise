package org.example;

import org.example.model.Contact;
import org.example.process.Accuracy;
import org.example.process.ContactsProcessor;
import org.example.process.Matching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Test Sample showing results
 *
 */
public class App 
{
    private static final String CSV_FILE = "src/main/resources/sample.csv";
    private static final String COMMA_DELIMITER = ",";

    public static void main(String[] args )
    {
        ContactsProcessor contactsProcessor = new ContactsProcessor();
        try (Stream<String> lines = Files.lines(Paths.get(CSV_FILE))) {
            List<Contact> records = lines.skip(1).map(line -> readContact(line.split(COMMA_DELIMITER)))
                    .collect(Collectors.toList());
            List<Matching> duplicates = contactsProcessor.findDuplicates(records);
            //Show possible duplicates
            List<Matching> possibleDuplicates = duplicates.stream().filter(m->m.getAccuracy() == Accuracy.HIGH).collect(Collectors.toList());
            System.out.println("These are the possible duplicates:");
            possibleDuplicates.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Contact readContact(String[] split) {
        Contact contact = new Contact();
        contact.setId(Long.parseLong(split[0]));
        if(split.length > 1) {
            contact.setFirstName(split[1]);
            if(split.length > 2) {
                contact.setLastName(split[2]);
                if(split.length > 3) {
                    contact.setEmail(split[3]);
                    if(split.length > 4) {
                        contact.setZipCode(split[4]);
                        if (split.length > 5) {
                            contact.setAddress(split[5]);
                        }
                    }
                }
            }
        }
        return contact;
    }
}
