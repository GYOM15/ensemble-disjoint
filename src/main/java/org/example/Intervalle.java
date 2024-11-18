package org.example;

public class Intervalle {
    private double borneInferieur;
    private boolean estFermeAGauche;
    private double borneSuperieur;
    private boolean estFermeADroite;

    /**
     * Construit un nouvel intervalle.
     *
     * précondition : borneInferieur <= borneSuperieur
     *
     * @param borneInferieur La borne inférieur (gauche) de l'intervalle.
     * @param estFermeAGauche Indique si la borne inférieur est inclusive.
     *                         true : la borne est dans l'intervalle.
     *                         false : la borne n'est pas dans l'intervalle.
     * @param borneSuperieur La borne supérieur (droite) de l'intervalle.
     * @param estFermeADroite Indique si la borne inférieur est inclusive.
     *                         true : la borne est dans l'intervalle.
     *                         false : la borne n'est pas dans l'intervalle.
     */
    public Intervalle( double borneInferieur, boolean estFermeAGauche,
                       double borneSuperieur, boolean estFermeADroite ) {
        if (borneInferieur > borneSuperieur) {
            throw new IllegalArgumentException("borneInferieur > borneSuperieur");
        }
        this.borneInferieur = borneInferieur;
        this.estFermeAGauche = estFermeAGauche;
        this.borneSuperieur = borneSuperieur;
        this.estFermeADroite = estFermeADroite;
    }

    /**
     * NE PAS MODIFIER LE CODE DU EQUALS.
     */
    @Override
    public boolean equals( Object obj ) {
        return switch( obj ) {
            case Intervalle int2 ->
                ( borneInferieur == int2.borneInferieur ) && ( estFermeAGauche == int2.estFermeAGauche ) &&
                        ( borneSuperieur == int2.borneSuperieur ) && ( estFermeADroite == int2.estFermeADroite );
            default -> false;
        };
    }

    @Override
    public String toString()
    {
        return (isEstFermeAGauche() ? "[" : "]") + getBorneInferieur() + "," + getBorneSuperieur() + (isEstFermeADroite()  ? "]" : "[");
    }

    public double getBorneInferieur() {
        return borneInferieur;
    }

    public boolean isEstFermeAGauche() {
        return estFermeAGauche;
    }

    public double getBorneSuperieur() {
        return borneSuperieur;
    }

    public boolean isEstFermeADroite() {
        return estFermeADroite;
    }
}
