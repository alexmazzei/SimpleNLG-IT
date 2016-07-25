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

public class Example_VP_CONDITIONAL_FORM {
	
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		//realiser.setDebugMode(true);
		String output = null;
		
		SPhraseSpec s = factory.createClause("io","camminare");
		s.setFeature(Feature.FORM, Form.CONDITIONAL);
		s.setFeature(Feature.TENSE, Tense.PRESENT);
		s.setFeature(Feature.PERFECT, false);
		output = realiser.realiseSentence(s);
		System.out.println("Io camminerei -->" + output);
		
		NPPhraseSpec subject = factory.createNounPhrase("il", "contadino");
		subject.addModifier("mio");
		NPPhraseSpec directObject = factory.createNounPhrase("il", "mela");
		SPhraseSpec clause = factory.createClause(subject, "mangiare", directObject);
		
		//CONDITIONAL FORM
		clause.setFeature(Feature.FORM, Form.CONDITIONAL);
		//###PROGRESSIVE = FALSE PERFECT = FALSE
		System.out.println("PROGRESSIVE IS FALSE PERFECT IS FALSE");
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		output = realiser.realiseSentence(clause);
		System.out.println("COND. PRESENTE");
		System.out.println("Io mangerei -->" + output);
		
		clause.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("COND. PASSATO");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.FUTURE);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - FUTURO PROG F PERFECT F");
		System.out.println("Io mangerei -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PLUS PAST PROG F PERFECT F");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO -  REMOTE PAST PROG F PERFECT F");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PLUS REMOTE PAST PROG F PERFECT F");
		System.out.println("Io avrei mangiato -->" + output);
		
		//###PROGRESSIVE = FALSE PERFECT = TRUE
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PRESENT PROG F PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PAST PROG F PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PLUS PAST PROG F PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - REMOTE PAST PROG F PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PLUS REMOTE PAST PROG F PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//###PROGRESSIVE = TRUE PERFECT = FALSE
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		clause.setFeature(Feature.PERFECT, false);
		clause.setFeature(Feature.PROGRESSIVE, true);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PRESENT PROG T PERFECT F");
		System.out.println("Io mangerei -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PAST PROG T PERFECT F");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.FUTURE);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - FUTURE PROG T PERFECT F");
		System.out.println("Io mangerei -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - REMOTE_PAST PROG T PERFECT F");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PLUS_PAST PROG T PERFECT F");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PLUS_REMOTE_PAST PROG T PERFECT F");
		System.out.println("Io avrei mangiato -->" + output);
		
		//###PROGRESSIVE = TRUE PERFECT = TRUE
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		clause.setFeature(Feature.PERFECT, true);
		clause.setFeature(Feature.PROGRESSIVE, true);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PRESENT PROG T PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PAST PROG T PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.FUTURE);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - FUTURE PROG T PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - REMOTE_PAST PROG T PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PLUS_PAST PROG T PERFECT T");
		System.out.println("Io avrei mangiato -->" + output);
		
		//NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO - PLUS_REMOTE_PAST PROG T PERFECT F");
		System.out.println("Io avrei mangiato -->" + output);
	}

}
