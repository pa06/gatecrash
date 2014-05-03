
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WorkerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;    
    
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) {
    	try {
    		RequestDispatcher view = request.getRequestDispatcher("/Index.jsp");
            view.forward(request, response);
            response.setStatus(200);
        } catch (Exception exception) {
            response.setStatus(500);
            try (PrintWriter writer =
                 new PrintWriter(response.getOutputStream())) {
                exception.printStackTrace(writer);
            } catch(Exception e) {
            }
        }
    }
}
