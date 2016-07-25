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

public class Example_VP_NORMAL_FORM {
	
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		//realiser.setDebugMode(true);
		String output = null;
		
		NPPhraseSpec subject = factory.createNounPhrase("il", "contadino");
		subject.addModifier("mio");
		NPPhraseSpec directObject = factory.createNounPhrase("il", "mela");
		SPhraseSpec clause = factory.createClause(subject, "mangiare", directObject);
		
		//###PROGRESSIVE = FALSE PERFECT = FALSE
		System.out.println("PROGRESSIVE IS FALSE PERFECT IS FALSE");
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		output = realiser.realiseSentence(clause);
		System.out.println("PRESENTE");
		System.out.println("Io mangio -->" + output);
		
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.FUTURE);
		output = realiser.realiseSentence(clause);
		System.out.println("FUTURO");
		System.out.println("Io mangerò -->" + output);
		
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("IMPERFETTO");
		System.out.println("Io mangiavo -->" + output);
		
		//USO NON CORRETTO
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ REMOTE PAST PROG F PERFECT F");
		System.out.println("Io mangiavo -->" + output);
		//USO NON CORRETTO
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.PLUS_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PLUS PAST PROG F PERFECT F");
		System.out.println("Io mangiavo -->" + output);
		//USO NON CORRETTO
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.PLUS_REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PLUS REMOTE PAST PROG F PERFECT F");
		System.out.println("Io mangiavo -->" + output);
		
		//###PROGRESSIVE = FALSE PERFECT = TRUE
		System.out.println("PERFECT IS TRUE PROGRESSIVE IS FALSE");
		//USO NON CORRETTO
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		clause.setFeature(Feature.PERFECT,true);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PRESENT PROG F PERFECT T");
		System.out.println("Io ho mangiato -->" + output);
		
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.FUTURE);
		clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println("FUTURO ANTERIORE");
		System.out.println("Io avrò mangiato -->" + output);
		
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.PAST);
		clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println("PASSATO PROSSIMO");
		System.out.println("Io ho mangiato -->" + output);
		
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.REMOTE_PAST);
		clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println("PASSATO REMOTO");
		System.out.println("Io mangiai -->" + output);
	
		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.PLUS_PAST);
		clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println("TRAPASSATO PROSSIMO");
		System.out.println("Io avevo mangiato -->" + output);

		clause.setFeature(Feature.FORM, Form.NORMAL);
		clause.setFeature(Feature.TENSE, Tense.PLUS_REMOTE_PAST);
		clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println("TRAPASSATO REMOTO");
		System.out.println("Io ebbi mangiato -->" + output);
		
		//###PROGRESSIVE = TRUE PERFECT = FALSE
		clause.setFeature(Feature.PROGRESSIVE, true);
		clause.setFeature(Feature.PERFECT, false);
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		output = realiser.realiseSentence(clause);
		System.out.println("PROGRESSIVO PRESENTE");
		System.out.println("Io sto mangiando -->" + output);
		
		clause.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("PROGRESSIVO PASSATO");
		System.out.println("Io stavo mangiando -->" + output);
		
		clause.setFeature(Feature.TENSE, Tense.FUTURE);
		output = realiser.realiseSentence(clause);
		System.out.println("PROGRESSIVO FUTURO");
		System.out.println("Io starò mangiando -->" + output);
		
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PLUS_PAST PROG T PERFECT F");
		System.out.println("Io stavo mangiando -->" + output);		
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ REMOTE_PAST PROG T PERFECT F");
		System.out.println("Io stavo mangiando -->" + output);	
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PLUS_REMOTE_PAST PROG T PERFECT F");
		System.out.println("Io stavo mangiando -->" + output);	
		
		//###PROGRESSIVE = TRUE PERFECT = TRUE
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		clause.setFeature(Feature.PERFECT, true);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PRESENT PROG T PERFECT T");
		System.out.println("Io stavo mangiando -->" + output);	
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PAST PROG T PERFECT T");
		System.out.println("Io stavo mangiando -->" + output);	
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.FUTURE);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ FUTURE PROG T PERFECT T");
		System.out.println("Io sto mangiando -->" + output);	
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PLUS_PAST PROG T PERFECT T");
		System.out.println("Io stavo mangiando -->" + output);	
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ REMOTE_PAST PROG T PERFECT T");
		System.out.println("Io stavo mangiando -->" + output);	
		//USO NON CORRETTO
		clause.setFeature(Feature.TENSE, Tense.PLUS_REMOTE_PAST);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _ PLUS_REMOTE_PAST PROG T PERFECT T");
		System.out.println("Io stavo mangiando -->" + output);	
		
		//TEST COMPATIBILITY WITH FRENCH TENSE CONDITIONAL
		clause.setFeature(Feature.TENSE, Tense.CONDITIONAL);
		clause.setFeature(Feature.PROGRESSIVE, false);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _  CONDITIONAL PAST");
		System.out.println("Io avrei mangiato -->" + output);
		
		clause.setFeature(Feature.PERFECT,false);
		output = realiser.realiseSentence(clause);
		System.out.println("NON CORRETTO _  CONDITIONAL PRESENT");
		System.out.println("Io mangerei -->" + output);
		
		//test incoativo verbs
		clause = factory.createClause(subject, "colpire", directObject);
		clause.setFeature(Feature.TENSE, Tense.PRESENT);
		output = realiser.realiseSentence(clause);
		System.out.println("INCOATIVO");
		System.out.println("il contadino colpisce la mela-->" + output);
		
		clause.setFeature(Feature.FORM, Form.IMPERATIVE);
		output = realiser.realiseSentence(clause);
		System.out.println("INCOATIVO");
		System.out.println("il contadino colpisce la mela-->" + output);
		
		
	}

}
