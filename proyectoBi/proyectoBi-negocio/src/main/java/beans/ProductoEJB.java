package beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder.In;

import entidades.Producto;
import entidades.Lote;
import excepciones.ExcepcionNegocio;
import persistencia.Persistencia;


@LocalBean
@Stateless
public class ProductoEJB {

	@EJB
	private Persistencia em;

	/**
	 * Edita un producto en la BD
	 * 
	 * @param producto
	 *            producto que se desea editar
	 */
	public void editarProducto(Producto producto,int bd) {
		em.setBd(bd);
		em.editar(producto);
	}


	/**
	 * Registra un producto en la BD
	 * 
	 * @param producto
	 *            producto que se desea registrar
	 */
	public void registrarProducto(Producto producto,int bd) throws ExcepcionNegocio {
		// TODO Auto-generated method stub
		if (buscarProducto(producto.getId(),bd) != null) {
			throw new ExcepcionNegocio("El producto ya existe");
		} else {
			em.setBd(bd);
			em.crear(producto);
		}
	}

	/**
	 * Busca un producto por su id
	 * 
	 * @param id
	 *            id del producto a buscar
	 * @return el producto si lo encuentra, de lo contrario null
	 */
	public Producto buscarProducto(int id,int bd) {
		// TODO Auto-generated method stub
		em.setBd(bd);
		return (Producto) em.buscar(Producto.class, id);
	}

	/**
	 * Busca el lote de un producto en la base de datos
	 * 
	 * @param codigo
	 *            código del lote
	 * @return el lote si lo encuetra, de lo contrario null
	 */
	public Lote buscarloteProducto(int codigo,int bd) {
		em.setBd(bd);
		return (Lote) em.buscar(Lote.class, codigo);
	}

	/**
	 * Lista los productos registrados
	 * 
	 * @return la lista de productos registrados
	 */
	public List<Producto> listarProductos(int bd) {
		em.setBd(bd);
		return (List<Producto>) (Object) em.listar(Producto.LISTAR);
	}


	/**
	 * Elimina un producto de la BD
	 * 
	 * @param producto
	 *            producto que se desea eliminar
	 */
	public void eliminarProducto(Producto producto,int bd) {
		em.setBd(bd);
		em.eliminar(producto);
	}


	/**
	 * Obtiene la lista de lotes registrados en la base de datos
	 * 
	 * @return la lista de lotes
	 */
	public List<Lote> lotes(int bd) {
		em.setBd(bd);
		return (List<Lote>) (Object) em.listar(Lote.LISTA_LOTES);
	}

}
