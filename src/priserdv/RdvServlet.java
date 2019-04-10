package priserdv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rdv.*;


/**
 * Servlet implementation class RdvServlet
 */
@WebServlet("/RdvServlet")
public class RdvServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RdvServlet() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		if (!new File("rdv.xml").exists()) {
	    	ListeRDV l = new ListeRDV();
	    	l.add(new RendezVous("15/03/19","15:00","Michel Cymes","Paul Jaquit","Pneumonie"));
			XMLTools.encodeToFile(l,"./rdv.xml");
		}
	    	
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	    /* R�cup�ration des champs du formulaire. */
        String medecin = request.getParameter( "selectMed" );
        String patientNom = request.getParameter( "patientNom" );
        String patientPrenom = request.getParameter( "patientPrenom" );
        String raison = request.getParameter( "raisonrdv" );
        String date = request.getParameter( "date" );
        String heure = request.getParameter( "Heure" );
        		
        RendezVous rdv = new RendezVous(date,heure,medecin,patientNom+" "+patientPrenom,raison);

        ListeRDV listeRdv= (ListeRDV) XMLTools.decodeToObject("rdv.xml");    
        listeRdv.add(rdv);
        XMLTools.encodeToFile(listeRdv,"rdv.xml");
		
        response.sendRedirect("listerdv?form=ok");
	}
	

}
