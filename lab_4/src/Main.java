import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class Main {

    private JFrame frame;
    private JTextField addField;
    private JTextField addFolderField;
    private JTextField replaceField;
    private Core core;
    int selected = 0;
    private String splitSeparator = "root";
    private String pathSeparator = "\\";


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main window = new Main();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() throws IOException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() throws IOException {
        frame = new JFrame();
        frame.setBounds(100, 100, 547, 578);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        core = new Core();
        JPanel panel = new MonitorPanel(core);
        panel.setBounds(10, 11, 511, 128);
        frame.getContentPane().add(panel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(294, 153, 227, 375);
        frame.getContentPane().add(scrollPane);

        JList list = new JList(core.getItemList());
        scrollPane.setViewportView(list);

        JButton removeFileButton = new JButton("Remove");
        removeFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String temp = core.getItemList()[selected];
                    core.saveSectorMap(temp);
                    panel.repaint();
                    core.removeFile(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    list.setModel(core.getModel());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        removeFileButton.setBounds(10, 184, 104, 23);
        frame.getContentPane().add(removeFileButton);

        JButton addFileButton = new JButton("Add file");
        addFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    core.createFile(addField.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    list.setModel(core.getModel());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        addFileButton.setBounds(10, 150, 104, 23);
        frame.getContentPane().add(addFileButton);

        JButton replaceFileButton = new JButton("Replace file");
        replaceFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String temp = core.getItemList()[selected];
                    core.saveSectorMap(temp);
                    panel.repaint();
                    core.replaceFile(temp, replaceField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    list.setModel(core.getModel());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        replaceFileButton.setBounds(10, 218, 104, 23);
        frame.getContentPane().add(replaceFileButton);

        JButton copyFileButton = new JButton("Copy file");
        copyFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String temp = core.getItemList()[selected];
                    core.loadSectorMap();
                    panel.repaint();
                    core.copyFile(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    list.setModel(core.getModel());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        copyFileButton.setBounds(10, 252, 104, 23);
        frame.getContentPane().add(copyFileButton);

        JButton addFolderButton = new JButton("Add folder");
        addFolderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    core.createFolder(addFolderField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    list.setModel(core.getModel());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        addFolderButton.setBounds(10, 286, 104, 23);
        frame.getContentPane().add(addFolderButton);

        JButton atHomeButton = new JButton("At home");
        addFolderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    core.goHome();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    list.setModel(core.getModel());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        atHomeButton.setBounds(10, 319, 104, 23);
        frame.getContentPane().add(atHomeButton);

        addField = new JTextField();
        addField.setBounds(121, 151, 163, 20);
        frame.getContentPane().add(addField);
        addField.setColumns(10);

        replaceField = new JTextField();
        replaceField.setColumns(10);
        replaceField.setBounds(121, 219, 163, 20);
        frame.getContentPane().add(replaceField);

        addFolderField = new JTextField();
        addFolderField.setBounds(121, 287, 163, 20);
        frame.getContentPane().add(addFolderField);
        addFolderField.setColumns(10);

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    String temp = core.getCurrentCat() + pathSeparator + core.getItemList()[list.locationToIndex(e.getPoint())];
                    if (e.getClickCount() == 2 && Files.isDirectory(Path.of(temp))) {
                        try {
                            core.setCurrentCat(core.getCurrentCat() + pathSeparator + core.getItemList()[list.locationToIndex(e.getPoint())]);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    if (e.getClickCount() == 1) {
                        selected = list.locationToIndex(e.getPoint());
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    list.setModel(core.getModel());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}