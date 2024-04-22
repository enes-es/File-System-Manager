import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class Directory extends FileSystemElement {
    private LinkedList<FileSystemElement> children;

    public Directory(String name, FileSystemElement parent) {
        super(name, parent);
        children = new LinkedList<FileSystemElement>();
    }

    /**
     * doesn't check for duplicates
     * @param element
     */
    public void addElement(FileSystemElement element) {
        element.setParent(this);
        children.add(element);
    }

        /**
     * doesn't check for duplicates
     * @param element
     */
    public void removeElement(FileSystemElement element) {
        children.remove(element);
        element.setParent(null);
    }

    public ArrayList<Directory> getSubDirectories() {
        ArrayList<Directory> directoriesArrayList = new ArrayList<Directory>();

        for (FileSystemElement directoryCandidate : getChildren()) {
            if (directoryCandidate instanceof Directory)
                directoriesArrayList.add((Directory) directoryCandidate);
        }

        if (directoriesArrayList.size() == 0)
            return null;

        else
            return directoriesArrayList;
    }

    /**
     * 
     * @param name
     * @return child if exists else null
     */
    public Directory getChildDirectory(String name) {

        for (FileSystemElement directoryCandidate : getChildren()) {
            if (directoryCandidate.getName().equals(name) && directoryCandidate instanceof Directory)
                return (Directory) directoryCandidate;
        }

        return null;
    }  

    /**
     * 
     * @param name
     * @return child if exists else null
     */
    public File getFile(String name) {
        return (File) getChildren().stream().filter(i -> i instanceof File && i.getName().equals(name)).findFirst()
                .orElse(null);
    }

    /**
     *  returns all directories including this one
     * @return 
     */
    public ArrayList<Directory> getInclusiveSubDirectories() {
        ArrayList<Directory> directoriesArrayList = new ArrayList<Directory>();

        directoriesArrayList.add(this);

        directoriesArrayList.addAll(getSubDirectories());

        return directoriesArrayList;
    }

    /**
     *  checks for existence of sub directory
     * @return 
     */
    public boolean hasSubDirectory() {
        boolean has_sub_directory = false;
        
        for (FileSystemElement directoryCandidate : children) {
            if (directoryCandidate instanceof Directory) {
                has_sub_directory = true;
                break;
            }

        }
        return has_sub_directory;
    }
    
    public List<FileSystemElement> getChildren() {
        return children;
    }

    @Override
    public void print(String prefix) {
        System.out.println(prefix + "Directory: " + getName());

        for (FileSystemElement element : children) {
            element.print(prefix + " ");
        }
    }
}
