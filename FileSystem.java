import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
//stream library

import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.stream.IntStream;

public class FileSystem {
    private Directory root;
    private Directory currentDirectory;

    public FileSystem() {
        this.root = new Directory("root", null);
        this.currentDirectory = root;

        Directory home = new Directory("home", root);
        Directory user = new Directory("user", home);
        Directory Documents = new Directory("Documents", user);
        Directory projects = new Directory("Projects", Documents);
        Directory project1 = new Directory("Project1", projects);
        Directory project2 = new Directory("Project2", projects);
        File report = new File("Report.docx", project2);
        File notes = new File("Notes.txt", project2);

        root.addElement(home);
        home.addElement(user);
        user.addElement(Documents);

        project2.addElement(report);
        project2.addElement(notes);

    }

    public File createFile(String name) {
        return createFile(name, getCurrentDirectory());
    }

    public Directory createDirectory(String name) {
        return createDirectory(name, getCurrentDirectory());
    }

    public File createFile(String name, Directory parent) {

        File newFile = new File(name, parent);
        parent.addElement(newFile);
        return newFile;
        // IMPROVEMENT: check for duplicates and throw if there is duplicate!
    }

    public Directory createDirectory(String name, Directory parent) {
        Directory newDir = new Directory(name, parent);
        parent.addElement(newDir);
        return newDir;
    }

    public File deleteFile(String name, Directory parent) {
        File toDelete;
        FileSystemElement searchResult;

        // find file here
        searchResult = returnElement(name, parent);

        if (searchResult == null || !(searchResult instanceof File)) {
            throw new NoSuchElementException(
                    "Can't find the file named" + name + " inside the path: " + parent.getFullPath());
        }

        toDelete = (File) searchResult;

        parent.removeElement(toDelete);

        return toDelete;
    }

    public Directory deleteDirectory(String name, Directory parent) {
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

        return toDelete;

    }

    public void deleteDirectoryRecursive(Directory directory) {
        // Delete all files and subdirectories recursively
        for (FileSystemElement element : directory.getChildren()) {
            if (element instanceof File) {
                directory.removeElement(element);
            } else if (element instanceof Directory) {
                deleteDirectoryRecursive((Directory) element);
            }
        }
        // Remove the directory itself from its parent
        Directory parent = (Directory) directory.getParentElement();
        parent.removeElement(directory);
    }

    public void moveElement(String name, Directory newParent) {
        FileSystemElement target;

        // find element here
        target = returnElement(name, currentDirectory);

        if (target == null) {
            throw new NoSuchElementException(
                    "Can't find the File/Directory named" + name + " inside the current path: "
                            + currentDirectory.getFullPath());
        }

        // remove from current parent
        ((Directory) target.getParentElement()).removeElement(target);

        // add to new parent
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
        // searches recursively starting from root.
        if (findDirectoryRecursive(name, root) != null)
            return true;
        else
            return false;

    }

    public ArrayList<Directory> getAllDirectories() {
        ArrayList<Directory> allDirectories = new ArrayList<Directory>(100);

        allDirectories.add(root);
        allDirectories.addAll(root.getSubDirectories());

        return allDirectories;
    }

    private Directory findDirectoryRecursive(String name, Directory directoryToSearch) {
        FileSystemElement element;

        element = returnElement(name, directoryToSearch);

        for (Directory subDirectory : directoryToSearch.getInclusiveSubDirectories()) {
            if (findDirectoryRecursive(name, subDirectory) != null) {
                return (Directory) subDirectory.getChildDirectory(name);
            }

            else {
                continue;
            }
        }

        return null;

    }

    private File findFileRecursive(String name, Directory directoryToSearch) {
        FileSystemElement element;

        element = returnElement(name, directoryToSearch);

        if (element != null && element instanceof File) {
            return (File) element;
        }

        for (Directory subDirectory : directoryToSearch.getInclusiveSubDirectories()) {
            File file = findFileRecursive(name, subDirectory);

            if (file != null && file instanceof File) {
                return file;
            }

            else {
                continue;
            }
        }

        // couldn't find it.
        return null;
    }

    public boolean searchFile(String name) {
        if (findFileRecursive(name, root) != null)
            return true;

        else
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

    public void printContents() {
        printHelper.printContents(currentDirectory);
    }

    public void printContents(Directory dir) {
        printHelper.printContents(dir);
    }

    public void printContentsWithDate() {
        printHelper.printContentsWithDate(currentDirectory);
    }

    public void printContentsWithDate(Directory dir) {
        printHelper.printContents(dir);
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

                else if (currElement instanceof File) {
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

    public void sortDirectoryByDate() {
        sortDirectoryByDate(getCurrentDirectory());
    }

    public void sortDirectoryByName() {
        sortDirectoryByName(getCurrentDirectory());
    }

    public void sortDirectoryByDate(Directory dir) {
        Comparator<FileSystemElement> compareByDate = Comparator.comparing(c -> c.getDateCreated());
        dir.getChildren().sort(compareByDate);
        printHelper.printContentsWithDate(dir);
    }

    public void sortDirectoryByName(Directory dir) {
        Comparator<FileSystemElement> compareByName = Comparator.comparing(c -> c.getName());

        dir.getChildren().sort(compareByName);

        printHelper.printContentsWithDate(dir);
    }

    public Directory getCurrentDirectory() {

        return currentDirectory;
    }

    public Directory getDirectory(String path) {

        ArrayList<String> directories = new ArrayList<>();

        directories.addAll(Arrays.asList(path.split("/")));

        // start searching from root
        Directory current = root;

        for (String directory : directories) {
            current = current.getChildDirectory(directory);
        }

        return current;
    }

    public Directory changeDirectory(String path) {
        // find the directory
        // if found, set currentDirectory to that directory
        // if not found, return null

        // path is a string of directories separated by '/'
        // split the string by '/'
        // for each directory in the split string, search for the directory in the
        // current directory

        ArrayList<String> directories = new ArrayList<>();

        // don't add first one
        directories.addAll(Arrays.asList(path.split("/")));
        //remove empty strings
        directories.removeIf(String::isEmpty);

        Directory current = root;

        for (String directory : directories) {
            current = changeToChildDirectory(directory, current);
        }


        return current;
    }

    public Directory changeDirectory(Directory dir) {
        currentDirectory = dir;
        return dir;
    }

    public Directory changeToChildDirectory(String dirName) {
        return changeToChildDirectory(dirName, currentDirectory);
    }

    public Directory changeToChildDirectory(String dirName, Directory parentDirectory) {

        Directory newDirectory;

        newDirectory = parentDirectory.getChildDirectory(dirName);

        if (newDirectory == null)
            throw new NoSuchElementException("No such child directory called: " + dirName + " under the directory: "
                    + currentDirectory.getFullPath());
        else {
            currentDirectory = newDirectory;
            return newDirectory;

        }

    }

    public Directory getRoot() {

        return root;
    }

}
