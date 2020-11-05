/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import kontroler.Kontroler;
import domen.Laborant;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milica
 */
public class ModelTabeleLaborant extends AbstractTableModel{
    private List<Laborant> list;
    private String[] header = {"Ime", "Prezime","Broj dosijea","Broj ordinacije","Test"};

    public ModelTabeleLaborant(List<Laborant> list) {
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
        Laborant l = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return l.getIme();
            case 1:
                return l.getPrezime();
            case 2:
                return l.getBrojDosijea();
            case 3:
                return l.getBrojOrdinacije()+" ";
            case 4:
                return l.getTest();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int i) {
        return header[i];
    }


    public List<Laborant> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Laborant> list) {
        this.list = list;
    }
}
