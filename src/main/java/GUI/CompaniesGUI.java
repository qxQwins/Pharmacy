package GUI;

import DAO.CompanyDAO;
import DAO.ProductDAO;
import Entities.Company;
import Entities.Product;
import JDBC.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class CompaniesGUI extends JFrame{
    private JPanel companiesPanel;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField nameTextField;
    private JTextField addressTextField;
    private JScrollPane scroll;
    private JTable companiesTable;
    private JButton productsButton;

    public CompaniesGUI(){
        setContentPane(companiesPanel);
        setTitle("Companies");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(750, 440);
        setSize(500, 400);
        try(Session session = Connection.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction();
            List<Company> list = session.createQuery("Select a from companies a", Company.class).getResultList();
            companiesTable.setModel(new CompaniesTableModel(list));
        }
        catch (NoClassDefFoundError exception) {
            System.out.println("Exception!" + exception);
        }
        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new ProductsGUI();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyDAO cd = new CompanyDAO();
                if(!inputChecker()) System.out.println("invalid input");
                else {
                    cd.add(new Company(nameTextField.getText(), addressTextField.getText()));
                }
                tableRefresh();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyDAO cd = new CompanyDAO();
                if(companiesTable.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(new JFrame(), "Choose the row you want to delete",
                            "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    cd.delete((Integer) companiesTable.getValueAt(companiesTable.getSelectedRow(), 0));
                    tableRefresh();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyDAO cd = new CompanyDAO();
                if(companiesTable.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(new JFrame(), "Choose the row you want to change",
                            "Update Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(!inputChecker()) System.out.println("invalid input");
                else {
                    cd.update((Integer) companiesTable.getValueAt(companiesTable.getSelectedRow(), 0),
                            nameTextField.getText(), addressTextField.getText());
                    tableRefresh();
                }

            }
        });
        setVisible(true);
    }
    public void tableRefresh(){
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            List<Company> list = session.createQuery("SELECT a FROM companies a", Company.class).getResultList();
            companiesTable.setModel(new CompaniesTableModel(list));
        }
        catch (NoClassDefFoundError exception) {
            System.out.println("Exception!" + exception);
        }
    }
    public boolean inputChecker(){
        boolean input = false;
        if (nameTextField.getText() == null || Objects.equals(nameTextField.getText(), "")) {
            JOptionPane.showMessageDialog(new JFrame(), "Name field is empty",
                    "Update Error", JOptionPane.ERROR_MESSAGE);
        } else if (addressTextField.getText() == null || Objects.equals(addressTextField.getText(), "")) {
            JOptionPane.showMessageDialog(new JFrame(), "Adress field is empty",
                    "Update Error", JOptionPane.ERROR_MESSAGE);
        }
        else input = true;
        return input;
    }
    private static class CompaniesTableModel extends AbstractTableModel{
        private final String[] col = {"Id", "Name", "Address"};

        public CompaniesTableModel(List<Company> companies) {
            this.companies = companies;
        }

        private List<Company> companies;
        @Override
        public int getRowCount() {
            return companies.size();
        }

        @Override
        public int getColumnCount() {
            return col.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch(columnIndex){
                case 0 -> companies.get(rowIndex).getId();
                case 1 -> companies.get(rowIndex).getName();
                case 2 -> companies.get(rowIndex).getAddress();
                default -> "-";
            };
        }
        @Override
        public String getColumnName(int column) {
            return col[column];
        }
    }

}
