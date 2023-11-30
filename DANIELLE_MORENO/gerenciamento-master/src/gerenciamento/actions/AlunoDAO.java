package gerenciamento.actions;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import gerenciamento.entity.Aluno;

public class AlunoDAO {
	

	private static AlunoDAO instance;
	protected EntityManager entityManager;

	public static AlunoDAO getInstance() {
		if (instance == null) {
			instance = new AlunoDAO();
		}

		return instance;
	}

	public AlunoDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public Aluno getById(final Long id) {
		return entityManager.find(Aluno.class, id);
	}
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("alunos");

	@SuppressWarnings("unchecked")
	public List<Aluno> findAll() {
		return entityManager.createQuery("FROM " + Aluno.class.getName()).getResultList();
	}

	public void persist(Aluno aluno) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void merge(Aluno aluno) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void remove(Aluno aluno) {
		try {
			entityManager.getTransaction().begin();
			aluno = entityManager.find(Aluno.class, aluno.getId());
			entityManager.remove(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}

	public void removeById(final Long id) {
		try {
			Aluno aluno = getById(id);
			remove(aluno);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public List<Aluno> getAlunosLetra(String letra) throws SQLException {

		String jpql = "select t from alunos as t where t.nome Like '" + letra + "%'";
		TypedQuery<Aluno> query = entityManager.createQuery(jpql, Aluno.class);
		query.setMaxResults(5);
		
		List<Aluno> alunos = query.getResultList();
		
		for(Aluno aluno: alunos) {
			System.out.println("ID: " + aluno.getId() 
				+ " E-mail: " + aluno.getEmail());
		}
		return alunos;
	}

}
