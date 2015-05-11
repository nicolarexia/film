package projet;

import java.util.*;

public class Film {	
	
	protected static ArrayList<Film> liste = new ArrayList<Film>(); //liste des films du fichier source
	protected static ArrayList<Film> vuUtil = new ArrayList<Film>(); //liste des films vus par l'utilisateur
	protected static ArrayList<Film> filmsConseilles = new ArrayList<Film>(); //liste des films conseillés
	protected static ArrayList<Film> listePourConseiller = new ArrayList<Film>(); //liste des films pouvant être conseillés (= liste - vuUtil)
	protected static int note[] = new int[18]; //liste contenant les "notes" de chaque film vu par l'utilisateur pour la comparaison
	
	protected ArrayList<String> monFilm;
	protected Boolean genre[];
	protected ArrayList<String> mesActeurs;
		
	public Film() {	
		
		this.monFilm = new ArrayList<String>();
		this.genre = new Boolean[18];
		this.mesActeurs = new ArrayList<String>();
		
		
		/* 0 Serie = false;
		1 Action = false;
		2 Crime = false;
		3 Thriller = false;
		4 Drama = false;
		5 Horror = false;
		6 Adventure = false;
		7 Family = false;
		8 Fantasy = false;
		9 Romance = false;
		10 SciFi = false;
		11 Animation = false;
		12 Comedy = false;
		13 Mystery = false;
		14 History = false;
		15 War = false;
		16 Sport = false;
		17 Biography = false;
		*/
		
	}
	
	public static void creerListePourConseiller() {
		for (int i=0;i<liste.size();i++) {
			listePourConseiller.add(liste.get(i));
		}
		for (int i=0;i<vuUtil.size();i++) {
			listePourConseiller.remove(vuUtil.get(i));
		}
	}
	
	public static void evaluer() { //rempli note[]
		for(int k=0;k<note.length;k++){
			note[k]=0;
		}
		for(int i=0;i<vuUtil.size();i++) {
			for(int j=0;j<18;j++) {
				if(vuUtil.get(i).genre[j]) {
					note[j]++;
				}
			}
		}
	}
	
	public static void afficherConseil() {	
		
		ArrayList<Film> tmp = new ArrayList<Film>();
		
		for (int i=0;i<listePourConseiller.size();i++) { //on initialise la liste des films conseillés avec tous les films que l'utilisateur n'a pas vu
			filmsConseilles.add(listePourConseiller.get(i));
		}
		
		for (int i=0; i<vuUtil.size();i++) { //ajoute à la liste tmp les films ayant les mêmes réalisateur que les films vus
			for (int j=0;j<listePourConseiller.size();j++) {
				if(listePourConseiller.get(j).monFilm.get(2).contains(vuUtil.get(i).monFilm.get(2)) && !tmp.contains(listePourConseiller.get(i))) {
					tmp.add(listePourConseiller.get(i));
				}
			}
		}		
		
		for (int i=0; i<vuUtil.size();i++) { //ajoute ensuite à la liste tmp les films ayant les mêmes acteurs			
			for (int j=0;j<listePourConseiller.size();j++) {
				int cpt=0;
				for (int k=0;k<vuUtil.get(i).mesActeurs.size();k++) {
					for (int l=0; l<listePourConseiller.get(j).mesActeurs.size();l++) {
						if(listePourConseiller.get(j).mesActeurs.get(l).equals(vuUtil.get(i).mesActeurs.get(k))) {
							cpt++;
						}
					}
					if(cpt!=0 && !tmp.contains(listePourConseiller.get(j))) {
						tmp.add(listePourConseiller.get(j));
					}
				}
				
			}
		}
		
		for (int j=0;j<note.length;j++) { //on supprime de la liste des films à conseiller ceux qui n'ont aucuns genres en commun avec les films vus par l'utilisateur
			if (note[j] == 0) {
				for(int i=0;i<listePourConseiller.size();i++) {
					if(listePourConseiller.get(i).genre[j] && filmsConseilles.contains(listePourConseiller.get(i)) ) {
						filmsConseilles.remove(listePourConseiller.get(i));
					}
				}				
			}
		}
		
		for(int j=0;j<tmp.size();j++) { //on supprime de filmsConseilles les films presents dans tmp
				if(filmsConseilles.contains(tmp.get(j))) {
					filmsConseilles.remove(tmp.get(j));
				}			
		}
		
		if(!tmp.isEmpty()) { //on affiche en premier les films de tmp
			for(int j=0;j<tmp.size();j++) {
				System.out.println(tmp.get(j).monFilm.get(0));
			}
		}
		
		System.out.println("Ligne de test");
		
		if(!filmsConseilles.isEmpty()) { //affichage des films conseillés de la liste fimsConseilles
		    for (int k=0;k<filmsConseilles.size();k++) {
		    	System.out.println(filmsConseilles.get(k).monFilm.get(0));
		    }
		} else {
			System.out.println("Aucun film à conseiller");
		}
				
	}
	
	
}		

