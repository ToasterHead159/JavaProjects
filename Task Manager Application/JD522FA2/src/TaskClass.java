
import java.io.Serializable;


public class TaskClass implements Serializable{

    private int ID;
    private String Name;
    private String Description;
    private String Status;
    private String Category;

    public TaskClass(int id, String name, String description, String status, String category) {//constructor for objects being retrived from the Database
        this.ID = id;
        this.Name = name;
        this.Description = description;
        this.Status = status;
        this.Category = category;
    }
    
    public TaskClass(String name,String description, String status, String category) {//constructor for the objects being written to the text file.
        this.Name = name;
        this.Description = description;
        this.Status = status;
        this.Category = category;
    }
    
    public TaskClass(String category) {//constructor for the category objects in combo boxes.
        this.Category = category;
    }

    public void setID(int id) {
        this.ID = id;
    }

    public int getID() {
        return this.ID;
    }
    
    public void setName(String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }
    
    public void setDescription(String desc) {
        this.Description = desc;
    }

    public String getDescription() {
        return this.Description;
    }
    
    public void setStatus(String stat) {
        this.Status = stat;
    }

    public String getStatus() {
        return this.Status;
    }
    
    public void setCategory(String cat) {
        this.Category = cat;
    }

    public String getCategory() {
        return this.Category;
    }

    @Override
    public String toString() {//used to display tasks in the task selection combobox for update and delete statments.
        return String.format("%s, %s, %s, %s", Name, Description, Status, Category);

    }
}
