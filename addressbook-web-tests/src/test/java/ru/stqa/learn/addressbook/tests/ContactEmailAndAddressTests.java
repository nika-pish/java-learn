package ru.stqa.learn.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.learn.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailAndAddressTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.goTo().addNewContactPage();
      app.contact().create(new ContactData()
              .withFirstname("Petr").withMiddlename("Ivanovich").withLastname("Ivanov").withNickname("IvIv")
              .withAddress("Moscow, Red Square (1)").withHomePhone("+7 (111)").withMobilePhone("22-22").withWorkPhone("333")
              .withEmail("222443@fake.fake").withEmail2("555666@fake.fake").withEmail3("777888@fake.fake")
              .withBday("1").withBmonth("January").withByear("1990"));
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactEmails() {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));
  }
}
