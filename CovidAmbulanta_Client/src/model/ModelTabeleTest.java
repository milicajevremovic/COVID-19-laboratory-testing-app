/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import kontroler.Kontroler;
import domen.Test;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milica
 */
public class ModelTabeleTest extends AbstractTableModel{
    private List<Test> list;
    private String[] header = {"Naziv i opis", "Uputstvo za primenu","Vrsta","Oznaka"};

    public ModelTabeleTest(List<Test> list) {
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
        Test test = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return test.getNaziv()+" "+test.getOpis();
            case 1:
                return test.getUputstvoZaPrimenu();
            case 2:
                return test.getVrsta().toString();
            case 3:
                return test.getOznaka();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int i) {
        return header[i];
    }


    public List<Test> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Test> list) {
        this.list = list;
    }
}
