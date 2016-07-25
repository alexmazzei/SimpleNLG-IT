package tutorial.italian;

import java.util.List;

import simplenlg.features.Feature;
import simplenlg.features.Form;
import simplenlg.features.Tense;
import simplenlg.features.italian.ItalianFeature;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.italian.ITXMLLexicon;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.realiser.Realiser;

public class testVP_Passive {
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		
		//prova lessico italiano, 
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		String output = "";
		//realiser.setDebugMode(true);
		
		SPhraseSpec passive = factory.createClause("Marco", "leggere");
		NPPhraseSpec object = factory.createNounPhrase("il", "libro");
		passive.setObject(object);
		//riflessivo.setIndirectObject("io");
		passive.setFeature(Feature.TENSE, Tense.PRESENT);
		passive.setFeature(Feature.PASSIVE, true);
		output = realiser.realiseSentence(passive);
		System.out.println(output);
	}
}
