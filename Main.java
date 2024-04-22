import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        Scanner scanner = new Scanner(System.in);

        Integer option = 0;

        do {
            printMenu();

            try {
                option = scanner.nextInt();
            }

            catch (InputMismatchException e) {
                System.out.println("Invalid option");
                scanner.next(); // clear the faulty input
                continue;
            }

            switch (option) {
                case 1:
                    changeDirectoryHelper(fs, scanner);
                    break;
                case 2:
                    listContentsHelper(fs);
                    break;
                case 3:
                    createElementHelper(fs, scanner);
                    break;
                case 4:
                    deleteElementHelper(fs, scanner);
                    break;
                case 5:
                    moveElementHelper(fs, scanner);
                    break;
                case 6:
                    searchHelper(fs, scanner);
                    break;
                case 7:
                    printDirectoryTreeHelper(fs);
                    break;
                case 8:
                    sortContentsHelper(fs);
                    break;
                case 9:
                    System.out.println("Exiting program..");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        } while (!option.equals(9));

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== File System Management Menu =====");
        System.out.println("1. Change directory");
        System.out.println("2. List directory contents");
        System.out.println("3. Create file/directory");
        System.out.println("4. Delete file/directory");
        System.out.println("5. Move file/directory");
        System.out.println("6. Search file/directory");
        System.out.println("7. Print directory tree");
        System.out.println("8. Sort contents by date created");
        System.out.println("9. Exit");
        System.out.println("Please select an option:");
    }

    private static void changeDirectoryHelper(FileSystem fs, Scanner scanner) {
        String newPath = new String();

        Scanner newScanner = new Scanner(System.in);
        System.out.println("Current Directory: " + fs.getCurrentDirectory().getFullPath());
        System.out.println("Enter new directory path: ");

        
        newPath = newScanner.nextLine(); // gets empty string because 
        fs.changeDirectory(newPath);

        System.out.println("Directory changed to: " + fs.getCurrentDirectory().getFullPath());
    }

    private static void listContentsHelper(FileSystem fs) {
        fs.listContents(fs.getCurrentDirectory());
    }

    private static void createElementHelper(FileSystem fs, Scanner scana) {
        @SuppressWarnings("{resource}")
        String inputText;
        Scanner scan = new Scanner(System.in);
        System.out.println("Current directory: " + fs.getCurrentDirectory().getFullPath());
        System.out.print("Create file or directory (f/d)");

        inputText = scan.nextLine().trim().toLowerCase();

        if (inputText.equals("f")) {
            createFileHelper(fs, scan);
        }

        else if (inputText.equals("d")) {
            createDirectoryHelper(fs, scan);
        }

        else {
            System.out.println("Please try again! Input ` f ` for file and ` d ` for directory!");
            createElementHelper(fs, scan);
        }

    }

    private static void createFileHelper(FileSystem fs, Scanner scana) {
        String fileName;
        File newFile;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name for new file: ");

        fileName = scan.nextLine();

        newFile = fs.createFile(fileName);

        System.out.println("File created: " + newFile.getName());

    }

    private static void createDirectoryHelper(FileSystem fs, Scanner scana) {
        String dirName;
        Directory newDir;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter name for new directory: ");

        dirName = scan.nextLine();

        newDir = fs.createDirectory(dirName);

        System.out.println("Directory created: " + newDir.getName());
    }

    private static void deleteElementHelper(FileSystem fs, Scanner scana) {
        String option;

        Scanner scan = new Scanner(System.in);

        System.out.println("Current directory: " + fs.getCurrentDirectory().getFullPath());
        System.out.println("Delete file or directory (f/d): ");

        option = scan.nextLine().trim().toLowerCase();

        if (option.equals("f")) {
            deleteFileHelper(fs, scan);
        }

        else if (option.equals("d")) {
            deleteDirectoryHelper(fs, scan);
        } else {
            System.out.println("Please try again! Input ` f ` for file and ` d ` for directory!");
            deleteElementHelper(fs, scan);
        }


        System.out.println("Current directory: " + fs.getCurrentDirectory().getFullPath());
        System.out.print("Enter name of file/directory to delete: ");

    }

    private static void deleteFileHelper(FileSystem fs, Scanner scana) {
        String fileName;
        File deleted;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter name of file to delete: ");

        fileName = scan.nextLine().trim();

        deleted = fs.deleteFile(fileName, fs.getCurrentDirectory());

        System.out.println("File deleted: " + deleted.getName());

    }

    private static void deleteDirectoryHelper(FileSystem fs, Scanner scana) {
        String dirName;
        FileSystemElement deleted;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter name of file/directory to delete: ");

        dirName = scan.nextLine().trim();

        try {
            deleted = fs.deleteFile(dirName, fs.getCurrentDirectory());
            System.out.println("File deleted: " + deleted.getName());

        } catch (NoSuchElementException e) {
            deleted = fs.deleteDirectory(dirName, fs.getCurrentDirectory());
            System.out.println("Directory deleted: " + deleted.getName());
        }

    }

    private static void moveElementHelper(FileSystem fs, Scanner scana) {
        String elementName;
        String newPath;
        Scanner scan = new Scanner(System.in);

        System.out.println("Current directory: " + fs.getCurrentDirectory().getFullPath());

        System.out.print("Enter the name of file/directory to move: ");
        elementName = scan.nextLine().trim();

        System.out.print("Enter new directory path: ");
        newPath = scan.nextLine().trim();

        fs.moveElement(elementName, fs.getDirectory(newPath));

    }

    private static void searchHelper(FileSystem fs, Scanner scana) {
        //ask if user wants to search for file or directory
        String option;
        Scanner scan = new Scanner(System.in);

        System.out.println("Current directory: " + fs.getCurrentDirectory().getFullPath());
        System.out.print("Search for file or directory (f/d): ");

        option = scan.nextLine().trim().toLowerCase();

        if (option.equals("f")) {
            searchFileHelper(fs, scan);
        }

        else if (option.equals("d")) {
            searchDirectoryHelper(fs, scan);
        } else {
            System.out.println("Please try again! Input ` f ` for file and ` d ` for directory!");
            searchHelper(fs, scan);
        }
    }

    private static void searchFileHelper(FileSystem fs, Scanner scana) {
        String fileName;
        File foundFile;
        Scanner scan = new Scanner(System.in);

       System.out.print("Enter name of file to search: ");
        fileName = scan.nextLine().trim();

        foundFile = fs.searchFile(fileName);

        if (foundFile != null) {
            System.out.println("File found: " + foundFile.getParentElement().getFullPath() + "/" + foundFile.getName());
        } else {
            System.out.println("File not found");
        }
    }

    private static void searchDirectoryHelper(FileSystem fs, Scanner scana) {
        String dirName;
        Directory flag_foundDir;
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter name of directory to search: ");
        dirName = scan.nextLine().trim();

        flag_foundDir = fs.searchDirectory(dirName);

        if (flag_foundDir != null) {
            System.out.println("Directory found: " + flag_foundDir.getFullPath());
        } else {
            System.out.println("Directory not found");
        }
    }

    private static void printDirectoryTreeHelper(FileSystem fs) {
        fs.printDirectoryTree();
    }

    private static void sortContentsHelper(FileSystem fs) {
        fs.sortDirectoryByDate();
        
        System.out.println("Sorted contents of " + fs.getCurrentDirectory().getFullPath() + " by date created:");

        fs.printContentsWithDate();
    }

}
