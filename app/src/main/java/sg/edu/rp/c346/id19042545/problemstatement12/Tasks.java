package sg.edu.rp.c346.id19042545.problemstatement12;

import java.io.Serializable;

public class Tasks  implements Serializable {
    private String name;
    private String description;

    public Tasks(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
