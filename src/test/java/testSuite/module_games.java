package testSuite;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.allPagesObject;

import java.util.Arrays;

public class module_games {
    //allPagesObject allPages;

    @Test
    public void listGamesNames(){
        String[] menuGamesNames, lhnGamesNames;
        allPagesObject allPages = new allPagesObject();
        menuGamesNames = allPages.homePage.captureMenuLinkList("Our games");
    }

    @Test
    public void listCustomersURLs(){
        allPagesObject allPages = new allPagesObject();
        allPages.homePage.clickOnMenuItem("Who We Are","Customers");
        allPages.customers.captureCustomerLinks();
    }
}
