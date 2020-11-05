/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.Rezultat;
import domen.Pacijent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milica
 */
public class ModelTabeleRezultat extends AbstractTableModel {

    List<Rezultat> rezultati;
    private String[] header = {"Redni broj", "Pacijent", "Rezultat testa"};

    public ModelTabeleRezultat(List<Rezultat> rezultati) {
        this.rezultati = rezultati;
    }

    @Override
    public int getRowCount() {
        if (rezultati != null) {
            return rezultati.size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rezultat rezultat = rezultati.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return rezultat.getPacijent();
            case 2:
                return rezultat.getVrednost()>-1?rezultat.getVrednost():"";
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int i) {
        return header[i];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 2:
                Rezultat r = rezultati.get(rowIndex);
                if (r.getVrednost() != -1) {
                    return false;
                }else{
                    return true;
                }
            default:
                return false;
        }
    }

    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        try {
            if (rowIndex >= rezultati.size()) {
            return;
        }
        Rezultat rezultat = rezultati.get(rowIndex);
        switch (columnIndex) {
            case 0:
                break;
            case 1:
                rezultat.setPacijent((Pacijent) o);
                break;
            case 2:

                if (Integer.parseInt(o.toString()) >= 0 && Integer.parseInt(o.toString()) <= 100) {
                    rezultat.setVrednost(Integer.parseInt(o.toString()));
                    rezultat.setNovi(true);
                } else {
                    rezultat.setVrednost(-1);
                    rezultat.setNovi(false);

                }
                break;

        }
        } catch (Exception e) {
        }
        
    }

    public List<Rezultat> dajSveRezultate() {
        return rezultati;
    }
}
