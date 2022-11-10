package taskOne.Repositry;

import taskOne.model.FlashCard;
import taskOne.model.Question;
import taskOne.model.Subject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class service {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static subjectRepo subjectRepo;
    private static  flashCardRepositry flashCardRepositry;
    private static questionRepo questionRepo;
    private List<Subject> listSubject;
    private List<FlashCard> listFlash;
    private List<Question> listquestion;
    public service(List<Subject> listSubject, List<FlashCard> listFlash, List<Question> listquestion){
        this.subjectRepo= new subjectRepo(entityManager);
        this.flashCardRepositry= new flashCardRepositry(entityManager);
        this.questionRepo = new questionRepo(entityManager);
        this.listSubject=listSubject;
        this.listFlash=listFlash;
        this.listquestion=listquestion;

    }


    public void inserToDateBase(){
        try {
            entityManager.getTransaction().begin();
            subjectRepo.saveSubject(listSubject);
            flashCardRepositry.saveFlashCard(listFlash);
            questionRepo.saveQuestion(listquestion);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        catch (Exception exception){
            entityManager.getTransaction().rollback();
        }

    }

}
