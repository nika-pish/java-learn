package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.goTo().addNewContactPage();
      app.contact().create(new ContactData()
              .withFirstname("Petr").withMiddlename("Ivanov").withLastname("Ivanovich").withNickname("IvIv")
              .withAddress("Moscow").withMobilePhone("44555").withEmail("222443@fake.fake")
              .withBday("1").withBmonth("January").withByear("1990").withGroup("test1"));
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactModification() {

    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Petr").withMiddlename("Ivanov").withLastname("Ivanovich")
            .withNickname("IvIv").withAddress("Moscow").withMobilePhone("44555").withEmail("222443@fake.fake")
            .withBday("1").withBmonth("January").withByear("1990");
    app.contact().modify(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }


}
