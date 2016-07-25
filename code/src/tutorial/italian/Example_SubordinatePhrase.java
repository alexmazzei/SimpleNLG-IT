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

public class Example_SubordinatePhrase {
	
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		//realiser.setDebugMode(true);
		String output = null;
		
		SPhraseSpec clause = factory.createClause("loro", "andare");
		clause.setFeature(Feature.TENSE, Tense.PAST);
		clause.setFeature(Feature.PERFECT, true);
		clause.setFeature(ItalianLexicalFeature.AUXILIARY_ESSERE, true);
		NPPhraseSpec ilParco = factory.createNounPhrase("il", "parco");
		PPPhraseSpec alParco = factory.createPrepositionPhrase("a", ilParco);
		clause.setComplement(alParco);
		output = realiser.realiseSentence(clause);
		System.out.println(output);
		
		SPhraseSpec principal = factory.createClause("voi", "dire");
		output = realiser.realiseSentence(principal);
		System.out.println(output);
		
		principal = factory.createClause("voi", "dire", clause);
		output = realiser.realiseSentence(principal);
		System.out.println(output);
		
		SPhraseSpec sub = factory.createClause("lui", "essere", "ricco");
		sub.setFeature(Feature.FORM, Form.SUBJUNCTIVE);
		sub.setFeature(Feature.TENSE, Tense.PAST);
		sub.setFeature(Feature.PROGRESSIVE, true);
		sub.setFeature(Feature.COMPLEMENTISER, "se");
		NPPhraseSpec casa = factory.createNounPhrase("il", "casa");
		SPhraseSpec prin = factory.createClause("lui", "comprare", casa);
		prin.setFeature(Feature.TENSE, Tense.CONDITIONAL);
		//prin.setFeature(Feature.FORM, Form.SUBJUNCTIVE);
		prin.addFrontModifier(sub);
		output = realiser.realiseSentence(prin);
		System.out.println(output);
	}

}
