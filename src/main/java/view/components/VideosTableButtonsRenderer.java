package view.components;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author edisonarango
 */
public class VideosTableButtonsRenderer extends ButtonsPanel implements TableCellRenderer {
    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}

class ButtonsPanel extends JPanel {
    public List<JButton> buttons;
    
    protected ButtonsPanel() {
        super();
        JButton download = new JButton();
        initButton(download, "/icons/small/download.png");
        JButton refresh = new JButton();
        initButton(refresh, "/icons/small/refresh.png");
        JButton edit = new JButton();
        initButton(edit, "/icons/small/edit.png");
        JButton delete = new JButton();
        initButton(delete, "/icons/small/delete.png");
        buttons = Arrays.asList(download, refresh, edit, delete);
        setOpaque(true);
        for (JButton b: buttons) {
            b.setFocusable(false);
            b.setRolloverEnabled(false);
            add(b);
        }
    }
    
    private void initButton(JButton button, String iconPATH) {
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPATH))); // NOI18N
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }
}
