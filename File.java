public class File extends FileSystemElement{
    
    public File(String name, FileSystemElement parent) {
        super(name, parent);
    }

    @Override
    public void print(String prefix) {
        System.out.println(prefix + "File: " + getName());
    }
}
