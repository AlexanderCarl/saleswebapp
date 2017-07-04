package saleswebapp.domain.repository.impl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Entity
@Table(name = "swa_todo_request_typ")
public class ToDoListRequestTyp {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "toDoRequestTyp", fetch = FetchType.LAZY)
    private List<ToDoList> toDoLists;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ToDoList> getToDoLists() {
        return toDoLists;
    }

    public void setToDoLists(List<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
    }
}
