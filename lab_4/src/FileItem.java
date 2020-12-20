import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class FileItem {
    FileItem next;
    String name;
    String path;
    int size;
    int copyCount;
    LinkedList<Integer> sectorList;

    public FileItem() {
        next = null;
    }

    public FileItem(String name, String path) throws IOException {
        this.name = name;
        this.path = path;
        Files.createFile(Path.of(path));
        copyCount = 1;
        sectorList = new LinkedList<>();
        next = null;
    }

    public FileItem(String path) throws IOException {
        this.name = name;
        this.path = path;
        copyCount = 1;
        size = (int) Files.size(Path.of(path));
        sectorList = new LinkedList<>();
        next = null;
    }

    public void incCopyCount() {
        copyCount++;
    }

    public String getCopyCount() {
        return Integer.toString(copyCount);
    }

    public void setSector(int digit) {
        this.sectorList.add(digit);
    }

    public LinkedList<Integer> getSectorList() {
        return sectorList;
    }

    public String getPath() {
        return path;
    }

    public boolean isFull() {
        if(size == sectorList.size()) {
            return true;
        }
        return false;
    }

    public void setNext(FileItem next) {
        this.next = next;
    }

    public FileItem getNext() {
        return next;
    }
}
