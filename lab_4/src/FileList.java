import java.io.IOException;

public class FileList {
    FileItem root = null;
    FileItem next;
    int count = 0;

    public FileList() throws IOException {
        root = new FileItem();
        next = null;
    }

    public void add(FileItem newfileItem) {
        FileItem tempItem = root.getNext();
        root.setNext(newfileItem);
        newfileItem.setNext(tempItem);
        count++;
    }

    public FileItem poll() {
        FileItem tempItem = root.getNext();
        root.setNext(tempItem.getNext());
        count--;
        return tempItem;
    }

    public int size() {
        return count;
    }
}
