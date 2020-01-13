package ru.stqa.learn.addressbook;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        goToHomePage();
        selectContact();
        deleteContact();
        acceptDeletion();
        goToHomePage();

    }

}
