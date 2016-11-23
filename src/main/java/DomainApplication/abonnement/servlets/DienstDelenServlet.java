package DomainApplication.abonnement.servlets;

import DomainApplication.IAbonnee;
import DomainApplication.IAbonnement;
import DomainApplication.abonnee.AbonneeService;
import DomainApplication.abonnement.AbonnementService;
import jersey.AbonneeJerseyService;
import jersey.AbonnementJerseyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Anders Egberts on 20/10/2016.
 */
@WebServlet(
        value = "/shareService"
)
public class DienstDelenServlet extends HttpServlet {
    AbonnementService abonnementService = new AbonnementService();
    AbonnementJerseyService jerseyService = new AbonnementJerseyService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IAbonnee loggedInUser = (IAbonnee) req.getSession().getAttribute( "loggedInUser" );
        if ( null == loggedInUser ) {
            req.getRequestDispatcher( "/login.jsp" ).forward( req , resp );
            return;
        }

        int abonneeId = loggedInUser.getAbonneeId();
        String bedrijf = req.getParameter("bedrijf");
        String naam = req.getParameter("naam");
        int targetAbonneeId = Integer.parseInt( req.getParameter( "targetUserId" ) );

        if ( null == bedrijf || null == naam ) {
            return;
        }

        IAbonnement abonnementToShare = jerseyService.getByOwnerCompanyandName( abonneeId , bedrijf , naam );
        IAbonnee shareRecipient = new AbonneeJerseyService().getAbonneeById( targetAbonneeId );
        jerseyService.shareWith( abonnementToShare , shareRecipient );

        resp.getWriter().write( "Service shared!!!111one");
    }
}