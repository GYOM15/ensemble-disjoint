package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class EnsembleDisjoint {
    // Proposition pour une variable d'instance utile pour cette classe :
    private final List<Intervalle> intervalles;

    {
        intervalles = new ArrayList<>();
    }

    public boolean isBoundariesOk(Intervalle intervalle) {
        return !(intervalle.getBorneInferieur() > intervalle.getBorneSuperieur());
    }

    /**
     * Construt un EnsembleDisjoint vide.
     */
    public EnsembleDisjoint() {
    }

    /**
     * Construit un EnsembleDisjoint d'intervalles à partir d'une collection d'intervalle.
     *
     * @param intervalles Une collection d'intervalles.  Les intervalles de cette collection ne sont pas necéssairement
     *                    disjoint.  Le constructeur s'occupe de les rendre disjoint.
     */
    public EnsembleDisjoint(Collection<Intervalle> intervalles) {
        if (intervalles != null) {
            this.intervalles.addAll(intervalles);
        }
    }

    /**
     * Construit un nouvel ensemble qui contient les valeurs de l’ensemble courant (this) et les valeurs de
     * l’intervalle en parametre.
     *
     * @param intervalle Un intervalle de valeur a ajouter dans l'ensemble disjoint resultant.
     *                   precondition : null != intervalle.
     * @return Un ensemble disjoint qui contient les valeurs de l'ensemble courant et de l'intervalle en
     * parametre.
     */
    public EnsembleDisjoint union(Intervalle intervalle) {
        List<Intervalle> nouveauxIntervalles = new ArrayList<>(this.intervalles);
        nouveauxIntervalles.add(intervalle);
        EnsembleDisjoint nouvelEnsemble = new EnsembleDisjoint(nouveauxIntervalles);
        List<Intervalle> intervallesDisjoints = nouvelEnsemble.getIntervallesTries();
        return new EnsembleDisjoint(intervallesDisjoints);
    }

    /**
     * Construit un nouvel ensemble qui contient les valeurs de l’ensemble courant (this) et les valeurs de
     * l’ensemble en parametre.
     *
     * @param ensemble Un ensemble de valeur a ajouter dans l'ensemble disjoint resultant.
     *                 precondition : null != ensemble.
     * @return Un ensemble disjoint qui contient les valeurs de l'ensemble courant et de l'ensemble en
     * parametre.
     */
    public EnsembleDisjoint union(EnsembleDisjoint ensemble) {
        List<Intervalle> tousLesIntervalles = new ArrayList<>(this.intervalles);
        if (ensemble != null) {
            tousLesIntervalles.addAll(ensemble.intervalles);
        }
        EnsembleDisjoint nouvelEnsemble = new EnsembleDisjoint(tousLesIntervalles);
        List<Intervalle> intervallesDisjoints = nouvelEnsemble.getIntervallesTries();
        return new EnsembleDisjoint(intervallesDisjoints);
    }

    /**
     * Construit un nouvel ensemble qui contient les valeurs de l’ensemble courant (this) qui sont aussi dans
     * l'intervalle en parametre.
     *
     * @param intervalle Un intervalle de valeur.
     *                   precondition : null != intervalle.
     * @return Un ensemble disjoint qui contient les valeurs de l'ensemble courant qui sont aussi dans l'intervalle en
     * parametre.
     */
    public EnsembleDisjoint intersection(Intervalle intervalle) {
        if (intervalle == null) {
            throw new IllegalArgumentException("L'intervalle ne peut pas être null.");
        }
        List<Intervalle> intersections = new ArrayList<>();
        for (Intervalle current : this.intervalles) {
            Intervalle intersection = calculerIntersection(current, intervalle);
            if (intersection != null) {
                intersections.add(intersection);
            }
        }
        return new EnsembleDisjoint(intersections);
    }
    /**
     * Calcule l'intersection de deux intervalles.
     * <p>
     * L'intersection est définie comme l'ensemble des valeurs communes aux deux intervalles.
     * Si les intervalles ne se chevauchent pas, ou si leur intersection est vide
     * (par exemple, lorsque les bornes se touchent mais ne sont pas incluses), la méthode retourne {@code null}.
     * </p>
     *
     * @param a Le premier intervalle. Précondition : {@code a != null}.
     * @param b Le second intervalle. Précondition : {@code b != null}.
     * @return Un nouvel intervalle représentant l'intersection des deux intervalles, ou {@code null}
     *         si l'intersection est vide.
     *
     * @throws IllegalArgumentException Si l'un des intervalles est null.
     */
    private Intervalle calculerIntersection(Intervalle a, Intervalle b) {
        double borneInf = Math.max(a.getBorneInferieur(), b.getBorneInferieur());
        boolean fermeAGauche = (borneInf == a.getBorneInferieur() ? a.isEstFermeAGauche() : b.isEstFermeAGauche())
                && (borneInf == b.getBorneInferieur() ? b.isEstFermeAGauche() : a.isEstFermeAGauche());
        double borneSup = Math.min(a.getBorneSuperieur(), b.getBorneSuperieur());
        boolean fermeADroite = (borneSup == a.getBorneSuperieur() ? a.isEstFermeADroite() : b.isEstFermeADroite())
                && (borneSup == b.getBorneSuperieur() ? b.isEstFermeADroite() : a.isEstFermeADroite());
        if (borneInf > borneSup || (borneInf == borneSup && (!fermeAGauche || !fermeADroite))) {
            return null;
        }
        return new Intervalle(borneInf, fermeAGauche, borneSup, fermeADroite);
    }

    /**
     * Construit un nouvel ensemble qui contient les valeurs de l’ensemble courant (this) qui sont aussi dans
     * l'ensemble en parametre.
     *
     * @param ensemble Un intervalle de valeur.
     *                 precondition : null != ensemble.
     * @return Un ensemble disjoint qui contient les valeurs de l'ensemble courant qui sont aussi dans l'ensemble en
     * parametre.
     */
    public EnsembleDisjoint intersection(EnsembleDisjoint ensemble) {
        if (ensemble == null) {
            throw new IllegalArgumentException("L'ensemble ne peut pas être null.");
        }
        List<Intervalle> intersections = new ArrayList<>();

        for (Intervalle intervalle1 : this.intervalles) {
            for (Intervalle intervalle2 : ensemble.intervalles) {
                Intervalle intersection = calculerIntersection(intervalle1, intervalle2);
                if (intersection != null) { // Ajouter uniquement si l'intersection n'est pas vide
                    intersections.add(intersection);
                }
            }
        }
        return new EnsembleDisjoint(intersections);
    }

    /**
     * Indique si la valeur en parametre appartient à l'ensemble disjoint.
     *
     * @param valeur une valeur.
     * @return true si la valeur est dans un des intervalles disjoint, false sinon.
     */
    public boolean appartient(double valeur) {
        boolean result = false;
        for (Intervalle intervalle : intervalles) {
            double borneInf = intervalle.getBorneInferieur();
            double borneSup = intervalle.getBorneSuperieur();
            boolean fermeAGauche = intervalle.isEstFermeAGauche();
            boolean fermeADroite = intervalle.isEstFermeADroite();
            boolean appartientAGauche = fermeAGauche ? borneInf <= valeur : borneInf < valeur;
            boolean appartientADroite = fermeADroite ? valeur <= borneSup : valeur < borneSup;
            if (appartientAGauche && appartientADroite) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Retourne un nouvel ensemble disjoint qui contient les memes valeurs que l'ensemble courant sauf pour les
     * valeur plus grande que le maximum en argument.
     *
     * @param max       La borne superieure des valeurs qui sont placees dans l'ensemble resultant.
     * @param estInclut Si estInclut est a true et que la valeur max etait dans l'ensemble courant, alors la valeur max
     *                  est aussi dans l'ensemble resultant.  Sinon, la valeur max n'est pas dans l'ensemble resultant.
     * @return L'ensemble des valeurs de l'ensemble courant sauf celle plus grande que le maximum (ou egal si non
     * inclut).
     */
    public EnsembleDisjoint bornerSuperieur(double max, boolean estInclut) {
        List<Intervalle> bornesResultantes = new ArrayList<>();
        for (Intervalle intervalle : this.intervalles) {
            double borneInf = intervalle.getBorneInferieur();
            double borneSup = intervalle.getBorneSuperieur();
            boolean fermeAGauche = intervalle.isEstFermeAGauche();
            boolean fermeADroite = intervalle.isEstFermeADroite();
            if (borneSup < max || (borneSup == max && fermeADroite && estInclut)) {
                // Intervalle complètement en dessous de max
                bornesResultantes.add(intervalle);
            } else if (borneInf < max) {
                // L'intervalle doit être tronqué à max
                bornesResultantes.add(new Intervalle(borneInf, fermeAGauche, max, estInclut
                ));
            }
        }

        return new EnsembleDisjoint(bornesResultantes);
    }

    /**
     * Retourne un nouvel ensemble disjoint qui contient les memes valeurs que l'ensemble courant sauf pour les
     * valeur plus petite que le minimum en argument.
     *
     * @param min       La borne inferieur des valeurs qui sont placees dans l'ensemble resultant.
     * @param estInclut Si estInclut est a true et que la valeur min etait dans l'ensemble courant, alors la valeur min
     *                  est aussi dans l'ensemble resultant.  Sinon, la valeur min n'est pas dans l'ensemble resultant.
     * @return L'ensemble des valeurs de l'ensemble courant sauf celle plus petite que le minimum (ou egal si non
     * inclut).
     */
    public EnsembleDisjoint bornerInferieur(double min, boolean estInclut) {
        List<Intervalle> bornesResultantes = new ArrayList<>();
        for (Intervalle intervalle : this.intervalles) {
            double borneInf = intervalle.getBorneInferieur();
            double borneSup = intervalle.getBorneSuperieur();
            boolean fermeAGauche = intervalle.isEstFermeAGauche();
            boolean fermeADroite = intervalle.isEstFermeADroite();
            if (borneInf > min || (borneInf == min && fermeAGauche && estInclut)) {
                // Intervalle complètement au-dessus de min
                bornesResultantes.add(intervalle);
            } else if (borneSup > min) {
                // L'intervalle doit être tronqué à min
                bornesResultantes.add(new Intervalle(
                        min,
                        estInclut,
                        borneSup,
                        fermeADroite
                ));
            }
            // Sinon, on ignore l'intervalle (il est complètement en dessous de min)
        }
        return new EnsembleDisjoint(bornesResultantes);
    }

    /**
     * Construit un ensemble disjoint qui represente l'ensemble image d'une fonction linéaire qui utilise l'ensemble
     * courant comme pre-image.
     * <p>
     * Comment faire : pour chaque intervalle de l'ensemble courant,
     * 1 - Appliquer la fonction aux bornes de l'intervalle.
     * 2 - Construire un intervalle utilisant comme borne les resultats calcule en (1).
     * 3 - Ajouter l'intervalle calcule en (2) dans l'ensemble resultant.
     * </p>
     * <p>
     * Par exemple, si nous avons l'ensemble disjoint : { [1,2], ]4,7] }
     * et la fonction lineaire : ( x ) -> 2x + 1
     * Cela nous donne l'ensemble disjoint : { [3,5], ]9,15] }
     * </p>
     * <p>
     * REMARQUE IMPORTANTE : certaine fonction lineaire vont vous demander d'inverser les bornes des intervalles.
     * Par exemple, applique la fonction lineaire ( x ) -> 2 - x
     * </p>
     *
     * @param fctLineaire La fonction lineaire qui sera applique.
     *                    precondition : cette fonction doit etre lineaire.
     * @return L'ensemble image de la fonction lineaire.
     */
    public EnsembleDisjoint appliquerFctLineaire(DoubleUnaryOperator fctLineaire) {
        List<Intervalle> nouveauxIntervalles = new ArrayList<>();

        for (Intervalle intervalle : this.intervalles) {
            // Appliquer la fonction linéaire aux bornes
            double borneInfImage = fctLineaire.applyAsDouble(intervalle.getBorneInferieur());
            double borneSupImage = fctLineaire.applyAsDouble(intervalle.getBorneSuperieur());

            // Déterminer les nouvelles bornes et leur ordre
            boolean inverse = borneInfImage > borneSupImage;
            double borneInf = inverse ? borneSupImage : borneInfImage;
            double borneSup = inverse ? borneInfImage : borneSupImage;

            // Déterminer les fermetures des bornes
            boolean fermeAGauche = inverse ? intervalle.isEstFermeADroite() : intervalle.isEstFermeAGauche();
            boolean fermeADroite = inverse ? intervalle.isEstFermeAGauche() : intervalle.isEstFermeADroite();

            // Créer le nouvel intervalle
            nouveauxIntervalles.add(new Intervalle(borneInf, fermeAGauche, borneSup, fermeADroite));
        }

        // Retourner un nouvel EnsembleDisjoint
        return new EnsembleDisjoint(nouveauxIntervalles);
    }

    /**
     * Retourne la liste des intervalles disjoints de l'ensemble.  Cette liste est en ordre croissant.
     * <p>
     * Cela implique que la liste L retournee repond a l'invariant suivant :
     * Soit L = { r_1, r_2, ..., r_i, ..., r_n }
     * Alors, pour tout i entre 1 et (n-1), r_i.borneSuperieur <= r_(i+1).borneInferieur.
     * </p>
     *
     * @return La liste trie des intervalles comprit dans l'ensemble disjoint.  Si l'ensemble disjoint
     * est vide, alors la liste retournee sera vide, mais non null.
     */
    public List<Intervalle> getIntervallesTries() {
        if (this.intervalles.isEmpty()) {
            return new ArrayList<>();
        }
        // Trier les intervalles par borne inférieure, puis par borne supérieure
        List<Intervalle> intervallesTries = new ArrayList<>(this.intervalles);
        intervallesTries.sort((a, b) -> {
            int compareBorneInf = Double.compare(a.getBorneInferieur(), b.getBorneInferieur());
            if (compareBorneInf != 0) {
                return compareBorneInf;
            }
            return Boolean.compare(!a.isEstFermeAGauche(), !b.isEstFermeAGauche()); // Fermé d'abord
        });

        // Fusionner les intervalles qui se chevauchent ou se touchent
        List<Intervalle> fusionnes = new ArrayList<>();
        Intervalle courant = intervallesTries.getFirst();

        for (int i = 1; i < intervallesTries.size(); i++) {
            Intervalle suivant = intervallesTries.get(i);

            if (seChevauchentOuSeTouchent(courant, suivant)) {
                // Fusionner les deux intervalles
                courant = fusionner(courant, suivant);
            } else {
                fusionnes.add(courant);
                courant = suivant;
            }
        }

        // Ajouter le dernier intervalle
        fusionnes.add(courant);

        return fusionnes;
    }

    /**
     * Vérifie si deux intervalles se chevauchent ou se touchent.
     * <p>
     * Deux intervalles se chevauchent si une partie de leurs valeurs se superpose.
     * Deux intervalles se touchent si leurs bornes adjacentes sont incluses
     * dans au moins un des intervalles.
     * </p>
     *
     * @param a Le premier intervalle. Précondition : {@code a != null}.
     * @param b Le second intervalle. Précondition : {@code b != null}.
     * @return {@code true} si les deux intervalles se chevauchent ou se touchent,
     *         {@code false} sinon.
     *
     * @throws IllegalArgumentException Si l'un des intervalles est null.
     */
    private boolean seChevauchentOuSeTouchent(Intervalle a, Intervalle b) {
        boolean seTouchent =
                (a.getBorneSuperieur() == b.getBorneInferieur() && (a.isEstFermeADroite() || b.isEstFermeAGauche())) ||
                        (b.getBorneSuperieur() == a.getBorneInferieur() && (b.isEstFermeADroite() || a.isEstFermeAGauche()));
        boolean seChevauchent = a.getBorneSuperieur() > b.getBorneInferieur();
        return seTouchent || seChevauchent;
    }

    /**
     * Fusionne deux intervalles qui se chevauchent ou se touchent.
     * <p>
     * Les bornes du nouvel intervalle sont calculées en fonction des bornes des deux intervalles :
     * - La borne inférieure est celle du premier intervalle.
     * - La borne supérieure est la plus grande des deux bornes supérieures.
     * - Les fermetures (inclusivité) des bornes sont ajustées pour inclure toutes les valeurs des intervalles.
     * </p>
     *
     * @param a Le premier intervalle. Précondition : {@code a != null}.
     * @param b Le second intervalle. Précondition : {@code b != null}.
     * @return Un nouvel intervalle résultant de la fusion des deux intervalles.
     *
     * @throws IllegalArgumentException Si l'un des intervalles est null ou s'ils ne se chevauchent pas ou ne se touchent pas.
     */
    private Intervalle fusionner(Intervalle a, Intervalle b) {
        double borneInf = a.getBorneInferieur();
        boolean fermeAGauche = a.isEstFermeAGauche();

        double borneSup = Math.max(a.getBorneSuperieur(), b.getBorneSuperieur());
        boolean fermeADroite = (borneSup == a.getBorneSuperieur() ? a.isEstFermeADroite() : b.isEstFermeADroite())
                || (borneSup == b.getBorneSuperieur() && b.isEstFermeADroite());

        return new Intervalle(borneInf, fermeAGauche, borneSup, fermeADroite);
    }
}
