package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class Main {
    public static void main(String[] args) {
        List<Intervalle> intervalles = new ArrayList<>();

        intervalles.add(new Intervalle(1, true, 2, true));   // [1, 2]
        intervalles.add(new Intervalle(4, false, 7, true));
        EnsembleDisjoint ensemble = new EnsembleDisjoint(intervalles);
        //System.out.println(intervalles);
        DoubleUnaryOperator fctLineaire = x -> 2 - x; // f(x) = 2x + 1
        EnsembleDisjoint resultat = ensemble.appliquerFctLineaire(fctLineaire);
        for (Intervalle inter : resultat.getIntervallesTries()) {
            System.out.println(inter);
        }
//        List<Intervalle> sortedIntervalles = ensemble.getIntervallesTries();
//        System.out.println(sortedIntervalles);
    }
}