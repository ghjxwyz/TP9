package bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MultiPlayerGameTest {

	private PartieMultiJoueurs partie;
	private String[] joueurs;

	@BeforeEach
	public void setUp() {
		partie = new PartieMultiJoueurs();
		joueurs = new String[] { "Pierre", "Paul" };
	}

	@Test
	void testDemarreNouvellePartie() {
		String message = partie.demarreNouvellePartie(joueurs);
		assertEquals("Prochain tir : joueur Pierre, tour n° 1, boule n° 1", message);
	}

	@Test
	void testDemarreNouvellePartieSansJoueurs() {
		assertThrows(IllegalArgumentException.class, () -> {
			partie.demarreNouvellePartie(new String[] {});
		});
		assertThrows(IllegalArgumentException.class, () -> {
			partie.demarreNouvellePartie(null);
		});
	}

	@Test
	void testEnregistreLancer() {
		partie.demarreNouvellePartie(joueurs);
		String message = partie.enregistreLancer(5);
		assertEquals("Prochain tir : joueur Pierre, tour n° 1, boule n° 2", message);
		
		message = partie.enregistreLancer(5);
		assertEquals("Prochain tir : joueur Paul, tour n° 1, boule n° 1", message);
	}

	@Test
	void testScorePour() {
		partie.demarreNouvellePartie(joueurs);
		partie.enregistreLancer(10);
		partie.enregistreLancer(5);
		partie.enregistreLancer(3);
		
		assertEquals(10, partie.scorePour("Pierre"));
		assertEquals(8, partie.scorePour("Paul"));
	}
	
	@Test
	void testScorePourJoueurInconnu() {
		partie.demarreNouvellePartie(joueurs);
		assertThrows(IllegalArgumentException.class, () -> {
			partie.scorePour("Jacques");
		});
	}

	@Test
	void testPartieTerminee() {
		partie.demarreNouvellePartie(new String[]{"Pierre"});
		
		for (int i = 0; i < 11; i++) {
			partie.enregistreLancer(10);
		}
		String message = partie.enregistreLancer(10);
		assertEquals("Partie terminée", message);
	}
    
    @Test
    void testAlternanceJoueurs() {
        partie.demarreNouvellePartie(joueurs);
        
        assertEquals("Prochain tir : joueur Pierre, tour n° 1, boule n° 2", partie.enregistreLancer(1));
        assertEquals("Prochain tir : joueur Paul, tour n° 1, boule n° 1", partie.enregistreLancer(1));
        assertEquals("Prochain tir : joueur Paul, tour n° 1, boule n° 2", partie.enregistreLancer(1));
        assertEquals("Prochain tir : joueur Pierre, tour n° 2, boule n° 1", partie.enregistreLancer(1));
        
        assertEquals("Prochain tir : joueur Pierre, tour n° 2, boule n° 2", partie.enregistreLancer(1));
        assertEquals("Prochain tir : joueur Paul, tour n° 2, boule n° 1", partie.enregistreLancer(1));
    }
}
