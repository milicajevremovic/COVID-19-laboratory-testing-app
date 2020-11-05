/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import kontroler.Kontroler;
import domen.Administrator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milica
 */
public class ModelTabeleAdministrator extends AbstractTableModel{
    private List<Administrator> list;
    private String[] header = {"Username", "Ime","Prezime"};

    public ModelTabeleAdministrator(List<Administrator> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Administrator a = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return a.getUsername();
            case 1:
                return a.getIme();
            case 2:
                return a.getPrezime();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int i) {
        return header[i];
    }


    public List<Administrator> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Administrator> list) {
        this.list = list;
    }
}
