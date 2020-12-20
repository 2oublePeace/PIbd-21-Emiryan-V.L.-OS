import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Core {
    private final static int COLS_NUMBERS = 32;
    private final static int ROWS_NUMBERS = 8;
    private final static int CELL_WIDTH = 15;
    private final static int CELL_HEIGHT = 15;
    private int X = 15;
    private int Y = 5;

    String separator = "\\";
    Catalog root = new Catalog("root", "D:\\root");
    Catalog node = root;
    String currentCat = root.getPath();
    int DISK_SIZE = 256;
    int[][] sectorMap = new int[ROWS_NUMBERS][COLS_NUMBERS];
    HashMap<String, Integer> copyTable = new HashMap<>();
    ArrayList<Catalog> catList = new ArrayList<>();

    public Core() throws IOException {
        initSectorMap();
        initFiles();
        loadSectorMap();
    }

    public void createFile(String fileName) throws IOException {
        if(node.getPath() == currentCat && !(Files.exists(Path.of(currentCat + separator + fileName)))) {
            node.addFile(new FileItem(fileName, currentCat + separator + fileName));
        }
    }

    public void createFolder(String catName) throws IOException {
        node.addCat(new Catalog(catName, currentCat + separator + catName, node));
    }

    public void copyFile(String fileName) throws IOException {
        if(copyTable.containsKey(fileName)) {
            int count = copyTable.get(fileName);
            copyTable.replace(fileName, ++count);
        } else {
            copyTable.put(fileName, 1);
        }

        String withoutExten = fileName.split("\\.")[0];
        String exten = fileName.split("\\.")[1];
        Path path1 = Path.of(currentCat + separator + fileName);
        Path path2 = Path.of(currentCat + separator + withoutExten + "(" + copyTable.get(fileName).toString() + ")" + "." + exten);
        Files.copy(path1, path2);
    }

    public void replaceFile(String fileName, String path) throws IOException {
        Path path1 = Path.of(currentCat + separator + fileName);
        Path path2 = Path.of(path + separator + fileName);
        Files.move(path1, path2);
    }

    public void removeFile(String fileName) throws IOException {
        Path path = Path.of(currentCat + separator + fileName);
        Files.deleteIfExists(path);
    }

    private void initSectorMap() {
        for(int i = 0; i < ROWS_NUMBERS; i++) {
            for(int j = 0; j < COLS_NUMBERS; j++) {
                sectorMap[i][j] = 0;
            }
        }
    }

    public void drawMonitor(Graphics g) {
        for(int i = 0; i < ROWS_NUMBERS; i++) {
            for(int j = 0; j < COLS_NUMBERS; j++) {
                if(sectorMap[i][j] == 0) {
                    g.setColor(Color.gray);
                    g.fillRect(X + CELL_WIDTH * j, (Y + (i * CELL_HEIGHT)), CELL_WIDTH, CELL_HEIGHT);
                } else if(sectorMap[i][j] == 1) {
                    g.setColor(Color.blue);
                    g.fillRect(X + CELL_WIDTH * j, (Y + (i * CELL_HEIGHT)), CELL_WIDTH, CELL_HEIGHT);
                }
                g.setColor(Color.BLACK);
                g.drawLine(X + CELL_WIDTH * j, (Y + (i * CELL_HEIGHT)), X + CELL_WIDTH * (j + 1), (Y + (i * CELL_HEIGHT)));
                g.drawLine(X + CELL_WIDTH * j, (Y + (i * CELL_HEIGHT)) + CELL_HEIGHT, X + CELL_WIDTH * (j + 1), (Y + (i * CELL_HEIGHT)) + CELL_HEIGHT);
                g.drawLine(X + CELL_WIDTH * j, (Y + (i * CELL_HEIGHT)), X + CELL_WIDTH * j, (Y + (i * CELL_HEIGHT)) + CELL_HEIGHT);
                g.drawLine(X + CELL_WIDTH * (j + 1), (Y + (i * CELL_HEIGHT)), X + CELL_WIDTH * (j + 1), (Y + (i * CELL_HEIGHT)) + CELL_HEIGHT);
            }
        }
    }

    public String getCurrentCat() {
        return currentCat;
    }

    public void setCurrentCat(String currentCat) throws IOException {
        Catalog temp;
        for(int i = 0; i < node.getCats().size(); i++) {
            temp = node.getCats().get(i);
            if(temp.getPath().equals(currentCat)) {
                node = temp;
                return;
            }
        }
    }

    public void goHome() throws IOException {
        node = root;
    }

    public String[] getItemList() throws IOException {
        return node.getItems();
    }

    public DefaultListModel getModel() throws IOException {
        DefaultListModel model = new DefaultListModel();
        String[] temp = getItemList();
        for(int i = 0; i < getItemList().length; i++) {
            model.addElement(temp[i]);
        }
        return model;
    }

    public void initFiles() throws IOException {
        int index = 0;
        Path currCat = Path.of(getCurrentCat());
        DirectoryStream<Path> files = Files.newDirectoryStream(currCat);
        for (Path path : files) {
            if(Files.isDirectory(path)) {
                node.addCat(new Catalog(path.toString(), node));
            } else {
                node.addFile(new FileItem(path.toString()));
            }
        }
    }

    public int[][] getSectorMap() {
        return sectorMap;
    }

    public static int getColsNumbers() {
        return COLS_NUMBERS;
    }

    public static int getRowsNumbers() {
        return ROWS_NUMBERS;
    }

    public void loadSectorMap() {
        FileList files = node.getFiles();
        FileItem file = files.poll();
        LinkedList<Integer> temp = file.getSectorList();
        for(int i = 0; i < ROWS_NUMBERS; i++) {
            for(int j = 0; j < COLS_NUMBERS; j++) {
                if(file.isFull()) {
                    if(files.size() > 0) {
                        file = files.poll();
                    } else {
                        return;
                    }
                }
                if(sectorMap[i][j] == 0) {
                    file.setSector(i * COLS_NUMBERS + j);
                    sectorMap[i][j] = 1;
                }
            }
        }
    }

    public void saveSectorMap(String str) {
        str = currentCat + separator + str;
        int compare = 0;
        int fileIndex = 0;
        int byteIndex = 0;
        FileList files = node.getFiles();
        FileItem file = files.poll();
        LinkedList<Integer> sectorList = file.getSectorList();
        for(int i = 0; i < files.size(); i++) {
            if(file.isFull()) {
                if(files.size() > 0) {
                    file = files.poll();
                } else {
                    return;
                }
            }
            if (file.getPath().equals(str)) {
                for(int j = 0; j < file.getSectorList().size(); j++) {
                    int index = file.getSectorList().get(j);
                    int jndex = (file.getSectorList().get(j) / 32)  * 32 + index;
                    sectorMap[index/32][jndex] = 0;
                }
            }
        }
    }
}
