import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.lexicon.italian.*;
import simplenlg.realiser.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;


public class MultiLingualTest {

    public static void main(String[] args) {

	Lexicon englishLexicon = new simplenlg.lexicon.english.XMLLexicon();
	NLGFactory englishFactory = new NLGFactory(englishLexicon);
        Lexicon frenchLexicon = new simplenlg.lexicon.french.XMLLexicon();
	NLGFactory frenchFactory = new NLGFactory(frenchLexicon);
	Lexicon italianLexicon = new ITXMLLexicon();
	NLGFactory italianFactory = new NLGFactory(italianLexicon);

	Realiser realiser = new Realiser();
	//realiser.setDebugMode(true);
	String output = null;
		
	SPhraseSpec clauseIt = italianFactory.createClause("Paolo", "amare", "Freancesca");
	clauseIt.setFeature(Feature.TENSE, Tense.PAST);

	SPhraseSpec clauseEn = englishFactory.createClause("John", "love", "Mary");
	clauseEn.setFeature(Feature.TENSE, Tense.PAST);

	SPhraseSpec clauseFr = frenchFactory.createClause("Pierre", "aimer", "Sophie");
	clauseFr.setFeature(Feature.TENSE, Tense.PAST);

	
	DocumentElement paragraph = englishFactory.createParagraph();
	paragraph.addComponent(clauseIt);
	paragraph.addComponent(clauseEn);
	paragraph.addComponent(clauseFr);
	DocumentElement document = englishFactory.createDocument("Trilingual love\n");
	document.addComponent(paragraph);

	String outString = realiser.realise(document).getRealisation();
        System.out.print(outString);
    }
}
