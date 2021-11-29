import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
public class CustomCellRenderer extends DefaultTableCellRenderer {

    private boolean[][] generation;

    public CustomCellRenderer() {

    }

    public CustomCellRenderer(boolean[][] arr) {
        generation = arr;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        if (generation[row][col]) {
            l.setBackground(Color.BLACK);
        } else {
            l.setBackground(Color.WHITE);
        }

        //Return the JLabel which renders the cell.
        return l;
    }

}
