import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

public class MainWindow {
    private JPanel panel ;
    private final JFrame frame = new JFrame();
    private int tableWidth = 500;
    private int tableHeight = 500;
    private int matrixSize = 50;
    private boolean[][] currentGeneration;
    private boolean[][] partOfGeneration1;
    private boolean[][] partOfGeneration2;
    private boolean[][] partOfGeneration3;
    private boolean[][] partOfGeneration4;

    public void generateMatrix(boolean randomMatrix) {
        for (Component comp : frame.getContentPane().getComponents()) {
            // remove old configuration
            if (comp instanceof JScrollPane) {
                frame.remove(comp);
            }
        }

        JTable table = new JTable() {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        DefaultTableModel defaultTableModel = (DefaultTableModel)table.getModel();
        defaultTableModel.setColumnCount(matrixSize);
        defaultTableModel.setRowCount(matrixSize);
        int total = matrixSize * matrixSize / 6;
        final int cellSize = 15;
        tableHeight = table.getRowCount() * cellSize + 65;
        tableWidth = table.getColumnCount() * cellSize + 18;
        if (tableHeight > 830) tableHeight = 800;
        if (tableWidth > 830) tableWidth = 800;
        table.setTableHeader(null);
        table.setRowHeight(cellSize);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(cellSize);
        }
        if (randomMatrix) {
            currentGeneration = new boolean[matrixSize][matrixSize];
            int currentRow, currentCol;
            for (int i = 0; i < total; i++) {
                currentRow = (int) (Math.random() * (matrixSize));
                currentCol = (int) (Math.random() * (matrixSize));
                if (!currentGeneration[currentRow][currentCol]) {
                    currentGeneration[currentRow][currentCol] = true;
                }
            }
        }
        table.setDefaultRenderer(Object.class, new CustomCellRenderer(currentGeneration));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);
        frame.setSize(tableWidth, tableHeight);
        frame.revalidate();
    }

    public void makeStartConfiguration() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Config");
        JMenuItem changeArrangement = new JMenuItem("Change configuration");
        changeArrangement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateMatrix(true);
            }
        });
        JMenuItem sizeChanger = new JMenuItem("Set size");
        sizeChanger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog();
                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;
                JPanel panel = new JPanel(new GridBagLayout());
                JSpinner spinner = new JSpinner();
                spinner.setModel(new SpinnerNumberModel(10, 10, 5000, 50));
                JFormattedTextField txt = ((JSpinner.NumberEditor) spinner.getEditor()).getTextField();
                ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);
                JButton confirmButton = new JButton("OK");
                confirmButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        matrixSize = (int)spinner.getValue();
                        generateMatrix(true);
                        dialog.dispose();
                    }
                });
                JLabel label = new JLabel();
                label.setText("Enter matrix size [10, 5000]");
                c.gridx = 0;
                c.gridy = 0;
                panel.add(label, c);
                c.gridy = 1;
                panel.add(spinner, c);
                c.gridy = 2;
                panel.add(confirmButton, c);
                dialog.add(panel);
                dialog.setModal(true);
                dialog.setSize(new Dimension(180, 100));
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
            }
        });
        JMenuItem startButton = new JMenuItem("GO!");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startComputing();
                } catch (Exception ex ) {
                    ex.printStackTrace();
                }
            }
        });

        menu.add(changeArrangement);
        menu.addSeparator();
        menu.add(sizeChanger);
        menu.addSeparator();
        menu.add(startButton);
        menuBar.add(menu);
        generateMatrix(true);   // draw default matrix
        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void startComputing() throws Exception {
        String name = "rmi://localhost/Compute";  // server 1
        Compute comp = (Compute) Naming.lookup(name);
        String name2 = "rmi://localhost/Compute2";  // server 2
        Compute comp2 = (Compute) Naming.lookup(name2);
        String name3 = "rmi://localhost/Compute3";  // server 3
        Compute comp3 = (Compute) Naming.lookup(name3);
        String name4 = "rmi://localhost/Compute4";  // server 3
        Compute comp4 = (Compute) Naming.lookup(name4);
        int rowsForFirst = matrixSize / 4;
        int rowsForSecond = matrixSize / 4;
        int rowsForThird = matrixSize / 4;
        int rowsForFourth = matrixSize / 4 + matrixSize % 4;
        partOfGeneration1 = new boolean[rowsForFirst][matrixSize];
        for (int i = 0; i < rowsForFirst; i++) {
            for (int j = 0; j < matrixSize; j++) {
                partOfGeneration1[i][j] = currentGeneration[i][j];
            }
        }
        partOfGeneration2 = new boolean[rowsForSecond][matrixSize];
        for (int i = 0; i < rowsForSecond; i++) {
            for (int j = 0; j < matrixSize; j++) {
                partOfGeneration2[i][j] = currentGeneration[i + rowsForFirst][j];
            }
        }

        partOfGeneration3 = new boolean[rowsForThird][matrixSize];
        for (int i = 0; i < rowsForThird; i++) {
            for (int j = 0; j < matrixSize; j++) {
                partOfGeneration3[i][j] = currentGeneration[i + rowsForFirst + rowsForSecond][j];
            }
        }

        partOfGeneration4 = new boolean[rowsForFourth][matrixSize];
        for (int i = 0; i < rowsForFourth; i++) {
            for (int j = 0; j < matrixSize; j++) {
                partOfGeneration4[i][j] = currentGeneration[i + rowsForFirst + rowsForSecond + rowsForThird][j];
            }
        }

        for (int k =0 ; k < 1; k++) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                       partOfGeneration1 = comp.defineNextGeneration(currentGeneration, partOfGeneration1, 0, rowsForFirst, matrixSize);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Thread thread2 = new Thread() {
                public void run() {
                    try {
                        partOfGeneration2 = comp2.defineNextGeneration(currentGeneration, partOfGeneration2, rowsForFirst, rowsForSecond, matrixSize);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thread3 = new Thread() {
                public void run() {
                    try {
                        partOfGeneration3 = comp3.defineNextGeneration(currentGeneration, partOfGeneration3, rowsForFirst + rowsForSecond, rowsForThird, matrixSize);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread thread4 = new Thread() {
                public void run() {
                    try {
                        partOfGeneration4 = comp4.defineNextGeneration(currentGeneration, partOfGeneration4, rowsForFirst + rowsForSecond + rowsForThird, rowsForFourth, matrixSize);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };


            long startTime = System.nanoTime();
            thread.start();
            thread2.start();
            thread3.start();
            thread4.start();
            thread.join();
            thread2.join();
            thread3.join();
            thread4.join();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;  //divide by 1000000 to get milliseconds.
            //System.out.println(duration);
            for (int i = 0; i < rowsForFirst; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    currentGeneration[i][j] = partOfGeneration1[i][j];
                }
            }
            for (int i = 0; i < rowsForSecond; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    currentGeneration[i + rowsForFirst][j] = partOfGeneration2[i][j];
                }
            }

            for (int i = 0; i < rowsForThird; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    currentGeneration[i + rowsForFirst + rowsForSecond][j] = partOfGeneration3[i][j];
                }
            }

            for (int i = 0; i < rowsForFourth; i++) {
                for (int j = 0; j < matrixSize; j++) {
                    currentGeneration[i + rowsForFirst + rowsForSecond + rowsForThird][j] = partOfGeneration4[i][j];
                }
            }
            generateMatrix(false);


        }
    }


    public static void main(String[] args) {

    }
}
