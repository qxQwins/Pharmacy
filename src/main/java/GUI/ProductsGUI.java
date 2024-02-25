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

public class ProductsGUI extends JFrame{
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JComboBox companyBox;
    private JPanel productsPanel;
    private JTable productsTable;
    private JScrollPane scroll;
    private JButton companiesButton;
    private Product product;

    public ProductsGUI() {
        setContentPane(productsPanel);
        setTitle("Products");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(750, 440);
        setSize(500, 400);
        companiesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CompaniesGUI();
            }
        });
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            //Table
            List<Product> list = session.createQuery("SELECT a FROM products a", Product.class).getResultList();
            productsTable.setModel(new ProductsTableModel(list));
            //Box
            List<Company> c = session.createQuery("SELECT a FROM companies a", Company.class).getResultList();
            for (Company company : c) {
                companyBox.addItem(company.getName());
            }
        }
        catch (NoClassDefFoundError exception) {
            System.out.println("Exception!" + exception);
        }
        setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CompanyDAO cd = new CompanyDAO();
                ProductDAO pd = new ProductDAO();
                if(!inputChecker()) System.out.println("invalid input");
                else {
                    pd.add(new Product(nameTextField.getText(), Integer.parseInt(priceTextField.getText()),
                            cd.fetchByName((String) companyBox.getSelectedItem())));
                }
                tableRefresh();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDAO pd = new ProductDAO();
                if(productsTable.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(new JFrame(), "Choose the row you want to delete",
                            "Delete Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    pd.delete((Integer) productsTable.getValueAt(productsTable.getSelectedRow(), 0));
                    tableRefresh();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductDAO pd = new ProductDAO();
                CompanyDAO cd = new CompanyDAO();
                if(productsTable.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(new JFrame(), "Choose the row you want to change",
                            "Update Error", JOptionPane.ERROR_MESSAGE);
                }
                else if(!inputChecker()) System.out.println("invalid input");
                else {
                    pd.update((Integer) productsTable.getValueAt(productsTable.getSelectedRow(), 0),
                            nameTextField.getText(), Integer.parseInt(priceTextField.getText()),
                            cd.fetchByName((String) companyBox.getSelectedItem()));
                    tableRefresh();
                }

            }
        });
    }
    public void tableRefresh(){
        try(Session session = Connection.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            List<Product> list = session.createQuery("SELECT a FROM products a", Product.class).getResultList();
            productsTable.setModel(new ProductsTableModel(list));
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
            } else if (priceTextField.getText() == null || Objects.equals(priceTextField.getText(), "")) {
                JOptionPane.showMessageDialog(new JFrame(), "Price field is empty",
                        "Update Error", JOptionPane.ERROR_MESSAGE);
            } else if (!priceTextField.getText().matches("-?(0|[1-9]\\d*)")) {
                JOptionPane.showMessageDialog(new JFrame(), "Price has to be integer",
                        "Update Error", JOptionPane.ERROR_MESSAGE);
            }
            else input = true;
            return input;
    }
    private static class ProductsTableModel extends AbstractTableModel{

        private final String[] col = {"Id", "Name", "Price", "Company"};
        private List<Product> products;

        private ProductsTableModel(List<Product> products) {
            this.products = products;
        }

        @Override
        public int getRowCount() {
            return products.size();
        }

        @Override
        public int getColumnCount() {
            return col.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch(columnIndex){
                case 0 -> products.get(rowIndex).getId();
                case 1 -> products.get(rowIndex).getName();
                case 2 -> products.get(rowIndex).getPrice();
                case 3 -> products.get(rowIndex).getCompany().getName();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int column) {
            return col[column];
        }
    }
}
