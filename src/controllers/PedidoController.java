package controllers;

import models.Pedido;

import java.util.*;

public class PedidoController {

    public Stack<Pedido> filtrarPorZona(List<Pedido> pedidos, int umbral) {
        Stack<Pedido> pila = new Stack<>();
        for (Pedido p : pedidos) {
            if (p.getZona() > umbral) {
                pila.push(p);
            }
        }
        return pila;
    }

    public TreeSet<Pedido> ordenarPorZona(Stack<Pedido> pila) {
        TreeSet<Pedido> set = new TreeSet<>((a, b) -> {
            int cmp = Integer.compare(a.getZona(), b.getZona());
            if (cmp != 0) return cmp;
            return a.getCliente().compareTo(b.getCliente());
        });
        set.addAll(pila);
        return set;
    }

    public TreeMap<Integer, Queue<Pedido>> agruparPorUrgencia(List<Pedido> pedidos) {
        TreeMap<Integer, Queue<Pedido>> mapa = new TreeMap<>();
        for (Pedido p : pedidos) {
            mapa.putIfAbsent(p.getUrgencia(), new LinkedList<>());
            mapa.get(p.getUrgencia()).add(p);
        }
        return mapa;
    }

    public Stack<Pedido> explotarGrupo(TreeMap<Integer, Queue<Pedido>> mapa) {
        int max = -1;
        int riesgoSel = -1;
        for (var e : mapa.entrySet()) {
            if (e.getValue().size() > max ||
                (e.getValue().size() == max && e.getKey() > riesgoSel)) {
                max = e.getValue().size();
                riesgoSel = e.getKey();
            }
        }

        Stack<Pedido> pila = new Stack<>();
        if (riesgoSel != -1) {
            List<Pedido> lista = new ArrayList<>(mapa.get(riesgoSel));
            for (int i = lista.size() - 1; i >= 0; i--) {
                pila.push(lista.get(i));
            }
        }
        return pila;
    }
}
