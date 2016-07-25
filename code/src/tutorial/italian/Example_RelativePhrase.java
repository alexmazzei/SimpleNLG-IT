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

public class Example_RelativePhrase {
	
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		//realiser.setDebugMode(true);
		String output = null;
		
		NPPhraseSpec np_agente = factory.createNounPhrase("il", "agente");
		np_agente.addModifier("giallo");
		NPPhraseSpec np_case = factory.createNounPhrase("il", "casa");
		np_case.setPlural(true);
		NPPhraseSpec np_cliente = factory.createNounPhrase("la", "cliente");
		NPPhraseSpec np_ufficio = factory.createNounPhrase("il", "ufficio");
		np_ufficio.addModifier("suo");
		PPPhraseSpec pp_ufficio = factory.createPrepositionPhrase("in", np_ufficio);
		SPhraseSpec proposition = factory.createClause(np_agente, "vendere", np_case);
		proposition.setIndirectObject(np_cliente);
		proposition.addComplement(pp_ufficio);
		proposition.setFeature(Feature.TENSE, Tense.PAST);
		
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(ItalianFeature.RELATIVE_PHRASE, np_agente);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(ItalianFeature.RELATIVE_PHRASE, np_case);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(ItalianFeature.RELATIVE_PHRASE, np_cliente);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setFeature(ItalianFeature.RELATIVE_PHRASE, pp_ufficio);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		NPPhraseSpec np_contadino = factory.createNounPhrase("il", "contadino");
		PPPhraseSpec pp_delContadino = factory.createPrepositionPhrase("di", np_contadino);
		np_case.addComplement(pp_delContadino);
		SPhraseSpec proposition2 = factory.createClause(np_agente, "vendere", np_case);
		proposition2.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		proposition2.setFeature(ItalianFeature.RELATIVE_PHRASE, pp_delContadino);
		output = realiser.realiseSentence(proposition2);
		System.out.println(output);
		
		NPPhraseSpec principal = factory.createNounPhrase("il", "contadino");
		principal.addModifier("bello");
		principal.addModifier(proposition2);
		output = realiser.realiseSentence(principal);
		System.out.println(output);
	}

}
