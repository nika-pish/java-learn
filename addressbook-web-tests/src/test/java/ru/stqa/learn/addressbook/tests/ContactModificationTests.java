package ru.stqa.learn.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions () {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.goTo().addNewContactPage();
            app.contact().create(new ContactData()
                    .withFirstname("Petr"). withMiddlename("Ivanov"). withLastname("Ivanovich").withNickname("IvIv")
                    .withAddress("Moscow").withMobile("44555").withEmail("222443@fake.fake")
                    .withBday("1"). withBmonth("January").withByear("1990"). withGroup("test1"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testContactModification() {

        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstname("Petr"). withMiddlename("Ivanov"). withLastname("Ivanovich")
                .withNickname("IvIv").withAddress("Moscow").withMobile("44555").withEmail("222443@fake.fake")
                .withBday("1"). withBmonth("January").withByear("1990");
        app.contact().modify(contact);
        app.goTo().homePage();
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        before.remove (modifiedContact);
        before.add (contact);
        Assert.assertEquals(before,after);
    }


}
