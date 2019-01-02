package view.components;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class VideosTableURLRenderer extends DefaultTableCellRenderer implements MouseListener, MouseMotionListener {
    
    private int row = -1;
    private int col = -1;
    private boolean isRollover;
    
    @Override 
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, false, row, column);
        String str = Objects.toString(value, "");

        if (isRolloverCell(table, row, column)) {
            setText("<html><u><font color='#1A237E'>" + str);
        } else if (hasFocus) {
            setText("<html><u><font color='#1A237E'>" + str);
        } else {
            setText("<html><u><font color='blue'>" + str);
        }
        return this;
    }
    
    protected boolean isRolloverCell(JTable table, int row, int column) {
        return !table.isEditing() && this.row == row && this.col == column && this.isRollover;
    }
    
    private static boolean isURLColumn(JTable table, int column) {
        return column >= 0 && table.getColumnClass(column).equals(NamedURL.class);
    }
    
    @Override 
    public void mouseMoved(MouseEvent e) {
        JTable table = (JTable) e.getComponent();
        Point pt = e.getPoint();
        int prevRow = row;
        int prevCol = col;
        boolean prevRollover = isRollover;
        row = table.rowAtPoint(pt);
        col = table.columnAtPoint(pt);
        if (col == 2 || col == 4) {
            table.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
        isRollover = isURLColumn(table, col); // && pointInsidePrefSize(table, pt);
        if (row == prevRow && col == prevCol && isRollover == prevRollover) {
            return;
        }
        if (!isRollover && !prevRollover) {
            return;
        }
        Rectangle repaintRect;
        if (isRollover) {
            Rectangle r = table.getCellRect(row, col, false);
            repaintRect = prevRollover ? r.union(table.getCellRect(prevRow, prevCol, false)) : r;
            table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else { //if (prevRollover) {
            repaintRect = table.getCellRect(prevRow, prevCol, false);
            table.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));          
        }
        table.repaint(repaintRect);

    }
    @Override 
    public void mouseExited(MouseEvent e) {
        JTable table = (JTable) e.getComponent();
        if (isURLColumn(table, col)) {
            table.repaint(table.getCellRect(row, col, false));
            row = -1;
            col = -1;
            isRollover = false;
        }
    }
    @Override 
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getComponent();
        Point pt = e.getPoint();
        int ccol = table.columnAtPoint(pt);
        if (isURLColumn(table, ccol)) { // && pointInsidePrefSize(table, pt)) {
            int crow = table.rowAtPoint(pt);
            NamedURL url = (NamedURL) table.getValueAt(crow, ccol);
            try {
                if (Desktop.isDesktopSupported()) { // JDK 1.6.0
                    Desktop.getDesktop().browse(url.toURI());
                }
            } catch (URISyntaxException | IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    @Override public void mouseDragged(MouseEvent e)  { /* not needed */ }
    @Override public void mouseEntered(MouseEvent e)  { /* not needed */ }
    @Override public void mousePressed(MouseEvent e)  { /* not needed */ }
    @Override public void mouseReleased(MouseEvent e) { /* not needed */ }
}
