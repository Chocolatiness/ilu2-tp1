package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbetals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbetals);
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois gaulois, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int place = marche.trouverEtalLibre();
		if (place==-1) {
			chaine.append("Il n'a plus de place au marché, " + gaulois.getNom() + " devra revenir demain.\n");
		}
		else {
			chaine.append(gaulois.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit 
					+ ".\nLe vendeur " + gaulois.getNom() + " vend des " + produit + " à l'étal n°" + place);
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalprod = marche.trouverEtal(produit);
		chaine.append("Les vendeurs qui proposent des " + produit + "sont :\n");
		for(int i =0; i<etalprod.length-1;i++) {
			chaine.append("- " + etalprod[i].getVendeur().getNom());
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalalib = marche.trouverVendeur(vendeur);
		int prodvendu = etalalib.getVendu(	);
		int proddebut = etalalib.getDebut();
		String produit = etalalib.getProd();
		chaine.append("Le vendeur " +vendeur.getNom() +"quitte son étal, il a vendu "+ prodvendu + produit 
				+" parmi les "+ proddebut +" qu'il\r\n" + 
				"voulait vendre.");
		etalalib.libererEtal();
		return chaine.toString();
	}
	
	private static class Marche{
		private Etal[] etals;
		private int nbetals; 
		
		private Marche(int nbetals) {
			etals  = new Etal[nbetals];
			this.nbetals = nbetals;
		}
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if (0 <= indiceEtal && indiceEtal < nbetals-1 && !etals[indiceEtal].isEtalOccupe()) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
			
		}
		
		private int trouverEtalLibre() {
			int i = 0;
			int indice = -1;
			while(i != nbetals-1 && indice ==-1) {
				if (!etals[i].isEtalOccupe())
					indice = i;
			}
			return indice;
		}
		
		private  Etal[] trouverEtal(String produit) {
			int nbetalsprod = 0;
			for (int i = 0; i<nbetals-1; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit))
					nbetalsprod++;
			}
			Etal[] etalProd = new Etal[nbetalsprod];
			int j = 0;
			for (int i = 0; i<nbetals-1; i++) {
				if (etals[i].contientProduit(produit))
					etalProd[j++] = etals[i];
				}
			return etalProd;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			int i = 0;
			Gaulois vendeur = null;
			while(i!= nbetals-1 && vendeur == null) {
				if (etals[i].getVendeur() == gaulois)
					vendeur = gaulois;
			}
			return etals[i];
		}
		
		private String afficherMarche() {
			int nbEtalVide = 0;
			for(int i = 0; i<nbetals-1; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				}
				else {
					nbEtalVide++;
				}
			}
			return "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n";
		}
		
	}
	
	
}



