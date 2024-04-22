import java.sql.Timestamp;


public abstract class FileSystemElement{
    String name;
    Timestamp dateCreated;
    FileSystemElement parentElement;

    public FileSystemElement(String name, FileSystemElement parentElement) {
        this.name = name;
        this.parentElement = parentElement;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public Timestamp getDateCreated(){
        return dateCreated;
    }

    public FileSystemElement getParentElement() {
        return parentElement;
    }

    //get full path
    public String getFullPath() {
        if (parentElement == null) {
            return name;
        } else {
            return parentElement.getFullPath() + "/" + name;
        }
    }



    //PARENT SETTER
    public void setParent(FileSystemElement parentElement) {
        this.parentElement = parentElement;
    }

    public abstract void print(String prefix);


}
