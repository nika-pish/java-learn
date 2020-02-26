package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() throws Exception {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData()
                .withFirstname("Petr"). withMiddlename("Ivanovich"). withLastname("Ivanov").withNickname("IvIv")
                .withAddress("Moscow").withMobile("44555").withEmail("222443@fake.fake")
                .withBday("1"). withBmonth("January").withByear("1990"). withGroup("test1");
        app.goTo().addNewContactPage();
        app.contact().create(contact);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size() +1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((o1) -> o1.getId()).max().getAsInt()))));
    }

    @Test
    public void testBadContactCreation() throws Exception {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData()
                .withFirstname("Petr'"). withMiddlename("Ivanovich"). withLastname("Ivanov").withNickname("IvIv")
                .withAddress("Moscow").withMobile("44555").withEmail("222443@fake.fake")
                .withBday("1"). withBmonth("January").withByear("1990"). withGroup("test1");
        app.goTo().addNewContactPage();
        app.contact().create(contact);
        app.goTo().homePage();
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));
    }
}

