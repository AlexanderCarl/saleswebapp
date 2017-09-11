package saleswebapp.repository.impl;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Alexander Carl on 04.07.2017.
 */
@Entity
@Table(name = "swa_todo_request_typ")
public class ToDoRequestTyp {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "toDoRequestTyp", fetch = FetchType.LAZY)
    private List<ToDo> toDos;

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

    public List<ToDo> getToDos() {
        return toDos;
    }

    public void setToDos(List<ToDo> toDos) {
        this.toDos = toDos;
    }
}
