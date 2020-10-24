package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;
import ru.stqa.learn.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new ContactData().withLastname("Popov1").withFirstname("Dmitrii 1").withAddress("Belgorod 1")
            .withEmail("111111@fake.fake 1").withMobilePhone("111 1")
            .withBday("1").withBmonth("January").withByear("1991").withGroup("test2")});
    list.add(new Object[] {new ContactData().withLastname("Popov2").withFirstname("Dmitrii 2").withAddress("Belgorod 2")
            .withEmail("111111@fake.fake 2").withMobilePhone("111 2")
            .withBday("2").withBmonth("February").withByear("1992").withGroup("test2")});
    list.add(new Object[] {new ContactData().withLastname("Popov3").withFirstname("Dmitrii 3").withAddress("Belgorod 3")
            .withEmail("111111@fake.fake 3").withMobilePhone("111 3")
            .withBday("3").withBmonth("March").withByear("1993").withGroup("test2")});
    return list.iterator();
  }

  @Test (dataProvider = "validGroups")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.goTo().addNewContactPage();
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((o1) -> o1.getId()).max().getAsInt()))));
  }

  @Test (enabled = true)
  public void testContactCreationWithPhoto() {
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

