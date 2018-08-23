package BeansKM;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import km.DimensionPage;
import km.DimensionUser;
import km.HechoRecentChange;
import persistencia.Persistencia;

@LocalBean
@Stateless
public class GestionKmEJB {

	@EJB
	private Persistencia em;
	
	public void guardarUsers (){
		em.limpiarKmDwh();
		List<DimensionUser> users = em.listaUsuarios();
		em.guardarUsers(users);
		try {
			List<DimensionPage> pages = em.listaPaginas();
			em.guardarPages(pages);
			
			List<HechoRecentChange> changes = em.listaCambios(users, pages);
			em.guardarChanges(changes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
