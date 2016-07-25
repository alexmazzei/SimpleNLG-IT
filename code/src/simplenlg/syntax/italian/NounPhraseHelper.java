/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is "Simplenlg".
 *
 * The Initial Developer of the Original Code is Ehud Reiter, Albert Gatt and Dave Westwater.
 * Portions created by Ehud Reiter, Albert Gatt and Dave Westwater are Copyright (C) 2010-11 The University of Aberdeen. All Rights Reserved.
 *
 * Contributor(s): Ehud Reiter, Albert Gatt, Dave Wewstwater, Roman Kutlak, Margaret Mitchell, Pierre-Luc Vaudry.
 */
package simplenlg.syntax.italian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simplenlg.features.DiscourseFunction;
import simplenlg.features.Feature;
import simplenlg.features.Gender;
import simplenlg.features.InternalFeature;
import simplenlg.features.LexicalFeature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Person;
import simplenlg.features.italian.ItalianLexicalFeature;
import simplenlg.features.italian.PronounType;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.ListElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseCategory;
import simplenlg.framework.PhraseElement;
import simplenlg.framework.StringElement;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.AdjPhraseSpec;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.syntax.AbstractNounPhraseHelper;

/**
 * This class contains static methods to help the syntax processor realise noun
 * phrases for French.
 * 
 * @author Cristina B. based on french noun helper by vaudrypl
 */
public class NounPhraseHelper extends AbstractNounPhraseHelper
{	/** PREMODIFIERS ORDERING**/
	/** The possessive position for ordering premodifiers. */
	private static final int POSSESSIVE_POSITION = 1;

	/** The ordinal position for ordering premodifiers. */
	private static final int ORDINAL_POSITION = 2;
	
	/** The qualitative position for ordering premodifiers. */
	private static final int QUALITATIVE_POSITION = 3;

	/** The noun position for ordering premodifiers. */
	private static final int NOUN_POSITION = 4;
	
	/** POSTMODIFIERS ORDERING**/
	/** The relation position for ordering postmodifiers. */
	private static final int RELATION_POSITION = 1;

	/** The geographic position for ordering postmodifiers. */
	private static final int GEOGRAPHIC_POSITION = 2;
	
	/** The colour position for ordering postmodifiers. */
	private static final int COLOUR_POSITION = 3;
	
	/** The qualitative position for ordering postmodifiers. */
	private static final int QUALITATIVE_POST_POSITION = 4;
	
	/**
	 * Creates the appropriate personal pronoun for the noun phrase.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @return the <code>NLGElement</code> representing the pronominal.
	 */
	@Override
	protected NLGElement createPronoun(PhraseElement phrase) {

		// this will contain the features we want the pronoun to have
		Map<String, Object> pronounFeatures = new HashMap<String, Object>();
		
		pronounFeatures.put(ItalianLexicalFeature.PRONOUN_TYPE, PronounType.PERSONAL);

		Object personValue = phrase.getFeature(Feature.PERSON);
		Person person;
		if (personValue instanceof Person) {
			pronounFeatures.put(Feature.PERSON, personValue);
			person = (Person) personValue;
		}
		// default person is THIRD
		else {
			pronounFeatures.put(Feature.PERSON, Person.THIRD);
			person = Person.THIRD;
		}
		
		// only check gender feature for third person pronouns
		if (person == Person.THIRD) {
			Object genderValue = phrase.getFeature(LexicalFeature.GENDER);
			if (genderValue instanceof Gender) {
				pronounFeatures.put(LexicalFeature.GENDER, genderValue);
			}
			// default gender is MASCULINE
			else {
				pronounFeatures.put(LexicalFeature.GENDER, Gender.MASCULINE);
			}
		}
		
		Object numberValue = phrase.getFeature(Feature.NUMBER);
		if (numberValue instanceof NumberAgreement) {
			pronounFeatures.put(Feature.NUMBER, numberValue);
		}
		// default number is SINGULAR
		else {
			pronounFeatures.put(Feature.NUMBER, NumberAgreement.SINGULAR);
		}
		
		pronounFeatures.put(Feature.POSSESSIVE,
				phrase.getFeatureAsBoolean(Feature.POSSESSIVE));
		
		NLGFactory phraseFactory = phrase.getFactory();
		Lexicon lexicon = phraseFactory.getLexicon();
		// search the lexicon for the right pronoun
		WordElement proElement =
			lexicon.getWord(LexicalCategory.PRONOUN, pronounFeatures);
		
		// if the right pronoun is not found in the lexicon,
		// take "il" as a last resort
		if (proElement == null) proElement = lexicon.lookupWord("lui", LexicalCategory.PRONOUN);
			
		// AG: createWord now returns WordElement; so we embed it in an
		// inflected word element here
		InflectedWordElement element = new InflectedWordElement(proElement);
		
		if (phrase.hasFeature(InternalFeature.DISCOURSE_FUNCTION)) {
			element.setFeature(InternalFeature.DISCOURSE_FUNCTION, 
					phrase.getFeature(InternalFeature.DISCOURSE_FUNCTION));
		}		
		if (phrase.hasFeature(Feature.PASSIVE)) {
			element.setFeature(Feature.PASSIVE, 
					phrase.getFeature(Feature.PASSIVE));
		}		
		return element;
	}
	
