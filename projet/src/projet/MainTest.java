package projet;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class MainTest extends Main {

	@Test
	public void testCreerListePourConseiller() { 
		Film monFilm = new Film();
		ArrayList<Film> listePourConseiller = new ArrayList<Film>();
		ArrayList<Film> vuUtil = new ArrayList<Film>();
		ArrayList<Film> liste = new ArrayList<Film>();
		
		//  1-- si l'utilisateur a vu un seul film, listePourConseiller retourne-t-il tous les films sauf un ? (après test : oui) ---
		liste.add(monFilm);
		vuUtil.add(monFilm);
		creerListePourConseiller();
		assertTrue("retourne tout sauf monFilm", listePourConseiller.size()==liste.size()-1);
		
		
		//2-- Et si le film que l'utilisateur a vu ne fait pas partie de liste ? après test : retourne tous les films de liste ---
		liste.remove(monFilm); 
		vuUtil.add(monFilm);
		creerListePourConseiller();
		assertTrue("retourne tous les éléments", listePourConseiller.size()==liste.size());
		
		
		//3-- si l'utilisateur n'a rien vu, listePourConseiller contient-t-il tous les films de la liste ? (après test : oui)  ---
		
		int i=0;
		for(i=0;i<liste.size();i++) {
			vuUtil.remove(liste.get(i));			//vuUtil ne contient aucun film de liste
		}
		creerListePourConseiller();
		assertTrue("retourne tous les éléments", listePourConseiller.size()==liste.size());
				
		
		//4-- si l'utilisateur a vu tous les films, listePourConseiller est-il vide ? (après test : oui) ---
		int i1=0;
		for(i1=0;i1<liste.size();i1++) {
			vuUtil.remove(liste.get(i1));
		}
		creerListePourConseiller();
		assertTrue("ne retourne aucun élément", listePourConseiller.isEmpty());		
		
		//5-- si liste est vide, listePourConseiller est-il vide ? (après test : oui) ---
		liste.remove(monFilm);
		creerListePourConseiller();
		assertTrue("aucun élément dans liste", liste.isEmpty());
	}

}