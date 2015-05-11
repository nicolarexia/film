package projet;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Film {
	
	protected static ArrayList<String> arraytxt = new ArrayList<String>(); //contient les lignes du txt source
	protected static String rep; //contient la chaine renvoyée par l'utilisateur
	protected static ArrayList<String> toConvert = new ArrayList<String>(); //contient les chiffres renvoyés par l'utilisateur sous forme de String
	protected static ArrayList<Integer> numerosFilmsVus = new ArrayList<Integer>(); //contient les numéros (int) des films vus par l'utilsateur
	
	public static void main(String[] args) throws IOException {
		
		//partie extraction des informations contenues dans le txt
		
		File inputFile = new File("txt_source.txt");
	    BufferedReader lecteur = null;
		String ligne;
		
	    try
	    {
	    	lecteur = new BufferedReader(new FileReader(inputFile));
	    	while ((ligne = lecteur.readLine()) != null)
	        {
	            arraytxt.add(ligne);
	        }
	    	 lecteur.close();
	    }
	    catch(FileNotFoundException e) {
	    	System.out.println("Erreur lecture");
	    } 
	       
	 
	    int i = 0;
	    
		while (i < arraytxt.size()-2) {
			
			if(i==0) {
				Film unFilm = new Film();
				liste.add(unFilm);
				unFilm.monFilm.add(arraytxt.get(i).substring(arraytxt.get(i).indexOf('.')+3,arraytxt.get(i).indexOf('(')-1));
				unFilm.monFilm.add(arraytxt.get(i).substring(arraytxt.get(i).indexOf('(')+1,arraytxt.get(i).indexOf('(')+5));
			} else if (arraytxt.get(i).length() == 0 && arraytxt.get(i+1).length() == 0) {
				Film unFilm = new Film();
				liste.add(unFilm);
				unFilm.monFilm.add(getTitre(i));
				unFilm.monFilm.add(getAnnee(i));
			}			
			i++;
		}
		
		addDirector();
		addActor();
		addGenre();
		defGenre();
		addType();
		
		//test fonctionnement de l'extraction
		/*for(int j=0;j<liste.size();j++) {
			System.out.println(liste.get(j).monFilm.get(0)); //Title
			System.out.println(liste.get(j).monFilm.get(1)); //Year
			System.out.println(liste.get(j).monFilm.get(2)); //Director
			System.out.println(liste.get(j).monFilm.get(3)); //Gender
			System.out.println(liste.get(j).genre[0]); //TV Series
		}*/
		
		
		//test Acteurs
		/*for (int j=0;j<liste.size();j++) {
			for (int i1=0;i1<liste.get(j).mesActeurs.size();i1++) {
				System.out.println(liste.get(j).mesActeurs.get(i1));	
			}
		}*/
		
		//presentation des films à l'utilisateur
		
		int cpt=1;
		for (int k=0;k<liste.size();k++) {
			System.out.println(cpt+". "+liste.get(k).monFilm.get(0));
			cpt++;
		}
		System.out.println(" ");
		
		//partie acquisition des films vus par l'utilisateur
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez entrer les numéros des films de la liste précédente que vous avez vu : (a/b/c/d)");
		rep = '$'+sc.nextLine()+'*';
		sc.close();
		separer();
		convert();
		addVu();
		
		//test de l'acquisition		
		/*for(int j=0; j<toConvert.size();j++) {
			System.out.println(toConvert.get(j));
			System.out.println(numerosFilmsVus.get(j));
		}
		for (int j=0; j<numerosFilmsVus.size();j++) {
			System.out.println(liste.get(numerosFilmsVus.get(j)));
		*/

		//partie trie des films
		
		creerListePourConseiller();
		
		//test création listePourConseiller
		/*for(int j=0; j<listePourConseiller.size();j++) {
			System.out.println(listePourConseiller.get(j));
		}
		*/
		
		evaluer();
		
		//test remplissage de note[]
		/*for (int j=0; j<note.length;j++) {
			System.out.println(note[j]);
		}*/
		
		System.out.println(" ");
		System.out.println("Nous vous conseillons les films suivant:\n");
		afficherConseil();
			    
	}
		
	
	public static String getTitre(int i) {
		return arraytxt.get(i+2).substring(arraytxt.get(i+2).indexOf('.')+3,arraytxt.get(i+2).indexOf('(')-1);
	}
	
	public static String getAnnee(int i) {
		return arraytxt.get(i+2).substring(arraytxt.get(i+2).indexOf('(')+1,arraytxt.get(i+2).indexOf('(')+5);
	}
		
	public static void addDirector() {	
		int i=0;
		int j=0;
		while(i<arraytxt.size()) {
			if(arraytxt.get(i).contains("With :") && !arraytxt.get(i-1).contains("Director :")) {
				liste.get(j).monFilm.add("Non renseigne");				
				if(j<liste.size()-1)
					j++;
			} else if (arraytxt.get(i).contains("Director") && arraytxt.get(i+1).contains("With :")) {
				liste.get(j).monFilm.add(arraytxt.get(i).substring(arraytxt.get(i).indexOf(':')+2));
				if(j<liste.size()-1)
						j++;
			}
			i++;
		}
	}
	
	public static void addGenre() {
		int i=0;
		int j=0;
		while(i<arraytxt.size()) {
			if(arraytxt.get(i).contains("With :")) {
				liste.get(j).monFilm.add(arraytxt.get(i+1));
				j++;
			}
			i++;
		}
	}
	
	
	public static void addActor() {
		int i=0;
		int k = 0;
		while(i<arraytxt.size()) {
			if(arraytxt.get(i).contains("With :")) {
				String tmp = new String();
				tmp = arraytxt.get(i).substring(arraytxt.get(i).indexOf(':')+2)+",fin";
				if (tmp.indexOf(',') == -1) {
					liste.get(k).mesActeurs.add(tmp);
				} else {
					while ((tmp.indexOf(',') != -1)) {
						liste.get(k).mesActeurs.add(tmp.substring(0,tmp.indexOf(',')));
						tmp = tmp.substring(tmp.indexOf(',')+2);
					}
				}
			k++;
			}
			i++;
		}
	}
	
	public static void defGenre() {
		int i=0;
		while(i<liste.size()) {
			liste.get(i).genre[1] = liste.get(i).monFilm.get(3).contains("Action");
			liste.get(i).genre[2] = liste.get(i).monFilm.get(3).contains("Crime");
			liste.get(i).genre[3] = liste.get(i).monFilm.get(3).contains("Thriller");
			liste.get(i).genre[4] = liste.get(i).monFilm.get(3).contains("Drama");
			liste.get(i).genre[5] = liste.get(i).monFilm.get(3).contains("Horror");
			liste.get(i).genre[6] = liste.get(i).monFilm.get(3).contains("Adventure");
			liste.get(i).genre[7] = liste.get(i).monFilm.get(3).contains("Family");
			liste.get(i).genre[8] = liste.get(i).monFilm.get(3).contains("Fantasy");
			liste.get(i).genre[9] = liste.get(i).monFilm.get(3).contains("Romance");
			liste.get(i).genre[10] = liste.get(i).monFilm.get(3).contains("Sci-Fi");
			liste.get(i).genre[11] = liste.get(i).monFilm.get(3).contains("Animation");
			liste.get(i).genre[12] = liste.get(i).monFilm.get(3).contains("Comedy");
			liste.get(i).genre[13] = liste.get(i).monFilm.get(3).contains("Mystery");
			liste.get(i).genre[14] = liste.get(i).monFilm.get(3).contains("History");
			liste.get(i).genre[15] = liste.get(i).monFilm.get(3).contains("War");
			liste.get(i).genre[16] = liste.get(i).monFilm.get(3).contains("Sport");
			liste.get(i).genre[17] = liste.get(i).monFilm.get(3).contains("Biography");				
			i++;			
		}
	}
	
	public static void addType() {
		int i=0;
		int j=0;
		while(i<arraytxt.size()-2) {
			if(arraytxt.get(i).length() == 0 && arraytxt.get(i+1).length() == 0) {
				liste.get(j).genre[0] = arraytxt.get(i+2).contains("TV Series");					 
			if(j<liste.size())
				j++;
			}
			i++;
		}
	}
	
	public static void separer() {
		int i=0;
		while(rep.charAt(i) != '*') {
			if(rep.charAt(i) == '$' || rep.charAt(i) == '/') {
				String tmp = new String();
				int j=i+1;
				while(rep.charAt(j) != '/') {
					if (rep.charAt(j) != '*') {
						tmp += rep.charAt(j);
					}
					if (j<rep.length()-1) {
						j++;
					} else {
						break;
					}
				}
				toConvert.add(tmp);
			}
		i++;
		}
	}
	
	public static void convert() {
		for(int i=0;i<toConvert.size();i++) {
			numerosFilmsVus.add(Integer.parseInt(toConvert.get(i))-1);
		}
	}
	
	public static void addVu() {
		for(int i=0;i<numerosFilmsVus.size();i++) {
			vuUtil.add(liste.get(numerosFilmsVus.get(i)));
		}
	}
		
}


