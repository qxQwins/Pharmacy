package GUI;

import Entities.User;
import JDBC.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class Login extends JFrame{
    private JPanel loginPanel;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JLabel password;
    private JLabel login;
    private JButton loginButton;
    private JButton signUpButton;
    private JCheckBox showPasswordCheckBox;

    public Login(){
        setContentPane(loginPanel);
        setTitle("Login in");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean valid = false, exists = false;
                try(Session session = Connection.getSessionFactory().openSession()) {
                    Transaction tx = session.beginTransaction();
                    List<User> list = session.createQuery("SELECT a FROM users a", User.class).getResultList();
                    for(User temp : list){
                        if(Objects.equals(temp.getLogin(), loginTextField.getText())){
                            exists = true;
                            valid = Objects.equals(temp.getPassword(), passwordField.getText());
                        }
                    }
                }
                catch (NoClassDefFoundError exception) {
                    System.out.println("Exception!" + exception);
                }
                if(!exists){JOptionPane.showMessageDialog(new JFrame(), "User with such login doesn't" +
                                " exist", "Login Error",
                        JOptionPane.ERROR_MESSAGE);
                }
                if(!valid)JOptionPane.showMessageDialog(new JFrame(), "Incorrect password", "Login Error",
                        JOptionPane.ERROR_MESSAGE);
                dispose();
                new ProductsGUI();
            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SignUp();
            }
        });
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showPasswordCheckBox.isSelected()){
                    passwordField.setEchoChar((char)0);
                }
                else passwordField.setEchoChar('*');
            }
        });

    }

}
