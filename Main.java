import java.util.InputMismatchException;
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
                    changeDirectoryHelper(scanner, fs);
                    break;
                case 2:
                    listContentsHelper(fs);
                    break;
                case 3:
                    createElementHelper(fs);
                    break;
                case 4:
                    deleteElementHelper(fs);
                    break;
                case 5:
                    moveElementHelper(fs);
                    break;
                case 6:
                    searchHelper(fs);
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

    private static void changeDirectoryHelper(Scanner scanner, FileSystem fs) {
        String newPath = null;
        fs.getCurrentDirectory();
        fs.changeDirectory(newPath);
        
        System.out.println("Current Directory: " + fs.getCurrentDirectory());
        System.out.println("Enter new directory path: ");

        newPath = scanner.next();

        



    }

    private static void listContentsHelper(FileSystem fs) {

    }

    private static void createElementHelper(FileSystem fs) {

    }

    private static void createFileHelper(FileSystem fs) {
    }

    private static void createDirectoryHelper(FileSystem fs) {

    }

    private static void deleteElementHelper(FileSystem fs) {

    }

    private static void deleteFileHelper(FileSystem fs) {

    }

    private static void deleteDirectoryHelper(FileSystem fs) {

    }

    private static void moveElementHelper(FileSystem fs) {

    }

    private static void searchHelper(FileSystem fs) {

    }

    private static void printDirectoryTreeHelper(FileSystem fs) {

    }

    private static void sortContentsHelper(FileSystem fs) {

    }

}
