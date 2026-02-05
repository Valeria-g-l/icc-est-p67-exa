package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pedido {
    private String cliente;
    private String codigoPostal;
    private List<Integer> prioridades;
    private int zona;
    private int urgencia;

    public Pedido(String cliente, String codigoPostal, List<Integer> prioridades) {
        this.cliente = cliente;
        this.codigoPostal = codigoPostal;
        this.prioridades = prioridades;
        this.zona = calcularZona();
        this.urgencia = calcularUrgencia();
    }

    private int calcularZona() {
        
        String[] partes = codigoPostal.split("-");
        return Integer.parseInt(partes[1]);
    }

    private int calcularUrgencia() {
        int suma = 0;
        for (int p : prioridades) {
            if (p % 3 == 0) suma += p;
        }

        Set<Character> vocales = new HashSet<>();
        for (char c : cliente.toLowerCase().toCharArray()) {
            if ("aeiou".indexOf(c) >= 0) {
                vocales.add(c);
            }
        }
        return suma * vocales.size();
    }

    public String getCliente() {
        return cliente;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public List<Integer> getPrioridades() {
        return prioridades;
    }

    public int getZona() {
        return zona;
    }

    public int getUrgencia() {
        return urgencia;
    }

    @Override
    public String toString() {
        return cliente + " (zona=" + zona + ", urgencia=" + urgencia + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pedido)) return false;
        Pedido other = (Pedido) o;
        return this.zona == other.zona && this.cliente.equals(other.cliente);
    }

    @Override
    public int hashCode() {
        return cliente.hashCode()* 31 + zona;
       
    }
}
