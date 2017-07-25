package saleswebapp.repository.impl;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by Alexander Carl on 06.07.2017.
 */
@Entity
@Table(name = "kitchen_type")
public class KitchenType  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "kitchenTypes")
    private List<Restaurant> restaurantList;

    public KitchenType() {
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

        KitchenType other = (KitchenType) obj;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
