package projet;

import java.util.*;

public class Film {	
	
	protected static ArrayList<Film> liste = new ArrayList<Film>(); //liste des films du fichier source
	protected static ArrayList<Film> vuUtil = new ArrayList<Film>(); //liste des films vus par l'utilisateur
	protected static ArrayList<Film> listePourConseiller = new ArrayList<Film>(); //liste des films à conseiller
	protected static int note[]; //tableau contenant les "notes" de chaque film non vu par l'utilisateur pour la comparaison
	protected static int filmRange[]; //tableau contenant les notes des films non vus par odre décroissant
	protected static int nbConseil; //nombre de film que l'utilisateur veut recevoir en conseil
	
	protected ArrayList<String> monFilm;
	protected Boolean genre[];
	protected ArrayList<String> mesActeurs;
		
	public Film() {	
		
		this.monFilm = new ArrayList<String>();
		this.genre = new Boolean[19];
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
	
	//cree la listePourConseiller
	public static void creerListePourConseiller() {
		for (int i=0;i<liste.size();i++) {
			listePourConseiller.add(liste.get(i));
		}
		for (int i=0;i<vuUtil.size();i++) {
			listePourConseiller.remove(vuUtil.get(i));
		}
	}
	
	//attribue à chaque film non vu une note en fonction d'un film vu
	public static void calculerNote() {
		
		//on initialise le tableau à 0, en lui donnant la taille de listePourConseiller
		note = new int[listePourConseiller.size()];
		for (int i=0;i<note.length;i++) {
			note[i]=0;
		}
		
		//on ajoute +3 aux films ayant un réalisateur en commun avec la liste des films vus (inutile pour le moment : cas impossible avec notre txt_source)
		for (int i=0; i<vuUtil.size();i++) {
			for (int j=0;j<listePourConseiller.size();j++) {
				if(listePourConseiller.get(j).monFilm.get(2).contains(vuUtil.get(i).monFilm.get(2))) {
					note[j]+=3;
				}
			}
		}
		
		//on ajoute +3 aux films ayant un acteur en commun avec la liste des films vus
		for (int i=0; i<vuUtil.size();i++) {		
			for (int j=0;j<listePourConseiller.size();j++) {
				for (int k=0;k<vuUtil.get(i).mesActeurs.size();k++) {
					for (int l=0; l<listePourConseiller.get(j).mesActeurs.size();l++) {
						if(listePourConseiller.get(j).mesActeurs.get(l).equals(vuUtil.get(i).mesActeurs.get(k))) {
							note[j]+=3;
						}
					}
					
				}
			}				
		}
		
		//on ajoute +1 aux films ayant un genre en commun avec la liste des films vus
		for (int i=0;i<vuUtil.size();i++) { 
				for(int j=0;j<listePourConseiller.size();j++) {
					for(int k=0;k<listePourConseiller.get(j).genre.length;k++) {
						if(listePourConseiller.get(j).genre[k] == vuUtil.get(i).genre[k]){
							note[j]++;
						}
					}
					if(listePourConseiller.get(j).genre[0] == vuUtil.get(i).genre[0]){
						note[j]++; //+1 s'il s'agit d'une série et que l'utilisateur a vu des séries
					}
				}				
		}
	}
	
	//ordonne les notes par ordre décroissant, filmRange contient les numéros des films à renvoyer
	public static void rangerFilm() {
		boolean a = true;
		int tmp;
		int fr;
		filmRange = new int[note.length];
		
		for (int i=0;i<filmRange.length;i++) {
			filmRange[i]=i;
		}
		
		while(a) {
			a=false;
			for (int i = 0; i<note.length-1;i++) {
				if(note[i]<note[i+1]) {
					tmp = note[i+1];
					fr = filmRange[i+1];
					note[i+1] = note[i];
					filmRange[i+1] = filmRange[i];
					note[i] = tmp;
					filmRange[i] = fr;
					a=true;
				}
			}
		}
	}
	
	public static void afficherResultat() {
		if(nbConseil<listePourConseiller.size()) {
			System.out.println("Nous vous conseillons les films suivant (par ordre décroissant de pertinence):\n");
			for (int i=0;i<nbConseil;i++) {
				System.out.println(listePourConseiller.get(filmRange[i]).monFilm.get(0));
			}
		} else {
			System.out.println("Vous avez saisi un nombre de films à conseiller trop important. Voici ce que nous pouvons vous conseiller (par ordre décroissant de pertinence) :\n");
			for (int i=0;i<listePourConseiller.size();i++) {
				System.out.println(listePourConseiller.get(filmRange[i]).monFilm.get(0));
			}
		}
	}
	
}
	
	
	
	

