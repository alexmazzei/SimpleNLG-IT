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

public class Example_CoordinatedPhrase {
	
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		
		Realiser realiser = new Realiser();
		realiser.setDebugMode(true);
		String output = null;
		
		SPhraseSpec clause = factory.createClause("loro", "essere", "bello");
		//clause.setFeature(Feature.TENSE, Tense.FUTURE);
		//clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println(output);
		
		clause = factory.createClause("loro", "diventare", "bello");
		//clause.setFeature(Feature.TENSE, Tense.FUTURE);
		
		output = realiser.realiseSentence(clause);
		System.out.println(output);
		
		
		clause = factory.createClause();
		clause.setVerb("diventare");
		clause.setFeature(ItalianLexicalFeature.AUXILIARY_ESSERE, true);
		NPPhraseSpec np_maria = factory.createNounPhrase("Maria");
		np_maria.setFeature(LexicalFeature.GENDER, Gender.FEMININE);
		NPPhraseSpec np_giulia = factory.createNounPhrase("Giulia");
		np_giulia.setFeature(LexicalFeature.GENDER, Gender.FEMININE);
		CoordinatedPhraseElement coord = factory.createCoordinatedPhrase();
		coord.addCoordinate(np_maria);
		coord.addCoordinate(np_giulia);
		clause.setSubject(coord);
		clause.setObject("bello");
		clause.setFeature(Feature.TENSE, Tense.FUTURE);
		clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println(output);
		
		coord.setConjunction("o");
		output = realiser.realiseSentence(clause);
		System.out.println(output);
		
		SPhraseSpec ud_it_116 = factory.createClause();
		ud_it_116.setSubject("noi");
		ud_it_116.setVerb("rispettare");
		NPPhraseSpec decisioni = factory.createNounPhrase("il", "decisione");
		decisioni.setPlural(true);
		decisioni.addModifier("eventuale");
		ud_it_116.setObject(decisioni);
		
		PPPhraseSpec inMateria = factory.createPrepositionPhrase("in", "materia");
		NPPhraseSpec parlamento = factory.createNounPhrase("il", "parlamento");
		PPPhraseSpec delParlamento = factory.createPrepositionPhrase("di", parlamento );
		
		inMateria.addPostModifier(delParlamento);
		
		ud_it_116.addPostModifier(inMateria);
		
		ud_it_116.addFrontModifier("quindi");
		output = realiser.realiseSentence(ud_it_116);
		System.out.println(output);
	}

}
