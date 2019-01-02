package view.components;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Objects;
import javax.swing.AbstractAction;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;


class DownloadAction extends AbstractAction {
    private final JTable table;
    protected DownloadAction(JTable table, Icon icon) {
        super("", icon);
        this.table = table;
    }
    @Override 
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        ((VideosTableModel)table.getModel()).downloadVideoCaptions(row);
    }
}

class RefreshAction extends AbstractAction {
    private final JTable table;
    protected RefreshAction(JTable table, Icon icon) {
        super("", icon);
        this.table = table;
    }
    @Override 
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        ((VideosTableModel)table.getModel()).refreshVideo(row);
    }
}

class EditAction extends AbstractAction {
    private final JTable table;
    protected EditAction(JTable table, Icon icon) {
        super("", icon);
        this.table = table;
    }
    @Override 
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        ((VideosTableModel)table.getModel()).editVideo(row);
    }
}

class DeleteAction extends AbstractAction {
    private final JTable table;
    protected DeleteAction(JTable table, Icon icon) {
        super("", icon);
        this.table = table;
    }
    @Override 
    public void actionPerformed(ActionEvent e) {
        int row = table.convertRowIndexToModel(table.getEditingRow());
        ((VideosTableModel)table.getModel()).deleteVideo(row);
    }
}

public class VideosTableButtonsEditor extends ButtonsPanel implements TableCellEditor {
    protected transient ChangeEvent changeEvent;
    protected final JTable table;
    
    private class EditingStopHandler extends MouseAdapter implements ActionListener {       
        @Override 
        public void mousePressed(MouseEvent e) {
            Object o = e.getSource();
            if (o instanceof TableCellEditor) {
                actionPerformed(null);
            } else if (o instanceof JButton) {
                //DEBUG: view button click -> control key down + edit button(same cell) press -> remain selection color
                ButtonModel m = ((JButton) e.getComponent()).getModel();
                if (m.isPressed() && table.isRowSelected(table.getEditingRow()) && e.isControlDown()) {
                    setBackground(table.getBackground());
                }
            }
        }
        @Override public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() -> fireEditingStopped());
        }
    }
    
    public VideosTableButtonsEditor(JTable table) {
        super();
        this.table = table;
        JButton buttonDownload = buttons.get(0);
        buttonDownload.setAction(new DownloadAction(table, buttonDownload.getIcon()));
        JButton buttonRefresh = buttons.get(1);
        buttonRefresh.setAction(new RefreshAction(table,buttonRefresh.getIcon()));
        JButton buttonEdit = buttons.get(2);
        buttonEdit.setAction(new EditAction(table,buttonEdit.getIcon()));
        JButton buttonDelete = buttons.get(3);
        buttonDelete.setAction(new DeleteAction(table,buttonDelete.getIcon()));

        EditingStopHandler handler = new EditingStopHandler();
        for (JButton b: buttons) {
            b.addMouseListener(handler);
            b.addActionListener(handler);
        }
        addMouseListener(handler);
    }
    @Override public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.setBackground(table.getSelectionBackground());
        return this;
    }
    @Override public Object getCellEditorValue() {
        return "";
    }

    //Copied from AbstractCellEditor
    //protected EventListenerList listenerList = new EventListenerList();
    //protected transient ChangeEvent changeEvent;
    @Override public boolean isCellEditable(EventObject e) {
        return true;
    }
    @Override public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }
    @Override public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }
    @Override public void cancelCellEditing() {
        fireEditingCanceled();
    }
    @Override public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }
    @Override public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }
    public CellEditorListener[] getCellEditorListeners() {
        return listenerList.getListeners(CellEditorListener.class);
    }
    protected void fireEditingStopped() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                // Lazily create the event:
                if (Objects.isNull(changeEvent)) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listeners[i + 1]).editingStopped(changeEvent);
            }
        }
    }
    protected void fireEditingCanceled() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                // Lazily create the event:
                if (Objects.isNull(changeEvent)) {
                    changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listeners[i + 1]).editingCanceled(changeEvent);
            }
        }
    }
}
