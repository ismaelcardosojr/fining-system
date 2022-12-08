package br.com.mildevs.dao;

import br.com.mildevs.entity.Condutor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class CondutorDAO {

    private EntityManager manager;

    public CondutorDAO() {
        this.manager = Persistence.createEntityManagerFactory("sistema-transito").createEntityManager();
    }

    public boolean persistirCondutor(Condutor novoCondutor) {
        try {
            this.manager.getTransaction().begin();
            this.manager.persist(novoCondutor);
            this.manager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean removerCondutor(int CNH) {
        Condutor condutorEncontrado = this.manager.find(Condutor.class, CNH);

        try {
            this.manager.getTransaction().begin();
            this.manager.remove(condutorEncontrado);
            this.manager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean atualizarCondutor(Condutor condutorModificado) {
        try {
            this.manager.getTransaction().begin();
            this.manager.merge(condutorModificado);
            this.manager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Condutor consultarCondutor(int CNH) {
        return this.manager.find(Condutor.class, CNH);
    }

    public List<Condutor> listarCondutores() {
        Query consulta = this.manager.createQuery("SELECT c FROM Condutor AS c");

        return consulta.getResultList();
    }
}