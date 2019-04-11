package priserdv;

import java.io.File;
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
@WebServlet()
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
		
		// Si le fichier des rendez-vous n'existe pas on le crée
		if (!new File("rdv.xml").exists()) {
	    	ListeRDV l = new ListeRDV();
			XMLTools.encodeToFile(l,"./rdv.xml");
		}
	    	
		ListeRDV listeRdv= (ListeRDV) XMLTools.decodeToObject("rdv.xml"); // Récupération des rendez-vous   
		request.setAttribute( "listeRdv", listeRdv );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/index.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	    // Récupération des champs du formulaire
        String medecin = request.getParameter( "selectMed" );
        String patientNom = request.getParameter( "patientNom" );
        String patientPrenom = request.getParameter( "patientPrenom" );
        String raison = request.getParameter( "raisonrdv" );
        String date = request.getParameter( "date" );
        String heure = request.getParameter( "heure" );
            
        RendezVous rdv = new RendezVous(date,heure,medecin,patientNom+" "+patientPrenom,raison);

        ListeRDV listeRdv= (ListeRDV) XMLTools.decodeToObject("rdv.xml"); // Récupération des rendez-vous
        
        // Si le rendez-vous existe déjà, on ne l'ajoute pas et on déclenche un message d'erreur
        // Sinon on l'ajoute et on déclenche un message de succes
        if (listeRdv.getListeRDV().contains(rdv)) {
        	response.sendRedirect("index?form=ko");
        } else {        
	        listeRdv.add(rdv);   
	        XMLTools.encodeToFile(listeRdv,"rdv.xml");
	        response.sendRedirect("listerdv?form=ok");
        }
        
	}
	

}
