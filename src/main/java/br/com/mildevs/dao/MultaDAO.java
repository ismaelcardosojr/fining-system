package br.com.mildevs.dao;

import br.com.mildevs.entity.Multa;
import br.com.mildevs.entity.Veiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class MultaDAO {

    private EntityManager manager;

    public MultaDAO() {
        this.manager = Persistence.createEntityManagerFactory("sistema-transito").createEntityManager();
    }

    public boolean persistirMulta(Multa novaMulta) {
        try {
            this.manager.getTransaction().begin();
            this.manager.persist(novaMulta);
            this.manager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean removerMulta(int codigo) {
        Multa veiculoEncontrado = this.manager.find(Multa.class, codigo);

        try {
            this.manager.getTransaction().begin();
            this.manager.remove(veiculoEncontrado);
            this.manager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Multa consultarMulta(int codigo) {
        return this.manager.find(Multa.class, codigo);
    }

    public List<Multa> listarMultas() {
        Query consulta = this.manager.createQuery("SELECT m FROM Multa AS m");

        return consulta.getResultList();
    }

    public List<Multa> listarMultasPorVeiculo(Veiculo placa) {
        Query consulta = this.manager.createQuery("SELECT m FROM Multa AS m WHERE m.veiculo = :veiculo");
        consulta.setParameter("veiculo", placa);

        return consulta.getResultList();
    }
}