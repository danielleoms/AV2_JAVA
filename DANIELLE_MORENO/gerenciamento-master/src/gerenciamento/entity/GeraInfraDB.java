package gerenciamento.entity;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class GeraInfraDB {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("alunos");
		factory.close();
	}
}
