import dao.CustomerDao;
import dao.ItemDao;
import dao.OrderDao;
import dao.UserSessionDao;
import org.hibernate.SessionFactory;
import util.ConnectionUtil;

public class AppRunner {
    public static void main(String[] args) {
        SessionFactory sessionFactory = ConnectionUtil.getSessionFactory();
        var session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        CustomerDao customerDao = new CustomerDao();
        UserSessionDao userSessionDao = new UserSessionDao();
        OrderDao orderDao = new OrderDao();
        ItemDao itemDao = new ItemDao();

        //session.get(Customer.class, 1);
        customerDao.findById(1);
        customerDao.findById(1).ifPresent(System.out::println);
        customerDao.findById(1).ifPresent(System.out::println);
        customerDao.findAll().forEach(System.out::println);
        customerDao.findAll().forEach(System.out::println);

        System.out.println();
        userSessionDao.findAll().forEach(System.out::println);
        customerDao.findByToken("b571754f-8a1f-4ff5-923b-e8d028751e6e").ifPresent(System.out::println);

        System.out.println();
        orderDao.findAll().forEach(System.out::println);
        orderDao.findOrdersByCustomer(1).forEach(System.out::println);

        System.out.println();
        itemDao.findAll().forEach(System.out::println);
        itemDao.findAll().forEach(System.out::println);
        itemDao.findById(2).ifPresent(System.out::println);
        session.getTransaction().commit();
    }
}

