package br.com.mildevs.dao;

import br.com.mildevs.entity.Veiculo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.util.List;

public class VeiculoDAO {

    private EntityManager manager;

    public VeiculoDAO() {
        this.manager = Persistence.createEntityManagerFactory("sistema-transito").createEntityManager();
    }

    public boolean persistirVeiculo(Veiculo novoVeiculo) {
        try {
            this.manager.getTransaction().begin();
            this.manager.persist(novoVeiculo);
            this.manager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean removerVeiculo(String placa) {
        Veiculo veiculoEncontrado = this.manager.find(Veiculo.class, placa);

        try {
            this.manager.getTransaction().begin();
            this.manager.remove(veiculoEncontrado);
            this.manager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean atualizarVeiculo(Veiculo veiculoModificado) {
        try {
            this.manager.getTransaction().begin();
            this.manager.remove(veiculoModificado);
            this.manager.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Veiculo consultarVeiculo(String placa) {
        return this.manager.find(Veiculo.class, placa);
    }

    public List<Veiculo> listarVeiculos() {
        Query consulta = this.manager.createQuery("SELECT v FROM Veiculo AS v");

        return consulta.getResultList();
    }
}