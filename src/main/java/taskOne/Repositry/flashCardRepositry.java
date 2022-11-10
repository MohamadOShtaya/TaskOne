package taskOne.Repositry;



import taskOne.model.FlashCard;
import taskOne.model.Question;
import taskOne.model.Subject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Repositry {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public Repositry(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Subject> findFirstSubject() {

        entityManager.createQuery("select * from subject where id:=1").getResultList();
        return entityManager.createQuery("select * from subject where subjectId:=1").getResultList();
    }
    public List<FlashCard> getFirstFiveFlashCard(){
        if(!entityManager.getTransaction().isActive())
        {
            entityManager.getTransaction().begin();
        }
        //select * from flashcard where flashCardId=28
       List<FlashCard> flashCards =  entityManager.createQuery("select * from subject limit =5").getResultList();
        return flashCards;
    }

    public List<Question> getFirstTenQuestions(){
            if(!entityManager.getTransaction().isActive())
            {
                entityManager.getTransaction().begin();
            }
            entityManager.createQuery("select * from subject where id:<5").getResultList();
            return entityManager.createQuery("select * from question where questionId:<11").getResultList();
        }


}
