package ru.stqa.learn.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;
import ru.stqa.learn.addressbook.model.Contacts;
import ru.stqa.learn.addressbook.model.GroupData;
import ru.stqa.learn.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContacts() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
      String xml = "";
      String line = reader.readLine();
      while (line != null) {
        xml += line;
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
      }.getType());
      return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test(dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.goTo().addNewContactPage();
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((o1) -> o1.getId()).max().getAsInt()))));
    verifyContactListInUI();
  }

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test(enabled = true)
  public void testContactCreationWithPhoto() {
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData()
            .withFirstname("Petr").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("IvIv")
            .withPhoto(photo).withAddress("Moscow").withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
            .withEmail("222443@fake.fake").withEmail2("555666@fake.fake").withEmail3("777888@fake.fake")
            .withBday("1").withBmonth("January").withByear("1990").inGroup(groups.iterator().next());
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.goTo().addNewContactPage();
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((o1) -> o1.getId()).max().getAsInt()))));
    verifyContactListInUI();
  }

  @Test(enabled = true)
  public void testBadContactCreation() throws Exception {
    Groups groups = app.db().groups();
    ContactData contact = new ContactData()
            .withFirstname("Petr'").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("IvIv")
            .withAddress("Moscow").withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withEmail("222443@fake.fake")
            .withBday("1").withBmonth("January").withByear("1990").inGroup(groups.iterator().next());
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.goTo().addNewContactPage();
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before));
    verifyContactListInUI();
  }
}