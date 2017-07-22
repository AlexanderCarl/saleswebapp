package saleswebapp.components.DTO;

import java.io.Serializable;

/**
 * Created by Alexander Carl on 21.07.2017.
 */
public class ATestForm implements Serializable {

    private static final long serialVersionUID = -5437007537438673117L;

    private String id;
    private String name;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
