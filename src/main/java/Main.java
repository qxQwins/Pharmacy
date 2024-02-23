import DAO.CompanyDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import Entities.Company;
import Entities.Product;
import Entities.User;
import GUI.Login;
import JDBC.Connection;
import com.mysql.cj.DataStoreMetadata;
import com.mysql.cj.MessageBuilder;
import com.mysql.cj.conf.HostInfo;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.log.Log;
import com.mysql.cj.log.ProfilerEventHandler;
import com.mysql.cj.protocol.Message;
import com.mysql.cj.protocol.ServerSession;
import org.hibernate.Session;
import jakarta.persistence.*;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    static UserDAO userDAO = new UserDAO();
    public static void main(String[] args) {
        Connection.Initialize();
//        User user = new User("qwinsyq", "vladik");
//        UserDAO ud = new UserDAO();
//        ud.add(user);
        //System.out.println(userDAO.fetchById(5));
        //new Login();
//        Company company1 = new Company("zxcPharm", "Igymensky 24");
//        CompanyDAO cd = new CompanyDAO();
//        cd.add(company1);
//        Product product1 = new Product("qwe", 12, company1);
//        Product product2 = new Product("asd", 10, company1);
//        Product product3 = new Product("zxc", 7, company1);
//        ProductDAO pd = new ProductDAO();
//        pd.add(product1);
//        pd.add(product2);
//        pd.add(product3);
//        Connection.shutdown();
    }
    public static boolean login(String login, String password){
        Session session = Connection.getSessionFactory().openSession();
        boolean result = false;
        List<User> list = session.createQuery("SELECT a FROM users a", User.class).getResultList();
        for(User temp : list){
            if(temp.getLogin() == login){
                result = Objects.equals(temp.getPassword(), password);
            }
        }
        return result;
    }
}
