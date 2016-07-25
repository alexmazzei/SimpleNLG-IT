
package tutorial.italian;


import simplenlg.features.Gender;
import simplenlg.features.LexicalFeature;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.italian.ITXMLLexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.realiser.Realiser;

public class Example_MorphonologyInAction {
public static void main(String[] args) {
	/*########LESSICO##########*/
	
	//prova lessico italiano, 
	Lexicon lexIta = new ITXMLLexicon();
	
	/*########CREAZIONE FACTORY##########*/
	
	NLGFactory factory = new NLGFactory(lexIta);
	
	/*########CREAZIONE realiser##########*/
	Realiser realiser = new Realiser();
	//realiser.setDebugMode(true);
	String output = null;
	
	NPPhraseSpec sbarco = factory.createNounPhrase("il", "sbarco");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "sogno");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "schiavo");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "schiavo");
	sbarco.setPlural(true);
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "problema");
	sbarco.setFeature(LexicalFeature.GENDER, Gender.MASCULINE);
	sbarco.setPlural(true);
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "sciroppo");
	sbarco.setFeature(LexicalFeature.GENDER, Gender.MASCULINE);
	sbarco.setPlural(true);
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "gnomo");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("un", "gnomo");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("un", "zio");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "casa");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "psicologo");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("il", "uomo");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("la", "auto");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("una", "auto");
	sbarco.setFeature(LexicalFeature.GENDER, Gender.FEMININE);
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("un", "amico");
	sbarco.addPreModifier("bello");
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("capello");
	sbarco.addPreModifier("bello");
	sbarco.setPlural(true);
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	
	sbarco = factory.createNounPhrase("cesto");
	sbarco.addPreModifier("bello");
	//sbarco.setPlural(true);
	output = realiser.realiseSentence(sbarco);
	System.out.println(output);
	}

	
}
