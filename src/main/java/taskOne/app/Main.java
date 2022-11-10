package org.taskOne.app;


import org.taskOne.Crawling.Crawling;
import org.taskOne.Repositry.Repositry;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class Main {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static Repositry fileRepository;
    private static Crawling crawling;
    public static void main(String[] args){

        crawling = new Crawling(entityManager);
        crawling.crawling();

        fileRepository = new Repositry(entityManager);
        fileRepository.findFirstSubject();
        entityManager.close();

        fileRepository.getFirstFiveFlashCard();
        entityManager.close();

        fileRepository.getFirstTenQuestions();
        entityManager.close();
    }
}
