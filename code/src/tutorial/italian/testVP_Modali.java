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

public class testVP_Modali {
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
		
		SPhraseSpec clauseModale = factory.createClause("io", "vendere");
		clauseModale.setFeature(Feature.MODAL, "potere");
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
		
		clauseModale.setFeature(Feature.NEGATED, true);
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
		
		clauseModale.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
		
		clauseModale.setFeature(Feature.TENSE, Tense.PRESENT);
		clauseModale.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
		
		clauseModale.setFeature(Feature.TENSE, Tense.CONDITIONAL);
		clauseModale.setFeature(Feature.PERFECT, false);
		output = realiser.realiseSentence(clauseModale);
		System.out.println(output);
	}
}
