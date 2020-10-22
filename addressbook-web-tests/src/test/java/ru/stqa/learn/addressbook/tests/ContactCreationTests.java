package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test(enabled = true)
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withFirstname("Petr").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("IvIv")
            .withPhoto(photo).withAddress("Moscow").withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
            .withEmail("222443@fake.fake").withEmail2("555666@fake.fake").withEmail3("777888@fake.fake")
            .withBday("1").withBmonth("January").withByear("1990").withGroup("test2");
    app.goTo().addNewContactPage();
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((o1) -> o1.getId()).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testBadContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstname("Petr'").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("IvIv")
            .withAddress("Moscow").withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withEmail("222443@fake.fake")
            .withBday("1").withBmonth("January").withByear("1990").withGroup("test2");
    app.goTo().addNewContactPage();
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before));
  }
}

