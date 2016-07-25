package tutorial.italian;

//GENERAL 
import simplenlg.features.*;
import simplenlg.framework.*;
import simplenlg.phrasespec.*;
import simplenlg.lexicon.Lexicon;

//ITALIAN
//importo feature italiane
import simplenlg.features.italian.*;
//importo lessico italiano
import simplenlg.lexicon.italian.*;
//importo il realizer francese che richiama i metodi 
//realiseSyntax e realiseMorphology degli elementi linguistici
import simplenlg.realiser.Realiser;

public class Example_Question {
	
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		//realiser.setDebugMode(true);
		String output = null;
		
		
		NPPhraseSpec np_donna = factory.createNounPhrase("il", "donna");
		NPPhraseSpec np_uomo = factory.createNounPhrase("il", "uomo");
		SPhraseSpec proposition = factory.createClause(np_donna, "abbracciare", np_uomo);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(Feature.PASSIVE, true);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(Feature.PASSIVE, false);
		proposition.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHO_SUBJECT);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(Feature.PASSIVE, true);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(Feature.PASSIVE, false);
		proposition.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHO_OBJECT);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(Feature.PASSIVE, true);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		NPPhraseSpec np_mela = factory.createNounPhrase("un", "mela");
		SPhraseSpec proposition2 = factory.createClause(np_donna, "regalare", np_mela);
		proposition2.setIndirectObject(np_uomo);
		proposition2.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHO_INDIRECT_OBJECT);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHAT_OBJECT);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.HOW);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHERE);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHY);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);

		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHEN);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.HOW_MANY);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.PASSIVE, true);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.PASSIVE, false);
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WH_WHICH);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.PASSIVE, true);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(Feature.PASSIVE,false);
		proposition2.setFeature(Feature.TENSE, Tense.CONDITIONAL);
		proposition2.setFeature(Feature.PERFECT, true);
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		SPhraseSpec principal = factory.createClause("voi", "dire", proposition2);
		principal.setIndirectObject("noi");
		principal.setFeature(Feature.FORM, Form.IMPERATIVE);
		output = realiser.realiseSentence(principal);
		System.out.println(output);
		
		proposition2.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.WHO_INDIRECT_OBJECT);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		output = realiser.realiseSentence(principal);
		System.out.println(output);
	}

}
