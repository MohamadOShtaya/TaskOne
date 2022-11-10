package org.taskOne.Crawling;


import org.taskOne.model.FlashCard;
import org.taskOne.model.Question;
import org.taskOne.model.Subject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Crawling {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Persistence");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    public List<FlashCard> listOfflash = new ArrayList<>();
    private List<String> linkList = new ArrayList<>();
    public List<Question> questionsList = new ArrayList<>();
    public List<Subject> listOfsub = new ArrayList<>();
    private int count = 0;

    public Crawling(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void crawling(){
        //************************************
        // open web browser
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Mohamad O Shtaya\\Downloads\\geckodriver-v0.30.0-win64\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

        ProfilesIni settings = new ProfilesIni();
        FirefoxProfile profile1 = settings.getProfile("default");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(profile1);
        driver.get("https://www.assignguru.com/mcqs/?fbclid=IwAR1YYsyJZNdR3RBhbZn4A9XTZmgh6WTxWwarWJSHrxu5PwF3wgCFkHV9vOQ#gsc.tab=0");
        //*************************************
        //save subject elements

        List<WebElement> listSubElm = driver.findElements(By.xpath("//*[@id=\"bd-sidebar\"]/ul/li/a"));
        for (WebElement obj : listSubElm) {
            count++;
            listOfsub.add(new Subject(count, obj.getText(), null, obj.getAttribute("href")));
        }
        //*************************************
        //saving flash card
        for (int i = 0; i < listOfsub.size(); i++) {
            driver.get(listOfsub.get(i).getUrl());
            //****************************************handle button next
            String str = driver.findElement(By.xpath("/html/body/div/main/div[3]/div/div[2]/nav/a[2]")).getText();
            String[] arr = str.split(" ");
            int firstNumber = Integer.parseInt(arr[0]);
            int lastNumber = Integer.parseInt(arr[2]);
            //*****************************************saving flash card
            while (firstNumber < lastNumber) {
                List<WebElement> listQusElm = driver.findElements(By.xpath("/html/body/div/main/div[3]/div/div[3]/div/a"));
                WebElement btnNext = driver.findElement(By.xpath("/html/body/div/main/div[3]/div/div[2]/nav/a[1]"));
                for (WebElement obj : listQusElm) {
                    linkList.add(obj.getAttribute("href"));
                    listOfflash.add(new FlashCard(obj.getText(), null, obj.getAttribute("href")));
                }
                listOfsub.get(i).setFlashCards(listOfflash);
                btnNext.click();
                firstNumber++;
                driver.getCurrentUrl();
                driver.get(driver.getCurrentUrl());
            }

        }
        //*************************************************
        //save question
        for (int j = 0; j < listOfsub.size(); j++) {
            driver.get(listOfsub.get(j).getUrl());
            for (int k = 0; k < listOfflash.size(); k++) {
                driver.get(listOfflash.get(k).getUrl());
                List<WebElement> questionElements = driver.findElements(By.xpath("/html/body/div/div/div[1]/div"));
                for (WebElement e : questionElements) {
                    questionsList.add(new Question(e.getText()));
                }
                questionsList.remove(0);
                questionsList.remove(1);
                listOfflash.get(k).setqList(questionsList);
            }
        }
        entityManager.getTransaction().begin();
        for(int i=0;i<listOfsub.size();i++){
            Subject subject = new Subject();
            subject = listOfsub.get(i);
            entityManager.persist(subject);
            entityManager.getTransaction().commit();
        }
        entityManager.close();



        entityManager.getTransaction().begin();
        for (int i=0;i<listOfflash.size();i++){
            FlashCard flashCard = new FlashCard();
            flashCard = listOfflash.get(i);
            entityManager.persist(flashCard);
            entityManager.getTransaction().commit();
        }
        entityManager.close();



        entityManager.getTransaction().begin();
        for (int i=0;i<questionsList.size();i++){
            Question question = new Question();
            question = questionsList.get(i);
            entityManager.persist(question);
            entityManager.getTransaction().begin();
        }
        entityManager.close();
    }
}
