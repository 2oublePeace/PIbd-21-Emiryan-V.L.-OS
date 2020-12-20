import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Catalog {
    String name;
    String path;
    int size;
    Catalog parentCatalog;
    ArrayList<Catalog> cats;
    FileList files;

    public Catalog(String path, Catalog parentCatalog) throws IOException {
        this.name = path.split("root")[1].substring(1);
        this.path = path;
        this.size = 0;
        cats = new ArrayList<>();
        files = new FileList();
    }

    public Catalog(String name, String path) throws IOException {
        this.name = name;
        this.path = path;
        this.size = 0;
        cats = new ArrayList<>();
        files = new FileList();
    }

    public Catalog(String name, String path, Catalog parentCatalog) throws IOException {
        this.name = name;
        this.path = path;
        this.parentCatalog = parentCatalog;
        this.size = 0;
        cats = new ArrayList<>();
        files = new FileList();
        Files.createDirectory(Path.of(path));
    }

    public void addFile(FileItem file) {
        files.add(file);
    }
    public void addCat(Catalog cat) {
        cats.add(cat);
    }

    public ArrayList<Catalog> getCats() {
        return cats;
    }

    public FileList getFiles() {
        return files;
    }

    public String getPath() {
        return path;
    }

    public String[] getItems() throws IOException {
        int index = 0;
        ArrayList<String> itemList = new ArrayList<>();
        Path currentCat = Path.of(path);
        DirectoryStream<Path> files = Files.newDirectoryStream(currentCat);
        for (Path path : files) {
            itemList.add(path.toString());
            index++;
        }
        String[] res = new String[itemList.size()];
        for(int i = 0; i < itemList.size(); i++) {
            res[i] = itemList.get(i).split(name)[1].substring(1);
        }
        return res;
    }
}
