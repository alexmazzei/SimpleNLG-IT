import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.lexicon.italian.*;
import simplenlg.realiser.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;


public class Testsimplenlgit {

    public static void main(String[] args) {
	Lexicon lexIta = new ITXMLLexicon();
	NLGFactory nlgFactory = new NLGFactory(lexIta);
	Realiser realiser = new Realiser();

	//realiser.setDebugMode(true);
	String output = null;
		
	SPhraseSpec clause = nlgFactory.createClause("loro", "essere", "bello");
	clause.setFeature(Feature.TENSE, Tense.PAST);
	//clause.setFeature(Feature.PERFECT, true);
	output = realiser.realiseSentence(clause);
	System.out.println(output);
	
    }

}
