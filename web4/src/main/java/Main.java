import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.CustomerServlet;
import servlet.DailyReportServlet;
import servlet.NewDayServlet;
import servlet.ProducerServlet;

public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        CustomerServlet customerServlet = new CustomerServlet();
        ProducerServlet producerServlet = new ProducerServlet();
        DailyReportServlet reportServlet = new DailyReportServlet();
        NewDayServlet newDayServlet = new NewDayServlet();
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(customerServlet), "/customer");
        contextHandler.addServlet(new ServletHolder(producerServlet), "/producer");
        contextHandler.addServlet(new ServletHolder(reportServlet), "/report/*");
        contextHandler.addServlet(new ServletHolder(newDayServlet), "/newday");

        server.setHandler(contextHandler);
        server.start();
        server.join();
    }
}
