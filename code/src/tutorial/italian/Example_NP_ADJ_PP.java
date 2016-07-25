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

public class Example_NP_ADJ_PP {
	
public static void main(String[] args) {
		
		/*########LESSICO##########*/
		Lexicon lexIta = new ITXMLLexicon();
		
		/*########CREAZIONE FACTORY##########*/
		NLGFactory factory = new NLGFactory(lexIta);
		
		/*########CREAZIONE realiser##########*/
		Realiser realiser = new Realiser();
		//realiser.setDebugMode(true);
		String output = null;
		
		/*########ADJs tests##########
		 * PreModifiers order: possessive ordinal qualitative
		 * PostModifers order: relation geographic colour qualitative
		 * N.B. A qualitative adjective in post-modifiers has to be 
		 * added by the users using addPostModifier.
		 * 1. Il mio bel cestino
		 * 2. Il mio bel cestino giallo
		 * 2.b La mia bella mela gialla
		 * 3. Il mio bel cestino giallo pieno di frutta - complements at the end
		 * 4. Il mio primo bel cestino giallo pieno di frutta
		 * 5. La grande casa gialla
		 * 6. L'incerta situazione politica
		 */
		
		System.out.println("### ADJ test ###");
		/*#### IL MIO BEL CESTINO ###*/
		//if there is info in the lexicon e.g., <possessive /> <colour /> etc.
		NPPhraseSpec one = factory.createNounPhrase("il", "cesto");
		one.addModifier("mio");
		//if there is no info in the lexicon, the user needs to create 
		//a word for the adjective, set up the word lexical feature and create the
		//adjective phrase 
		WordElement bello_w = lexIta.getWord("bello", LexicalCategory.ADJECTIVE);
		bello_w.setFeature(ItalianLexicalFeature.QUALITATIVE, true);
		//AdjPhraseSpec bello = factory.createAdjectivePhrase(bello_w);
		one.addModifier(bello_w);
		
		System.out.print("POSSESSIVE in lessico + QUALITATIVE set feature --> ");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*#### IL MIO BEL CESTINO GIALLO - DEFAULT CASE###*/
		System.out.print("DEFAULT CASE : Il mio bel cestino giallo --> ");
		one.addModifier("giallo");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*#### IL MIO BEL CESTINO GIALLO - COLOUR###*/
		System.out.print("WITH COLOUR FEATURE : Il mio bel cestino giallo --> ");
		one.clearModifiers();
		one.addModifier(bello_w);
		one.addModifier("mio");
		WordElement giallo_w = lexIta.getWord("giallo", LexicalCategory.ADJECTIVE);
		bello_w.setFeature(ItalianLexicalFeature.COLOUR, true);
		AdjPhraseSpec giallo = factory.createAdjectivePhrase(giallo_w);
		one.addModifier(giallo);
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*#### LA MIA BELLA MELA GIALLA - COLOUR###*/
		System.out.print("AGREEMENT FEMININE : La mia bella mela gialla --> ");
		one = factory.createNounPhrase("il", "mela");
		one.addModifier(bello_w);
		one.addModifier("mio");
		one.addModifier(giallo);
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*### Il mio bel cestino giallo pieno di frutta 
		 * - if complements, adj at the end
		 * also if the adj is in preposition e.g. qualitative
		 * ###*/
		one = factory.createNounPhrase("il", "cesto");
		one.addModifier(bello_w);
		one.addModifier("mio");
		one.addModifier(giallo);
		PPPhraseSpec complementsPieno = factory.createPrepositionPhrase("di", "frutta");
		WordElement pieno_w = lexIta.getWord("pieno", LexicalCategory.ADJECTIVE);
		pieno_w.setFeature(ItalianLexicalFeature.QUALITATIVE, true);
		AdjPhraseSpec pieno = factory.createAdjectivePhrase(pieno_w);
		pieno.addComplement(complementsPieno);
		one.addModifier(pieno);
		System.out.print("COMPLEMENT OF QUALITATIVE ADJ : Il mio bel cestino giallo pieno di frutta --> ");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*### Il mio primo bel cestino giallo pieno di frutta ###*/
		WordElement primo = lexIta.getWord("primo", LexicalCategory.ADJECTIVE);
		primo.setFeature(ItalianLexicalFeature.ORDINAL, true);
		one.addModifier(primo);
		System.out.print("ORDINAL primo in lessico --> ");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		System.out.print("ORDINAL dopo dieci automatico -->  "); 
		one.addModifier("undicesimo");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*### LA GRANDE CASA GIALLA ###*/
		one = factory.createNounPhrase("il", "casa");
		System.out.print("LA GRANDE CASA GIALLA DEFAULTS --> "); 
		one.addModifier("grande");
		one.addModifier("gialla");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		one.clearModifiers();
		WordElement grande_w = lexIta.getWord("grande", LexicalCategory.ADJECTIVE);
		grande_w.setFeature(ItalianLexicalFeature.QUALITATIVE, true);
		one.addModifier(grande_w);
		one.addModifier(giallo_w);
		System.out.print("QUALITATIVE AND COLOUR --> "); 
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*### INCERTA SITUAZIONE POLITICA###*/
		one = factory.createNounPhrase("il", "situazione");
		System.out.print("INCERTA SITUAZIONE POLITICA DEFAULTS --> "); 
		one.addModifier("incerta");
		one.addModifier("politica");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		System.out.print("RELATION QUALITATIVE --> "); 
		one.clearModifiers();
		WordElement incerta = lexIta.getWord("incerto", LexicalCategory.ADJECTIVE);
		incerta.setFeature(ItalianLexicalFeature.QUALITATIVE, true);
		one.addModifier(incerta);
		WordElement politica = lexIta.getWord("politico", LexicalCategory.ADJECTIVE);
		politica.setFeature(ItalianLexicalFeature.RELATION, true);
		one.addModifier(politica);
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		System.out.print("RELATION + incerta as QUALITATIVE Post --> "); 
		one.clearModifiers();
		one.addPostModifier(incerta);
		one.addModifier(politica);
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*########Determiners tests##########*/
		NPPhraseSpec vino = factory.createNounPhrase("vino");
		System.out.print("SPECIFIER il --> "); 
		vino.setSpecifier("il");
		output = realiser.realiseSentence(vino);
		System.out.println(output);
		
		WordElement abbastanza = lexIta.getWord("abbastanza", LexicalCategory.ADVERB); 
		//abbastanza vino
		//abbastanza acqua
		vino.setSpecifier(abbastanza);
		System.out.println("ADVs as specifiers/determiners -->");
		output = realiser.realiseSentence(vino);
		System.out.println(output);
		
		one = factory.createNounPhrase("mela");
		one.setSpecifier("questo");
		System.out.println("ADJS DIMOSTRATIVI as specifiers/determiners -->");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		System.out.println("ADJS DIMOSTRATIVI -qui PARTICLE -->");
		one.setSpecifier("questo qui");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		one = factory.createNounPhrase("questo qui","contadino");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		System.out.println("ADJS INDEFINIT as DETERMINERS -->");
		one = factory.createNounPhrase("mela");
		one.setPlural(true);
		one.setSpecifier("molto");
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		System.out.println("DETERMINERS PARTITIVI-->");
		NPPhraseSpec snAcqua = factory.createNounPhrase("dello", "acqua");
		output = realiser.realiseSentence(snAcqua);
		System.out.println(output);
		
		System.out.println("ES 3.5 NON CHIARO-->");
		one = factory.createNounPhrase("la", "casa");
		AdjPhraseSpec belle = factory.createAdjectivePhrase("bello");
		AdvPhraseSpec molto = factory.createAdverbPhrase("molto");
		belle.addModifier(molto);
		one.addModifier(belle);
		one.addModifier("spazioso");
		one.setPlural(true);
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		System.out.println("ES 3.6 PP come complemento del nome  e modificatore del nome-->");
		PPPhraseSpec complementoNomeCategoria = factory.createPrepositionPhrase("di", "campagna");
		one.addComplement(complementoNomeCategoria);
		PPPhraseSpec complementoNomeMateria = factory.createPrepositionPhrase("di", "pietra");
		one.addModifier(complementoNomeMateria);
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		System.out.println("ES 3.7 PP come complemento del adj-->");
		NPPhraseSpec frutti = factory.createNounPhrase("frutto");
		frutti.setPlural(true);
		PPPhraseSpec complementoAdj = factory.createPrepositionPhrase("di", frutti);
		AdjPhraseSpec pienoModNome = factory.createAdjectivePhrase("pieno");
		pienoModNome.addComplement(complementoAdj);
		one= factory.createNounPhrase("il", "cestino");
		one.addModifier(pienoModNome);
		output = realiser.realiseSentence(one);
		System.out.println(output);
		
		/*COMPARATIVE - REGOLARE*/
		//COMPARATIVO CON PIÚ 
		System.out.println("COMPARATIVO REGOLARE - più + ADJ base form");
		NPPhraseSpec mela = factory.createNounPhrase("il", "mela");
		SPhraseSpec comparativo = factory.createClause(mela, "essere");
		AdjPhraseSpec bella = factory.createAdjectivePhrase("bello");
		bella.addModifier("più");
		NPPhraseSpec pera = factory.createNounPhrase("la", "pera");
		PPPhraseSpec dellaPera = factory.createPrepositionPhrase("di", pera);
		bella.addComplement(dellaPera);
		comparativo.addComplement(bella);
		output = realiser.realiseSentence(comparativo);
		System.out.println(output);
		

		//COMPARATIVO PLURALE
		System.out.println("COMPARATIVO REGOLARE PLURALE - più + ADJ base form");
		NPPhraseSpec mele = factory.createNounPhrase("il", "mela");
		mele.setPlural(true);
		SPhraseSpec comparativoPlurale = factory.createClause(mele, "essere");
		AdjPhraseSpec buone = factory.createAdjectivePhrase("buono");
		buone.addModifier("più");
		NPPhraseSpec fragole = factory.createNounPhrase("la", "fragola");
		fragole.setPlural(true);
		PPPhraseSpec delleFragole = factory.createPrepositionPhrase("di", fragole);
		buone.addComplement(delleFragole);
		comparativoPlurale.addComplement(buone);
		output = realiser.realiseSentence(comparativoPlurale);
		System.out.println(output);
		

		//COMPARATIVO IRREGOLARE NEL LESSICO
		System.out.println("COMPARATIVO IRREGOLARE - is_comparative and comparative form");
		mela = factory.createNounPhrase("il", "mela");
		SPhraseSpec comparativoIrregolare = factory.createClause(mela, "essere");
		AdjPhraseSpec migliore = factory.createAdjectivePhrase("buono");
		migliore.setFeature(Feature.IS_COMPARATIVE, true);
		NPPhraseSpec fragola = factory.createNounPhrase("la", "fragola");
		PPPhraseSpec dellaFragola = factory.createPrepositionPhrase("di", fragola);
		migliore.addComplement(dellaFragola);
		comparativoIrregolare.addComplement(migliore);
		output = realiser.realiseSentence(comparativoIrregolare);
		System.out.println(output);
		
		//SUPERLATIVO IRREGOLARE
		System.out.println("SUPERLATIVO IRREGOLARE - is_superlative and superlative form");
		mela = factory.createNounPhrase("il", "mela");
		SPhraseSpec superlativoIrregolare = factory.createClause(mela, "essere");
		WordElement buono = lexIta.getWord("buono", LexicalCategory.ADJECTIVE);
		AdjPhraseSpec ottimo = factory.createAdjectivePhrase(buono);
		//ottimo.setFeature(Feature.IS_COMPARATIVE, false);
		ottimo.setFeature(Feature.IS_SUPERLATIVE, true);
		superlativoIrregolare.addComplement(ottimo);
		output = realiser.realiseSentence(superlativoIrregolare);
		System.out.println(output);		
		
		//SUPERLATIVO REGOLARE
		//PER ORA NEL LESSICO
		//da GESTIRE SUPERLATIVE_FEMINININE
		//ORA NEL LESSICO GIALLISSIMA
		System.out.println("SUPERLATIVO REGOLARE - is_superlative, per ora nel lessico");
		SPhraseSpec superlativoReg = factory.createClause(mela, "essere");
		AdjPhraseSpec giallissima = factory.createAdjectivePhrase("giallo");
		giallissima.setFeature(Feature.IS_SUPERLATIVE, true);
		superlativoReg.addComplement(giallissima);
		output = realiser.realiseSentence(superlativoReg);
		System.out.println(output);		
				
		
		//SUPERLATIVO RELATIVO
		// PROBLEMA LA -- sbagliato usare determiner in PPPhraseSpec!
		// la dovrebbe essere pronome come in "di tutte le qualitá il coraggio è
		// la più importante
		// provo a creare frutta elided per generare pronome
		System.out.println("SUPERLATIVO RELATIVO - costruzione speciale con NP elided");
		mela = factory.createNounPhrase("il", "mela");

		WordElement frutta = lexIta.getWord("frutta");
		InflectedWordElement fruttaElided = new InflectedWordElement(frutta);
		fruttaElided.setFeature(Feature.ELIDED, true);

		NPPhraseSpec lapiugialla = factory.createNounPhrase();
		lapiugialla.setHead(fruttaElided);
		lapiugialla.setSpecifier("il");

		AdjPhraseSpec piugiallaSupRel = factory.createAdjectivePhrase("giallo");
		//piugiallaSupRel.setFeature(Feature.IS_SUPERLATIVE, false);
		piugiallaSupRel.addModifier("più");

		lapiugialla.addPreModifier(piugiallaSupRel);
		// PPPhraseSpec piùGialla = factory.createPrepositionPhrase("il",
		// giallaRel);
		// superlativoRel.addComplement(piùGialla);

		SPhraseSpec superlativoRel = factory.createClause(mela, "essere", lapiugialla);

		output = realiser.realiseSentence(superlativoRel);
		System.out.println(output);
		
		AdjPhraseSpec miglioreSupRel = factory.createAdjectivePhrase("buona");
		//piugiallaSupRel.setFeature(ItalianFeature.IS_SUPERLATIVE_RELATIVE, true);
		//piugiallaSupRel.addModifier("più");
		miglioreSupRel.setFeature(ItalianFeature.IS_SUPERLATIVE_RELATIVE, true);
		
		lapiugialla.clearModifiers();
		lapiugialla.addPreModifier(miglioreSupRel);
		
		SPhraseSpec superlativoRelIrregolare = factory.createClause(mela, "essere", lapiugialla);
		
		 output = realiser.realiseSentence(superlativoRelIrregolare);
		System.out.println(output);	
		
		NPPhraseSpec contadino = factory.createNounPhrase("il", "contadino");
		contadino.setPlural(true);
		SPhraseSpec superlativoMP = factory.createClause(contadino, "essere");
		AdjPhraseSpec ottimi = factory.createAdjectivePhrase("buono");
		ottimi.setFeature(Feature.IS_SUPERLATIVE, true);
		superlativoMP.addComplement(ottimi);
		output = realiser.realiseSentence(superlativoMP);
		System.out.println(output);	
		
		NPPhraseSpec sbarco = factory.createNounPhrase("questo", "sbarco");
		output = realiser.realiseSentence(sbarco);
		System.out.println(output);
		
		sbarco = factory.createNounPhrase("questo", "schiavo");
		sbarco.setPlural(true);
		output = realiser.realiseSentence(sbarco);
		System.out.println(output);
		
		sbarco.setFeature(Feature.PRONOMINAL, true);
		SPhraseSpec proposition = factory.createClause("tu", "volere");
		proposition.setObject(sbarco);
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
		proposition.setObject("questo");
		output = realiser.realiseSentence(proposition);
		System.out.println(output);
		
	}

}
