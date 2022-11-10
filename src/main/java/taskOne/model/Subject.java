package org.taskOne.model;




import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity(name = "subject")
@Table(name = "subject")
public class Subject {

    @Id
    @Column(name = "subjectId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(name = "subjectName")
    private String name;
    @Column(name = "subjectUrl")
    private String url;
    @OneToMany(mappedBy = "subject",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<FlashCard> flashCards= new ArrayList<>();
    public Subject() {

    }
    public Subject( String name,String url) {

        this.name = name;
        this.url=url;
    }
    public Subject(int id, String name, ArrayList<FlashCard> flashCards, String url) {
        super();
        this.id = id;
        this.name = name;
        this.flashCards=flashCards;
        this.url=url;
    }




    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<FlashCard> getFlashCards(){
        return flashCards;
    }
    public void setFlashCards(List<FlashCard> flashCards){
        this.flashCards=flashCards;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }

    @Override
    public String toString() {
        return "subjects [id=" + id + ", name=" + name +",flashCard"+flashCards+ ",url"+url + "]";

    }

}
