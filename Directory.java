import java.util.LinkedList;
import java.util.List;

public class Directory extends FileSystemElement {
    private LinkedList<FileSystemElement> children;


    public Directory(String name, FileSystemElement parent) {
        super(name, parent);
        children = new LinkedList<FileSystemElement>();
    }

    public void addElement(FileSystemElement element) {
        children.add(element);
    }

    public void removeElement(FileSystemElement element) {
        children.remove(element);
    }

    public List<FileSystemElement> getChildren() {
        return children;
    }

    

    @Override
    public void print(String prefix) {
        System.out.println(prefix + "Directory: " + getName());

        for (FileSystemElement element: children) {
            element.print(prefix + " ");
        }
    }
}
