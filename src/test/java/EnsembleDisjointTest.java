

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.example.EnsembleDisjoint;
import org.example.Intervalle;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class EnsembleDisjointTest {

    @Test
    void appartient1() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                    new Intervalle( 4.0, true, 5.0, true )
                )
        );

        assertFalse( e.appartient( 3.5 ) );
        assertTrue( e.appartient( 4.0 ) );
        assertTrue( e.appartient( 4.5 ) );
        assertTrue( e.appartient( 5.0 ) );
        assertFalse( e.appartient( 5.5 ) );
    }

    @Test
    void appartient2() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 4.0, false, 5.0, true )
                )
        );

        assertFalse( e.appartient( 3.5 ) );
        assertFalse( e.appartient( 4.0 ) );
        assertTrue( e.appartient( 4.5 ) );
        assertTrue( e.appartient( 5.0 ) );
        assertFalse( e.appartient( 5.5 ) );
    }

    @Test
    void appartient3() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 4.0, false, 5.0, false )
                )
        );

        assertFalse( e.appartient( 3.5 ) );
        assertFalse( e.appartient( 4.0 ) );
        assertTrue( e.appartient( 4.5 ) );
        assertFalse( e.appartient( 5.0 ) );
        assertFalse( e.appartient( 5.5 ) );
    }

    @Test
    void appartient4() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 4.0, true, 5.0, false )
                )
        );

        assertFalse( e.appartient( 3.5 ) );
        assertTrue( e.appartient( 4.0 ) );
        assertTrue( e.appartient( 4.5 ) );
        assertFalse( e.appartient( 5.0 ) );
        assertFalse( e.appartient( 5.5 ) );
    }

    @Test
    void appartient5() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 4.0, true, 5.0, true ),
                        new Intervalle( 7.0, true, 10.0, true )
                )
        );

        assertFalse( e.appartient( 3.5 ) );
        assertTrue( e.appartient( 4.5 ) );
        assertFalse( e.appartient( 5.5 ) );
        assertTrue( e.appartient( 8.0 ) );
        assertFalse( e.appartient( 11.0 ) );
    }

    @Test
    void appartient6() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( -10.0, true, -7.0, true ),
                        new Intervalle( -5.0, true, -4.0, true )
                )
        );

        assertFalse( e.appartient( -3.5 ) );
        assertTrue( e.appartient( -4.5 ) );
        assertFalse( e.appartient( -5.5 ) );
        assertTrue( e.appartient( -8.0 ) );
        assertFalse( e.appartient( -11.0 ) );
    }

    @Test
    void unionIntervalle1() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 5.0, true, 8.0, false );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false ),
                        new Intervalle( 5.0, true, 8.0, false )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle2() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 3.0, true, 8.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, true, 8.0, true )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle3() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 2.0, false, 4.0, false );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle4() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 1.0, true, 3.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 1.0, true, 4.0, false )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle5() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 0.0, true, 2.0, false );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 0.0, true, 2.0, false ),
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle6() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 1.0, true, 8.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 1.0, true, 8.0, true )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle7() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 2.5, true, 3.5, false );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle8() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 2.0, false, 8.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, true, 8.0, true )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle9() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 2.0, false, 3.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle10() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 3.0, false, 4.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, true )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionIntervalle11() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 1.0, false, 4.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 1.0, false, 4.0, true )
                )
        );

        assertEquals( attendu, e.union( i ).getIntervallesTries() );
    }

    @Test
    void unionEnsemble1() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        EnsembleDisjoint e2 = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 3.0, false, 9.0, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 10.0, false )
                )
        );

        assertEquals( attendu, e.union( e2 ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle1() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 5.0, true, 8.0, false );
        List< Intervalle > attendu = new ArrayList<>(
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle2() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 3.0, true, 8.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 3.0, true, 4.0, false )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle3() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 2.0, false, 4.0, false );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle4() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 1.0, true, 3.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, true, 3.0, true )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle5() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 0.0, true, 2.0, false );
        List< Intervalle > attendu = new ArrayList<>(
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle6() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 1.0, true, 8.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle7() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 2.5, true, 3.5, false );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.5, true, 3.5, false )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle8() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, true, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 2.0, false, 8.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle9() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 2.0, false, 3.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 3.0, true )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle10() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 3.0, false, 4.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 3.0, false, 4.0, false )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionIntervalle11() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );
        Intervalle i = new Intervalle( 1.0, false, 4.0, true );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );

        assertEquals( attendu, e.intersection( i ).getIntervallesTries() );
    }

    @Test
    void intersectionEnsemble1() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        EnsembleDisjoint e2 = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 3.0, false, 9.0, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 3.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 9.0, false )
                )
        );

        assertEquals( attendu, e.intersection( e2 ).getIntervallesTries() );
    }

    @Test
    void bornerSuperieur1() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 9.0, false )
                )
        );

        assertEquals( attendu, e.bornerSuperieur( 9.0, false ).getIntervallesTries() );
    }

    @Test
    void bornerSuperieur2() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false )
                )
        );

        assertEquals( attendu, e.bornerSuperieur( 5.0, false ).getIntervallesTries() );
    }

    @Test
    void bornerInferieur1() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 3.0, true, 4.0, false ),
                        new Intervalle( 7.0, true, 10.0, false )
                )
        );

        assertEquals( attendu, e.bornerInferieur( 3.0, true ).getIntervallesTries() );
    }

    @Test
    void bornerInferieur2() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 7.0, true, 10, false )
                )
        );

        assertEquals( attendu, e.bornerInferieur( 5.0, false ).getIntervallesTries() );
    }

    @Test
    void appliquerFctLineaire1() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );

        assertEquals( attendu, e.appliquerFctLineaire( ( x ) -> x ).getIntervallesTries() );
    }

    @Test
    void appliquerFctLineaire2() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( 3.0, false, 7.0, false ),
                        new Intervalle( 13.0, true, 19.0, false )
                )
        );

        assertEquals( attendu, e.appliquerFctLineaire( ( x ) -> 2.0 * x - 1.0 ).getIntervallesTries() );
    }

    @Test
    void appliquerFctLineaire3() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle( 2.0, false, 4.0, false ),
                        new Intervalle( 7.0, true, 10, false )
                )
        );
        List< Intervalle > attendu = new ArrayList<>(
                Arrays.asList(
                        new Intervalle( -6.0, false, -3, true ),
                        new Intervalle( 0.0, false, 2.0, false )
                )
        );

        assertEquals( attendu, e.appliquerFctLineaire( ( x ) -> - x + 4.0 ).getIntervallesTries() );
    }

    /**
     * Nos propres cas de tests
     */
    @Test
    void appartientEnsembleVide() {
        EnsembleDisjoint e = new EnsembleDisjoint();
        assertFalse(e.appartient(5.0));
    }

    @Test
    void appartientIntervalleVide() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(3.0, false, 3.0, false) // Intervalle vide
                )
        );
        assertFalse(e.appartient(3.0));
    }

    @Test
    void unionEnsembleVide() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(2.0, true, 4.0, false)
                )
        );
        EnsembleDisjoint e2 = new EnsembleDisjoint(); // Ensemble vide
        List<Intervalle> attendu = Arrays.asList(
                new Intervalle(2.0, true, 4.0, false)
        );
        assertEquals(attendu, e.union(e2).getIntervallesTries());
    }

    @Test
    void unionIntervallesAdjacents() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(2.0, true, 4.0, false)
                )
        );
        Intervalle i = new Intervalle(4.0, true, 6.0, false);
        List<Intervalle> attendu = Arrays.asList(
                new Intervalle(2.0, true, 6.0, false)
        );
        assertEquals(attendu, e.union(i).getIntervallesTries());
    }

    @Test
    void intersectionIntervalleVide() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(2.0, true, 4.0, false)
                )
        );
        Intervalle i = new Intervalle(3.0, false, 3.0, false); // Intervalle vide
        List<Intervalle> attendu = new ArrayList<>();
        assertEquals(attendu, e.intersection(i).getIntervallesTries());
    }

    @Test
    void intersectionEnsembleVide() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(2.0, true, 4.0, false)
                )
        );
        EnsembleDisjoint e2 = new EnsembleDisjoint(); // Ensemble vide
        List<Intervalle> attendu = new ArrayList<>();
        assertEquals(attendu, e.intersection(e2).getIntervallesTries());
    }

    @Test
    void bornerSuperieurVide() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(2.0, true, 4.0, false)
                )
        );
        List<Intervalle> attendu = new ArrayList<>(); // Résultat vide
        assertEquals(attendu, e.bornerSuperieur(1.0, false).getIntervallesTries());
    }

    @Test
    void bornerInferieurMultipleIntervalles() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(2.0, true, 4.0, false),
                        new Intervalle(5.0, true, 8.0, true)
                )
        );
        List<Intervalle> attendu = Arrays.asList(
                new Intervalle(5.0, true, 8.0, true)
        );
        assertEquals(attendu, e.bornerInferieur(5.0, true).getIntervallesTries());
    }

    @Test
    void appliquerFctLineaireConstante() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(2.0, true, 4.0, false)
                )
        );
        List<Intervalle> attendu = Arrays.asList(
                new Intervalle(3.0, true, 3.0, false) // Intervalle vide
        );
        assertEquals(attendu, e.appliquerFctLineaire((x) -> 3.0).getIntervallesTries());
    }

    @Test
    void appliquerFctLineaireInverse() {
        EnsembleDisjoint e = new EnsembleDisjoint(
                Arrays.asList(
                        new Intervalle(2.0, true, 4.0, false)
                )
        );
        List<Intervalle> attendu = Arrays.asList(
                new Intervalle(-4.0, false, -2.0, true)
        );
        assertEquals(attendu, e.appliquerFctLineaire((x) -> -x).getIntervallesTries());
    }
}
