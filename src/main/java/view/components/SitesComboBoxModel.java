package view.components;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import model.Site;

/**
 *
 * @author edisonarango
 */
public class SitesComboBoxModel extends AbstractListModel implements ComboBoxModel {
    
    private ArrayList<Site> sites;
    private Site selected;
    
    public SitesComboBoxModel(ArrayList<Site> sites){
        this.sites = sites;
        setSelectedToFirstItem();
    }
    
    public void setSelectedToFirstItem(){
        if (sites.size() > 0) {
            selected = sites.get(0);
        }
        else {
            selected = null;
        }
    }

    @Override
    public int getSize() {
        return sites.size();
    }

    @Override
    public Object getElementAt(int index) {
        return sites.get(index);
    }

    @Override
    public void setSelectedItem(Object item) {
        selected = (Site)item;
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }
    
}