	/**Cristina B.
	 * Determines the maximim position at which this modifier can occur.
	 * Based on the english version
	 * 
	 * @param modifier
	 *            the modifier to be checked.
	 * @return the maximum position for this modifier.
	 */
	protected int getMaxPostPos(NLGElement modifier) {
		int position = QUALITATIVE_POST_POSITION;

		if (modifier.isA(LexicalCategory.ADJECTIVE)
				|| modifier.isA(PhraseCategory.ADJECTIVE_PHRASE)) {
			WordElement adjective = getHeadWordElement(modifier);

			if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.RELATION)
					.booleanValue()) {
				position = RELATION_POSITION;
			} else if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.GEOGRAPHIC)
					.booleanValue()) {
				position = GEOGRAPHIC_POSITION;
			} else if (adjective
					.getFeatureAsBoolean(LexicalFeature.COLOUR)
					.booleanValue()) {
				position = COLOUR_POSITION;
			} else if (adjective
					.getFeatureAsBoolean(LexicalFeature.QUALITATIVE)
					.booleanValue()) {
				position = QUALITATIVE_POST_POSITION;
			} //else {
				//position = CLASSIFYING_POSITION;
			//}
		}
		return position;
	}
	
	/**Cristina B.
	 * Determines the maximim position at which this modifier can occur.
	 * Based on the english version
	 * 
	 * @param modifier
	 *            the modifier to be checked.
	 * @return the maximum position for this modifier.
	 */
	protected int getMaxPrePos(NLGElement modifier) {
		int position = NOUN_POSITION;

		if (modifier.isA(LexicalCategory.ADJECTIVE)
				|| modifier.isA(PhraseCategory.ADJECTIVE_PHRASE)) {
			WordElement adjective = getHeadWordElement(modifier);

			if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.POSSESSIVE)
					.booleanValue()) {
				position = POSSESSIVE_POSITION;
			} else if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.ORDINAL)
					.booleanValue() || isOrdinal(adjective)) {
				position = ORDINAL_POSITION;
			} else if (adjective
					.getFeatureAsBoolean(LexicalFeature.QUALITATIVE)
					.booleanValue()) {
				position = QUALITATIVE_POSITION;
			} //else {
				//position = CLASSIFYING_POSITION;
			//}
		}
		return position;
	}
	
	
	/**Cristina B.
	 * Determines the minimim position at which this modifier can occur.
	 * Based on the english version getMinPos
	 * 
	 * @param modifier
	 *            the modifier to be checked.
	 * @return the minimum position for this modifier.
	 */
	protected int getMinPrePos(NLGElement modifier) {
		int position = POSSESSIVE_POSITION;

		if (modifier.isA(LexicalCategory.NOUN)
				|| modifier.isA(PhraseCategory.NOUN_PHRASE)) {

			position = NOUN_POSITION;
		} else if (modifier.isA(LexicalCategory.ADJECTIVE)
				|| modifier.isA(PhraseCategory.ADJECTIVE_PHRASE)) {
			WordElement adjective = getHeadWordElement(modifier);

			if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.POSSESSIVE)
					.booleanValue()) {
				position = POSSESSIVE_POSITION;
			} else if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.ORDINAL)
					.booleanValue() || isOrdinal(adjective)) {
				position = ORDINAL_POSITION;
			} else if (adjective
					.getFeatureAsBoolean(ItalianLexicalFeature.QUALITATIVE)
					.booleanValue()) {
				position = QUALITATIVE_POSITION;
			}
		}
		return position;
	}
	
	/**Cristina B.
	 * Determines the minimim position at which this modifier can occur.
	 * Based on the english version getMinPos
	 * 
	 * @param modifier
	 *            the modifier to be checked.
	 * @return the minimum position for this modifier.
	 */
	protected int getMinPostPos(NLGElement modifier) {
		int position = RELATION_POSITION;

		//if (modifier.isA(LexicalCategory.NOUN)
		//		|| modifier.isA(PhraseCategory.NOUN_PHRASE)) {

		//	position = NOUN_POSITION;
		//} else 
		if (modifier.isA(LexicalCategory.ADJECTIVE)
				|| modifier.isA(PhraseCategory.ADJECTIVE_PHRASE)) {
			WordElement adjective = getHeadWordElement(modifier);

			if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.RELATION)
					.booleanValue()) {
				position = RELATION_POSITION;
			} else if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.GEOGRAPHIC)
					.booleanValue()) {
				position = GEOGRAPHIC_POSITION; 
			} else if (adjective.getFeatureAsBoolean(ItalianLexicalFeature.COLOUR)
					.booleanValue()) {
				position = COLOUR_POSITION;
			} else if (adjective
					.getFeatureAsBoolean(ItalianLexicalFeature.QUALITATIVE)
					.booleanValue()) {
				position = QUALITATIVE_POST_POSITION;
			}
		}
		return position;
	}
	
	/**Cristina B.
	 * 
	 * Add a modifier to a noun phrase. Use heuristics to decide where it goes.
	 * based on italian grammar of reference. 
	 * COLOUR, RELATION, GEOGRAPHIC are in postPosition (both adj words and phrase with no complements), 
	 * ORDINAL POSSESSIVE and QUALITATIVE are in prePosition (both adj words and phrase with no complements)
	 * Complex string postposition. 
	 * se ADJPhrase has complements, postPosition
	 * The order is managed by the sortNPPreModifiers and sortNPPostModifiers
	 * 
	 * @param nounPhrase
	 * @param modifier
	 */
	@Override
	public void addModifier(NPPhraseSpec nounPhrase, Object modifier) {
		//no modifiers, returns
		if (modifier == null)
			return;

		// get modifier as NLGElement if possible
		//can be an ADJPhrase, WordElement or Inflected
		//if null is a complex string
		NLGElement modifierElement = null;
		if (modifier instanceof NLGElement)
			modifierElement = (NLGElement) modifier;
		else if (modifier instanceof String) {
			String modifierString = (String) modifier;
			Lexicon lexicon = nounPhrase.getLexicon();
			if (lexicon.hasWord(modifierString)) {
				modifierElement = lexicon.lookupWord(modifierString);
			}
			//createWord returns a NLGElement if modifier is a word in the lexicon,
			//otherwise null
			else if (isOrdinal(modifierString) ||
						(modifierString.length() > 0 && !modifierString.contains(" "))) {
				modifierElement = nounPhrase.getFactory().createWord(modifier,
						LexicalCategory.ADJECTIVE);
			}
		}

		// if no modifier element, must be a complex string, add as postModifier
		if (modifierElement == null) {
			nounPhrase.addPostModifier((String) modifier);
			return;
		}
		
		//if modifier element is an ADJPhrase
		if (modifierElement instanceof AdjPhraseSpec) {
			AdjPhraseSpec modifierAdjPhrase = (AdjPhraseSpec) modifierElement;
			NLGElement modifierHead = modifierAdjPhrase.getHead();
			List<NLGElement> modifierComplements =
				modifierAdjPhrase.getFeatureAsElementList(InternalFeature.COMPLEMENTS);
			//if AdjPhrase no complements, POSSESSIVE, ORDINAL, QUALITATIVE --> preposition
			if ((modifierHead.getFeatureAsBoolean(ItalianLexicalFeature.POSSESSIVE)
						|| isOrdinal(modifierHead) 
						|| modifierHead.getFeatureAsBoolean(ItalianLexicalFeature.ORDINAL)
						|| modifierHead.getFeatureAsBoolean(ItalianLexicalFeature.QUALITATIVE))
					&& modifierComplements.isEmpty()) {
						nounPhrase.addPreModifier(modifierElement);
						return;
			}
			//if AdjPhrase no complements, RELATION, COLOUR, GEOGRAPHIC --> postposition
			else if ((modifierHead.getFeatureAsBoolean(ItalianLexicalFeature.RELATION)
					|| modifierHead.getFeatureAsBoolean(ItalianLexicalFeature.COLOUR)
					|| modifierHead.getFeatureAsBoolean(ItalianLexicalFeature.GEOGRAPHIC))
				&& modifierComplements.isEmpty()) {
					nounPhrase.addPostModifier(modifierElement);
					return;
			}
		}//end ADJPhrase
		
		//if modifiers is a single word
		WordElement modifierWord = null;
		if (modifierElement instanceof WordElement)
			modifierWord = (WordElement) modifierElement;
		else if (modifierElement instanceof InflectedWordElement)
			modifierWord = ((InflectedWordElement) modifierElement)
					.getBaseWord();

		// check if modifier is an adjective
		if (modifierWord != null
				&& modifierWord.getCategory() == LexicalCategory.ADJECTIVE){
			
			//if adj word POSSESSIVE, ORDINAL, QUALITATIVE --> preposition
			if (modifierElement.getFeatureAsBoolean(ItalianLexicalFeature.POSSESSIVE) 
					|| modifierElement.getFeatureAsBoolean(ItalianLexicalFeature.ORDINAL)
					|| modifierElement.getFeatureAsBoolean(ItalianLexicalFeature.QUALITATIVE)
					|| isOrdinal(modifierWord)) {
						nounPhrase.addPreModifier(modifierWord);
						return;
			}
			//if AdjPhrase no complements, RELATION, COLOUR, GEOGRAPHIC --> postposition
			else if(modifierElement.getFeatureAsBoolean(ItalianLexicalFeature.RELATION)
					||modifierElement.getFeatureAsBoolean(ItalianLexicalFeature.COLOUR)
					|| modifierElement.getFeatureAsBoolean(ItalianLexicalFeature.GEOGRAPHIC)){
						nounPhrase.addPostModifier(modifierWord);
						return;
			}
		}
		// default case
		nounPhrase.addPostModifier(modifierElement);
	}

	/**
	 * Recognises ordinal adjectives by their ending ("ième").
	 * Exceptions : "premier", "second" and "dernier" are treated
	 * in the lexicon.
	 * 
	 * @param element
	 * @return
	 * 			true if the element represents an ordinal adjective
	 * 			false otherwise
	 */
	protected boolean isOrdinal(NLGElement element) {
		String baseForm = null;
		
		if (element instanceof WordElement) {
			baseForm = ((WordElement) element).getBaseForm();
		} else if (element instanceof StringElement) {
			baseForm = ((StringElement) element).getRealisation();
		}
		
		return isOrdinal(baseForm);
	}
		
	/**Cristina B.
	 * Adapted French code for recognizing ordinal adjs for italian.
	 * Ordinal adjectives are usually in preposed position as in "Il primo libro"
	 * and not "il libro primo".
	 * Recognises ordinal adjectives by their endings ("esimo", "esime", "esimi", "esima).
	 * Exceptions : "primo", "secondo", "terzo"..."decimo",  are treated
	 * in the lexicon.
	 * 
	 * @param expression
	 * @return
	 * 			true if the expression represents an ordinal adjective
	 * 			false otherwise
	 */
	protected boolean isOrdinal(String expression) {
		//return (expression != null && expression.endsWith("esimo"));
		return (expression != null && 
				(expression.endsWith("esimo") || 
				 expression.endsWith("esima") ||
				 expression.endsWith("esimi") || 
				 expression.endsWith("esime"))
				);
	}
		
	/**
	 * The main method for realising noun phrases.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> to be realised.
	 * @return the realised <code>NLGElement</code>.
	 */
	@Override
	public ListElement realise(PhraseElement phrase) {
		ListElement realisedElement = null;

			realisedElement = new ListElement(phrase);
			
		// Creates the appropriate pronoun if the noun phrase
		// is pronominal.
		if (phrase.getFeatureAsBoolean(Feature.PRONOMINAL)) {
			realisedElement.addComponent(createPronoun(phrase));
			
		} else {
			realisedElement = super.realise(phrase);
		}
		// to take care of demonstrative determiners with particle (e.g. "questo NP qui")
		addParticle(phrase, realisedElement);
		
		return realisedElement;
	}

	/**
	 * Adds the particle feature (if any) to the last element syntactically realised
	 * 
	 * @param phrase			the noun phrase
	 * @param realisedElement	the ListElement of syntactically realised elements
	 */
	protected void addParticle(PhraseElement phrase, ListElement realisedElement) {

		NLGElement specifier = phrase.getFeatureAsElement(InternalFeature.SPECIFIER);
		
		if (specifier != null) {
			String particle = specifier.getFeatureAsString(Feature.PARTICLE);
			
			if (particle != null) {
				NLGElement lastElement = realisedElement.getRightMostTerminalElement();		
				
				if (lastElement instanceof InflectedWordElement) {
					lastElement.setFeature(Feature.PARTICLE, particle);
				} else if (lastElement instanceof StringElement) {
					String realisation = lastElement.getRealisation();
					realisation += " " + particle;
					lastElement.setRealisation(realisation);
				}
			}
		}
	}
	/**Cristina B.
	 * Sort the list of premodifiers for this noun phrase using adjective
	 * ordering (ie, "mio" comes before "primo")
	 * based on the english version
	 * 
	 * @param originalModifiers
	 *            the original listing of the premodifiers.
	 * @return the sorted <code>List</code> of premodifiers.
	 */
	@Override
	protected List<NLGElement> sortNPPreModifiers(List<NLGElement> originalModifiers) {

		List<NLGElement> orderedModifiers = null;

		if (originalModifiers == null || originalModifiers.size() <= 1) {
			orderedModifiers = originalModifiers;
		} else {
			orderedModifiers = new ArrayList<NLGElement>(originalModifiers);
			boolean changesMade = false;
			do {
				changesMade = false;
				for (int i = 0; i < orderedModifiers.size() - 1; i++) {
					if (getMinPrePos(orderedModifiers.get(i)) > getMaxPrePos(orderedModifiers
							.get(i + 1))) {
						NLGElement temp = orderedModifiers.get(i);
						orderedModifiers.set(i, orderedModifiers.get(i + 1));
						orderedModifiers.set(i + 1, temp);
						changesMade = true;
					}
				}
			} while (changesMade == true);
		}
		return orderedModifiers;
	}
	
	/**Cristina B.
	 * Sort the list of postmodifiers for this noun phrase using adjective
	 * ordering (ie, "mio" comes before "primo")
	 * based on the english version
	 * 
	 * @param originalModifiers
	 *            the original listing of the postmodifiers.
	 * @return the sorted <code>List</code> of postmodifiers.
	 */
	@Override
	protected List<NLGElement> sortNPPostModifiers(List<NLGElement> originalModifiers) {

		List<NLGElement> orderedModifiers = null;

		if (originalModifiers == null || originalModifiers.size() <= 1) {
			orderedModifiers = originalModifiers;
		} else {
			orderedModifiers = new ArrayList<NLGElement>(originalModifiers);
			boolean changesMade = false;
			do {
				changesMade = false;
				for (int i = 0; i < orderedModifiers.size() - 1; i++) {
					if (getMinPostPos(orderedModifiers.get(i)) > getMaxPostPos(orderedModifiers
							.get(i + 1))) {
						NLGElement temp = orderedModifiers.get(i);
						orderedModifiers.set(i, orderedModifiers.get(i + 1));
						orderedModifiers.set(i + 1, temp);
						changesMade = true;
					}
				}
			} while (changesMade == true);
		}
		return orderedModifiers;
	}
	
}
