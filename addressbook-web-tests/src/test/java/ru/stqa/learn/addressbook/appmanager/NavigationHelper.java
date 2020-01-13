package ru.stqa.learn.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper {
    public WebDriver wd;

    public NavigationHelper(WebDriver wd) {
        this.wd = wd;

    }

    public void gotoGroupPage() {
      wd.findElement(By.linkText("groups")).click();
    }


    public void goToAddNewContactPage() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void goToHomePage() {
        wd.findElement(By.linkText("home")).click();
    }
}
