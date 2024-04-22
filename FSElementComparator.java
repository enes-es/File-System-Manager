import java.util.Comparator;

public interface FSElementComparator {
    static int compareDate(FileSystemElement object1, FileSystemElement object2) {
        return object1.getDateCreated().compareTo(object2.getDateCreated());
    }

    static int comparaName(FileSystemElement object1, FileSystemElement object2) {
        return object1.getName().compareTo(object2.getName());
    }


}
