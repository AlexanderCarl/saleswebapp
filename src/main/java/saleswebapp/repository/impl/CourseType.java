package saleswebapp.repository.impl;

import javax.persistence.*;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Entity
@Table(name = "course_types")
public class CourseType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public CourseType(String name) {
        this.name = name;
    }

    public CourseType() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        CourseType other = (CourseType) obj;
        if (Integer.valueOf(id) == null) {
            if (Integer.valueOf(other.id) != null)
                return false;
        }

        if (id != other.getId()) {
            return false;
        }

        if (!name.equals(other.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Integer.valueOf(id) == null) ? 0 : (Integer.valueOf(id).hashCode()));

        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
