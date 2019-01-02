package view.components;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;
import model.Account;

/**
 *
 * @author edisonarango
 */
public class AccountsComboBoxModel extends AbstractListModel implements MutableComboBoxModel{
    
    private ArrayList<Account> accounts;
    private Account selected;
    
    public AccountsComboBoxModel(ArrayList<Account> accounts){
        this.accounts = accounts;
        setSelectedToFirstItem();
    }
    
    public AccountsComboBoxModel(AccountsComboBoxModel model){
        this.accounts = (ArrayList<Account>)model.accounts.clone();
        this.selected = model.selected;
    }
    
    public void setSelectedToFirstItem(){
        if (accounts.size() > 0) {
            selected = accounts.get(0);
        }
        else {
            selected = null;
        }
    }

    @Override
    public int getSize() {
        return accounts.size();
    }

    @Override
    public Object getElementAt(int index) {
        return accounts.get(index);
    }

    @Override
    public void setSelectedItem(Object item) {
        selected = (Account)item;
    }

    @Override
    public Object getSelectedItem() {
        return selected;
    }

    @Override
    public void addElement(Object item) {
        accounts.add((Account)item);
    }

    @Override
    public void removeElement(Object obj) {
        accounts.remove((Account)obj);
    }

    @Override
    public void insertElementAt(Object item, int index) {
        accounts.add(index, (Account)item);
    }

    @Override
    public void removeElementAt(int index) {
        accounts.remove(index);
    }
    
}
