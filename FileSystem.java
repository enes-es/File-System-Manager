import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public class FileSystem {
    private Directory root;
    private Directory currentDirectory;

    public FileSystem() {
        this.root = new Directory("root", null);
        this.currentDirectory = root;
    }

    public void createFile(String name, Directory parent) {
        File newFile = new File(name, parent);
        parent.addElement(newFile);
        // IMPROVEMENT: check for duplicates and throw if there is duplicate!
    }

    public void createDirectory(String name, Directory parent) {
        Directory newDir = new Directory(name, parent);
        parent.addElement(newDir);
    }

    public void deleteFile(String name, Directory parent) {
        FileSystemElement toDelete;

        // find file here

        if (toDelete == null) {
            throw new NoSuchElementException(
                    "Can't find the file named" + name + " inside the path: " + parent.getFullPath());
        }

        parent.removeElement(toDelete);
    }

    public void deleteDirectory(String name, Directory parent) {
        Directory toDelete;
        FileSystemElement searchResult;

        // find directory here
        searchResult = returnElement(name, parent);

        if (searchResult == null || !(searchResult instanceof Directory)) {
            throw new NoSuchElementException(
                    "Can't find the directory named" + name + " inside the path: " + parent.getFullPath());
        }

        toDelete = (Directory) searchResult;

        parent.removeElement(toDelete);
    }

    public void moveElement(String name, Directory newParent) {
        FileSystemElement target = null;

        // find element here
        target = returnElement(name, currentDirectory);
        if (target == null) {
            throw new NoSuchElementException();
        }

        target.getParentElement();

        // remove from parent here

        newParent.addElement(target);

    }

    public FileSystemElement returnElement(String name, Directory parent) {
        FileSystemElement target;

        target = parent.getChildren().stream()
                .filter(element -> element.getName().equals(name))
                .findFirst()
                .orElse(null);

        return target;
    }

    public boolean searchDirectory(String name) {

        return false;
    }

    public boolean searchFile(String name) {

        return false;
    }

    // DEBUG:
    public void printDirectoryTree() {
        // this is the path to the current directory from root
        // then contents of current directory are printed

        // log ancestor directories until root
        // print ancestor directories
        // print current directory
        //// print directory contents.
        //////// if file print in file format
        //////// if directory print in directory format

        ArrayList<FileSystemElement> ancestorDirectories = new ArrayList<FileSystemElement>();

        FileSystemElement iterateDirectory = currentDirectory;

        int count;
        int index;

        // log ancestor directories
        while (iterateDirectory.parentElement != null) {
            ancestorDirectories.add(iterateDirectory);
            iterateDirectory = iterateDirectory.getParentElement();
        }

        // print ancestor directories
        for (count = 0, index = ancestorDirectories.size() - 1; count < ancestorDirectories.size(); ++count, --index) {
            printHelper.printSpaces(count);
            System.out.print("* ");
            System.out.print(ancestorDirectories.get(index).getName());
            System.out.println("/");
        }

        // print current director and its contents
        printHelper.printSpaces(count);
        System.out.print("* ");
        System.out.print(currentDirectory.getName());
        System.out.print("/ ");
        System.out.println("(Current Directory)");

        // print contents
        printHelper.printContents(currentDirectory, count);

    }

    // TODO: complete methods.
    private static class printHelper {
        static void printSpaces(int count) {
            IntStream.range(0, count).forEach(i -> System.out.print(" "));
        }

        // CHECK:
        static void printContents(Directory directory, int spaces) {
            ListIterator<FileSystemElement> contents = directory.getChildren().listIterator();
            FileSystemElement currElement;
            StringBuilder sBuilder = new StringBuilder();
            String leadingSpaces;

            for (int i = 0; i < spaces; ++i)
                sBuilder.append(" ");

            leadingSpaces = sBuilder.toString();
            sBuilder.setLength(0); // "clear" the builder

            while (contents.hasNext()) {
                currElement = contents.next();

                sBuilder.append(leadingSpaces);

                if (currElement instanceof Directory) {
                    sBuilder.append("* ");
                    sBuilder.append(currElement.getName() + "/" + "\n");

                }

                if (currElement instanceof File) {
                    sBuilder.append(currElement.getName() + "\n");
                }


                // get new
                // add space
                // if directory add start space
                // append along with \n at end.
            }

            System.out.print(sBuilder);

        }

        // CHECK
        static void printContents(Directory directory) {
            ListIterator<FileSystemElement> contents = directory.getChildren().listIterator();
            FileSystemElement currElement;
            StringBuilder sBuilder = new StringBuilder();

            while (contents.hasNext()) {
                currElement = contents.next();

                if (currElement instanceof Directory) {
                    sBuilder.append("* ");
                    sBuilder.append(currElement.getName() + "/\n");

                }

                if (currElement instanceof File) {
                    sBuilder.append(currElement.getName() + "\n");
                }

                else {
                    throw new TypeNotPresentException("Directory or File", null);
                }
            }

            System.out.print(sBuilder);

        }

        static void printContentsWithDate(Directory directory) {
            ListIterator<FileSystemElement> contents = directory.getChildren().listIterator();
            FileSystemElement currElement;
            StringBuilder sBuilder = new StringBuilder();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("(yyyy-MM-dd HH-mm-ss)");
            while (contents.hasNext()) {
                currElement = contents.next();

                if (currElement instanceof Directory) {
                    sBuilder.append("* ");
                    sBuilder.append(currElement.getName() + "/ ");
                    sBuilder.append(dateFormat.format(currElement.getDateCreated()));
                }

                if (currElement instanceof File) {
                    sBuilder.append(currElement.getName() + "\n");
                }

                else {
                    throw new TypeNotPresentException("Directory or File", null);
                }
            }

            System.out.print(sBuilder);

        }

        

    }

    public void listContents(Directory dir) {
        System.out.println("Listing contents of " + dir.getFullPath() + ":");
        printHelper.printContents(dir);
    }

    public void sortDirectoryByDate(Directory dir) {

    }

    public String getCurrentDirectory() {

        return currentDirectory.getFullPath();
    }

    public Directory changeDirectory(String path) {
        // find the directory
        // if found, set currentDirectory to that directory
        // if not found, return null

        // start searching from root

        return null;
    }

    public Directory getRoot() {

        return root;
    }

}
