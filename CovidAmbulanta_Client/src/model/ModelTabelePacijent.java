/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import kontroler.Kontroler;
import domen.Pacijent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milica
 */
public class ModelTabelePacijent extends AbstractTableModel{
    private List<Pacijent> list;
    private String[] header = {"Ime i prezime", "Datum rodjenja","Telefon","Email","Laborant"};

    public ModelTabelePacijent(List<Pacijent> list) {
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
        Pacijent p = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getIme()+" "+p.getPrezime();
            case 1:
                return Kontroler.getInstance().getSdf().format(p.getDatumRodjenja());
            case 2:
                return p.getTelefon();
            case 3:
                return p.getEmail();
            case 4:
                return p.getLaborant();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int i) {
        return header[i];
    }


    public List<Pacijent> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<Pacijent> list) {
        this.list = list;
    }
}
