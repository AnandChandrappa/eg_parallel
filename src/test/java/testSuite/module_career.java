package testSuite;

import org.testng.annotations.Test;
import pageObjects.allPagesObject;


public class module_career {
    //allPagesObject allPages;

    @Test
    public void findAutomationJobInEvolutionGames(){
        allPagesObject allPages = new allPagesObject();

        allPages.careers.searchJob("IT Engineering","Latvia");
        allPages.careers.selectJobFromResult("Automation Engineer");
        allPages.careers.getPosteByNames();

    }
}
