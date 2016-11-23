package DomainApplication.dienst;

import DomainApplication.IAbonnee;
import jersey.DienstJerseyService;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Anders Egberts on 14/10/2016.
 */
@WebServlet(
        urlPatterns = { "/dienstUitproberen" }
)
public class DienstServlet extends HttpServlet {
    DienstService dienstService = new DienstService();
    DienstJerseyService jerseyService = new DienstJerseyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IAbonnee loggedInUser = (IAbonnee) req.getSession().getAttribute( "loggedInUser" );
        if ( null == loggedInUser ) {
            req.getRequestDispatcher( "/login.jsp" ).forward( req , resp );
            return;
        }
        System.out.println( loggedInUser );
        String searchTerm = req.getParameter( "searchTerm" );
        System.out.println( "searchTerm: " + searchTerm );
        if ( null == searchTerm ) {
            req.setAttribute("diensten", jerseyService.getAll());
        } else {
            req.setAttribute("diensten", jerseyService.search( searchTerm ));
        }
        req.getRequestDispatcher( "/dienstUitproberenView.jsp" ).forward( req , resp );
    }
}