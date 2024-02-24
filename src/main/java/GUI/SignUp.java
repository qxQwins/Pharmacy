package GUI;

import DAO.UserDAO;
import Entities.User;
import JDBC.Connection;
import com.mysql.cj.log.Log;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class SignUp extends JFrame{
    private JTextField loginTextField;
    private JPasswordField passwordTextField;
    private JButton signUpButton;
    private JPanel signUpPanel;
    SignUp(){
        setContentPane(signUpPanel);
        setTitle("Sign up");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                new Login();
            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean repeat = false;
                try(Session session = Connection.getSessionFactory().openSession()){
                    List<User> list = session.createQuery("Select a From users a", User.class).getResultList();
                    for(User temp : list) {
                        if (temp.getLogin().equals(loginTextField.getText())){
                            repeat = true;
                            break;
                    }
                    }
                }
                catch (NoClassDefFoundError exception){
                    System.out.println("Exception! + " + exception);
                }
                if(repeat){
                    JOptionPane.showMessageDialog(new JFrame(), "User with such login already " +
                                    " exists", "Sign up Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(passwordTextField.getText().isBlank()){
                    JOptionPane.showMessageDialog(new JFrame(), "Password field is empty",
                            "Sign up Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(loginTextField.getText().isBlank()){
                    JOptionPane.showMessageDialog(new JFrame(), "Login field is empty",
                            "Sign up Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    User newUser = new User(loginTextField.getText(), passwordTextField.getText());
                    UserDAO dao = new UserDAO();
                    dao.add(newUser);
                    JOptionPane.showMessageDialog(new JFrame(), "User signed up!");
                    dispose();
                    new Login();
                }
            }
        });

    }
}
