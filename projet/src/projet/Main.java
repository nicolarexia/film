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
				unFilm.genre[0]=getSerie(i);
				unFilm.monFilm.add(getAnnee(i));
			}			
			i++;
		}
		
		addDirector();
		addActor();
		addGenre();
		defGenre();
		
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
		
		separer();
		convert();
		addVu();
		
		//partie traitement des films vus par l'utilisateur
		
		creerListePourConseiller();
		int nbConseilMax = liste.size()-nbMax(numerosFilmsVus);
		Scanner scan = new Scanner(System.in);
		System.out.println("Combien de films devons-nous vous conseiller ? (Maximum : "+nbConseilMax+")");
		int temp = scan.nextInt();	
		Film.nbConseil = temp;
		scan.close();
		sc.close();				
		calculerNote();
		rangerFilm();
		afficherResultat();
			    
	}
	
	//calcule le nombre de films différents vus par l'utilisateur
	public static int nbMax(ArrayList<Integer> monArray) {
		int nb = monArray.size();
		for (int i=0; i<monArray.size()-1;i++) {
			if (monArray.get(i).equals(monArray.get(i+1))) {
				nb--;
			}
		}
		return nb;
	}
		
	//renvoie le titre du film
	public static String getTitre(int i) {
		return arraytxt.get(i+2).substring(arraytxt.get(i+2).indexOf('.')+3,arraytxt.get(i+2).indexOf('(')-1);
	}
	
	//renvoie un booleen vrai si c'est une série
	public static Boolean getSerie(int i){
		return arraytxt.get(i+2).contains("TV Series");
	}
	
	//renvoie l'annee de parution du film
	public static String getAnnee(int i) {
		return arraytxt.get(i+2).substring(arraytxt.get(i+2).indexOf('(')+1,arraytxt.get(i+2).indexOf('(')+5);
	}
	
	//ajoute le réalisateur		
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
	
	//rajoute les genres du film
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
	
	//rajoute les acteurs
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
	
	//definit les genres
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
			liste.get(i).genre[18] = liste.get(i).monFilm.get(3).contains("Music");
			i++;			
		}
	}
	
	//sépare en String ne contenant que des chiffres le String entré par l'utilisateur
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
	
	//converti les String en Integer
	public static void convert() {
		for(int i=0;i<toConvert.size();i++) {
			numerosFilmsVus.add(Integer.parseInt(toConvert.get(i))-1);
		}
	}
	
	//ajoute à la liste vuUtil les films vus par l'utilisateur	
	public static void addVu() {
		for(int i=0;i<numerosFilmsVus.size();i++) {
			vuUtil.add(liste.get(numerosFilmsVus.get(i)));
		}
	}
		
}


