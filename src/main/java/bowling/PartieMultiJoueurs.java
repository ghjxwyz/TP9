package bowling;

import java.util.ArrayList;
import java.util.HashMap;

public class PartieMultiJoueurs implements IPartieMultiJoueurs {

    private ArrayList<String> listeJoueurs;
    private HashMap<String, PartieMonoJoueur> parties;
    private int joueurCourantIndex;

    public PartieMultiJoueurs(ArrayList<String> listeJoueurs, HashMap<String, PartieMonoJoueur> parties) {
        this.listeJoueurs = new ArrayList<>();
        this.parties = new HashMap<>();
    }
    
    public PartieMultiJoueurs() {
        this.listeJoueurs = new ArrayList<>();
        this.parties = new HashMap<>();
    }

    @Override
    public String demarreNouvellePartie(String[] joueurs) throws IllegalArgumentException {
        if (joueurs == null || joueurs.length == 0) {
            throw new IllegalArgumentException("Il faut au moins un joueur");
        }

        listeJoueurs.clear();
        parties.clear();

        for (String j : joueurs) {
            listeJoueurs.add(j);
            parties.put(j, new PartieMonoJoueur());
        }
        
        joueurCourantIndex = 0;

        return messageProchainTir();
    }

    @Override
    public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
        if (parties.isEmpty()) {
            throw new IllegalStateException("La partie n'est pas démarrée");
        }

        String joueurCourant = listeJoueurs.get(joueurCourantIndex);
        PartieMonoJoueur partieJoueur = parties.get(joueurCourant);

        boolean rejouer = partieJoueur.enregistreLancer(nombreDeQuillesAbattues);

        if (!rejouer) {
            joueurCourantIndex = (joueurCourantIndex + 1) % listeJoueurs.size();
            
            String prochainJoueur = listeJoueurs.get(joueurCourantIndex);
            if (parties.get(prochainJoueur).estTerminee()) {
                return "Partie terminée";
            }
        }

        return messageProchainTir();
    }

    @Override
    public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
        if (!parties.containsKey(nomDuJoueur)) {
            throw new IllegalArgumentException("Le joueur " + nomDuJoueur + " ne joue pas dans cette partie");
        }
        return parties.get(nomDuJoueur).score();
    }
    
    private String messageProchainTir() {
        String joueurCourant = listeJoueurs.get(joueurCourantIndex);
        PartieMonoJoueur partieJoueur = parties.get(joueurCourant);
        int tour = partieJoueur.numeroTourCourant();
        int boule = partieJoueur.numeroProchainLancer();
        
        return String.format("Prochain tir : joueur %s, tour n° %d, boule n° %d", joueurCourant, tour, boule);
    }
}
