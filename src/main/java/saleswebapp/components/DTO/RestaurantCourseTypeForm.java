package saleswebapp.components.DTO;

import saleswebapp.repository.impl.CourseType;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 07.07.2017.
 */
public class RestaurantCourseTypeForm implements Serializable {

    private static final long serialVersionUID = -4747945138016887482L;

    private int id;
    private String name;

    public RestaurantCourseTypeForm() {
        super();
    }

    public RestaurantCourseTypeForm(CourseType courseType) {
        this.id = courseType.getId();
        this.name = courseType.getName();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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
}
