/*
Mopu * The contents of this file are subject to the Mozilla Public License
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

package simplenlg.morphology.italian;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.PhraseCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.StringElement;
import simplenlg.framework.WordElement;
import simplenlg.features.*;
import simplenlg.features.italian.*;
import simplenlg.lexicon.Lexicon;
import simplenlg.morphology.MorphologyRulesInterface;

/**
 * Morphology rules for Italian.
 * 
 * @author Cristina Battaglino
 *
 */
public class MorphologyRules extends simplenlg.morphology.english.NonStaticMorphologyRules
		implements MorphologyRulesInterface {
	
	public static final String a_o_regex = "\\A(a|ä|à|â|o|ô).*";

	/**
	 * This method performs the morphology for determiners.
	 * It returns a StringElement made from the baseform, or
	 * the plural or feminine singular form of the determiner
	 * if it applies.
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 */
	@Override
	public NLGElement doDeterminerMorphology(InflectedWordElement element) {
		String inflectedForm;
		// Get gender from parent, or from self if there is no parent
		NLGElement parent = element.getParent();
		Object gender = null;
		
		if (parent != null) gender = parent.getFeature(LexicalFeature.GENDER);
		//trying to do the agreement between determiners and noun in NP
		//when the SP Phrase Spec is not created as parent by the user
		//else gender = element.getFeature(LexicalFeature.GENDER);
		if(gender == null) gender = element.getFeature(LexicalFeature.GENDER);
		
		boolean feminine = Gender.FEMININE.equals( gender );
		
		// plural form
		if (element.isPlural() && 
			element.hasFeature(LexicalFeature.PLURAL)) {
			inflectedForm = element.getFeatureAsString(LexicalFeature.PLURAL);
			
			if (feminine && element.hasFeature(ItalianLexicalFeature.FEMININE_PLURAL)) {
				inflectedForm = element.getFeatureAsString(ItalianLexicalFeature.FEMININE_PLURAL);
			}
			
		// feminine singular form
		} else if (feminine	&& element.hasFeature(ItalianLexicalFeature.FEMININE_SINGULAR)) {
			inflectedForm = element.getFeatureAsString(ItalianLexicalFeature.FEMININE_SINGULAR);
		// masculine singular form
		} else {
			inflectedForm = element.getBaseForm();
			// remove particle if the determiner has one
			String particle = getParticle(element);
			inflectedForm = inflectedForm.replaceFirst(particle, "");
			inflectedForm = inflectedForm.trim();
		}
		
		StringElement realisedElement = new StringElement(inflectedForm, element);
		return realisedElement;
	}
	
	/**
	 * This method performs the morphology for adjectives.
	 * Based in part on the same method in the english rules
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @param baseWord
	 *            the <code>WordElement</code> as created from the lexicon
	 *            entry.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	@Override
	public NLGElement doAdjectiveMorphology(
			InflectedWordElement element, WordElement baseWord) {

		String realised = null;
		 //System.out.println("Test morphology adjs :" + element.getFeatureAsBoolean(Feature.IS_COMPARATIVE));
		// base form from baseWord if it exists, otherwise from element
		String baseForm = getBaseForm(element, baseWord);

		
		// Comparatives and superlatives 
		if (element.getFeatureAsBoolean(Feature.IS_SUPERLATIVE).booleanValue()){
			realised = element.getFeatureAsString(LexicalFeature.SUPERLATIVE);
			
			if (realised == null && baseWord != null) {
				realised = baseWord
						.getFeatureAsString(LexicalFeature.SUPERLATIVE);
			}
			//if (realised == null) realised = baseForm;
			if(realised == null){
				//build rules for superlative
				int length = baseForm.length();
				//if(baseForm.endsWith("o")){
					realised = baseForm.substring(0, length-1) + "issimo";
				//}
			}
			
			//if (realised == null) realised = baseForm;
			
		}
		if (element.getFeatureAsBoolean(Feature.IS_COMPARATIVE).booleanValue() || 
				element.getFeatureAsBoolean(ItalianFeature.IS_SUPERLATIVE_RELATIVE).booleanValue()) {
			realised = element.getFeatureAsString(LexicalFeature.COMPARATIVE);

			if (realised == null && baseWord != null) {
				realised = baseWord
						.getFeatureAsString(LexicalFeature.COMPARATIVE);
			}
			if (realised == null) realised = baseForm;
		} 
		
		if(realised == null)
			realised = baseForm;
		
		// Get gender from parent or "grandparent" or self, in that order
		NLGElement parent = element.getParent();
		Object function = element.getFeature(InternalFeature.DISCOURSE_FUNCTION);
		boolean feminine = false;
		if (parent != null) {
			if (function == DiscourseFunction.HEAD) {
				function = parent.getFeature(InternalFeature.DISCOURSE_FUNCTION);
			}

			if (!parent.hasFeature(LexicalFeature.GENDER) && parent.getParent() != null) {
				parent = parent.getParent();
			}
		} else {
			parent = element;
		}
		// if parent or grandparent is a verb phrase and the adjective is a modifier,
		// assume it's a direct object attribute if there is one
		if (parent.isA(PhraseCategory.VERB_PHRASE) && (function == DiscourseFunction.FRONT_MODIFIER
				|| function == DiscourseFunction.PRE_MODIFIER
				|| function == DiscourseFunction.POST_MODIFIER)) {
			List<NLGElement> complements = parent.getFeatureAsElementList(InternalFeature.COMPLEMENTS);
			NLGElement directObject = null;
			for (NLGElement complement: complements) {
				if (complement.getFeature(InternalFeature.DISCOURSE_FUNCTION) == DiscourseFunction.OBJECT) {
					directObject = complement;
				}
			}
			if (directObject != null) parent = directObject;
		}
		feminine = Gender.FEMININE.equals( parent.getFeature(LexicalFeature.GENDER) );

		// Feminine
		// The rules used here apply to the most general cases.
		// Exceptions are meant to be specified in the lexicon or by the user
		// by means of the ItalianLexicalFeature.FEMININE_SINGULAR feature, and
		//SUPERLATIVE_FEMININE 
		// Reference : pg 93 Patota
		///Cristina B. 
		//irregular comparative don't have feminine form?
		//superlative and comparative yes...we need to agree here with the noun
		if ( feminine && 
				!element.getFeatureAsBoolean(Feature.IS_COMPARATIVE).booleanValue()
				&& !element.getFeatureAsBoolean(ItalianFeature.IS_SUPERLATIVE_RELATIVE).booleanValue()) {
			//takes the superlative singular feminine form
			if(element.getFeatureAsBoolean(Feature.IS_SUPERLATIVE).booleanValue()){
				realised = element.getFeatureAsString(ItalianLexicalFeature.SUPERLATIVE_FEMININE_SINGULAR);
				
				if (realised == null && baseWord != null) {
					realised = baseWord
							.getFeatureAsString(ItalianLexicalFeature.SUPERLATIVE_FEMININE_SINGULAR);
				}
				
				if(realised == null ){
					//build rules for superlative
					int length = baseForm.length();
					//if(baseForm.endsWith("o")){
						realised = baseForm.substring(0, length-1) + "issima";
					//}
				}
				//if (realised == null) realised = baseForm;
			}
			//take the feminine form
			else if (element.hasFeature(ItalianLexicalFeature.FEMININE_SINGULAR)) {
				realised = element.getFeatureAsString(ItalianLexicalFeature.FEMININE_SINGULAR);
			}
			//if no information from user or lexicon and not superlative etc...simple rules
			else{ 
				int lenght = realised.length();
				if (realised.endsWith("o")) {
					realised = realised.substring(0, lenght-1) + "a";
				}
			}//ends simple rules
		}

		// Plural Cristina B.
		// The rules used here apply to the most general cases.
		// Exceptions are meant to be specified in the lexicon or by the user
		// by means of the LexicalFeature.PLURAL and
		// ItalianLexicalFeature.FEMININE_PLURAL and the 
		// ItalianLexicalFeature.SUPERLATIVE_FEMININE_PLURAL 
		//and the ItalianLexicalFeature.SUPERLATIVE_PLURAL
		//features.
		// Reference : pg. 93
		if (parent.isPlural()) {
			if (feminine) {
				if(element.getFeatureAsBoolean(Feature.IS_SUPERLATIVE).booleanValue()){
					
					realised = element.getFeatureAsString(ItalianLexicalFeature.SUPERLATIVE_FEMININE_PLURAL);
					
					if (realised == null && baseWord != null) {
						realised = baseWord
								.getFeatureAsString(ItalianLexicalFeature.SUPERLATIVE_FEMININE_PLURAL);
					}
					
					if(realised == null){
						//build rules for superlative
						int length = baseForm.length();
						//if(baseForm.endsWith("o")){
							realised = baseForm.substring(0, length-1) + "issime";
						//}
					}
					
					//if (realised == null) realised = baseForm;
				}
				else if (element.hasFeature(ItalianLexicalFeature.FEMININE_PLURAL)) {
					realised = element.getFeatureAsString(ItalianLexicalFeature.FEMININE_PLURAL);
				}
				else {//simple general rule
					int lenght = realised.length();
					//first group cara mamma, care mamme
					//third group bambina egoista, bambine egoiste
					if (realised.endsWith("o") || realised.endsWith("a")) {
						realised = realised.substring(0, lenght-1) + "e";
					}
					//second group nave veloce, nave veloci
					else if (realised.endsWith("e")) {
						realised = realised.substring(0, lenght-1) + "i";
					}
				}
			}
			else if (element.hasFeature(LexicalFeature.PLURAL)) {
				if(element.getFeatureAsBoolean(Feature.IS_SUPERLATIVE).booleanValue()){
					
					realised = element.getFeatureAsString(ItalianLexicalFeature.SUPERLATIVE_PLURAL);
					
					if (realised == null && baseWord != null) {
						realised = baseWord
								.getFeatureAsString(ItalianLexicalFeature.SUPERLATIVE_PLURAL);
					}
					
					if(realised == null){
						//build rules for superlative
						int length = baseForm.length();
						//element.getBaseForm();
						//if(baseForm.endsWith("o")){
							realised = baseForm.substring(0, length-1) + "issimi";
						
						//}
					}
					
					//if(realised == null) realised = baseForm;
				}
				else{
					realised = element.getFeatureAsString(LexicalFeature.PLURAL);
				}
			}
			else {
				int lenght = realised.length();
				//second group group treno veloce, treni veloci
				// third group bambino egoista bambini egoisti
				if (realised.endsWith("e") || realised.endsWith("a")) {
					realised = realised.substring(0, lenght-1) + "i";
				}
				//first group caro papà, cari fratelli
				else if(realised.endsWith("o")){
					realised = realised.substring(0, lenght-1) + "i";
				}
				//realised = buildRegularPlural(realised);
			}
		}
		
		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised, element);
		return realisedElement;
	}

	/**
	 * Return an empty string if the element doesn't have a particle.
	 * If it has a non empty one, it returns it prepended by a dash.
	 * 
	 * @param element
	 * @return	the String to be appended to the element's realisation
	 */
	protected String getParticle(InflectedWordElement element) {
		String particle = element.getFeatureAsString(Feature.PARTICLE);
		
		if (particle == null) particle = "";
		else if (!particle.isEmpty()) 
			//particle = "-" + particle;
			//Cristina B. Italian has no -
			particle = " " + particle;
		
		return particle;
	}

//	/**
//	 * @param element
//	 * @return true if parent or grandparent of element is feminine
//	 */
//	public boolean getParentOrGrandParentFeminine(InflectedWordElement element) {
//		// Get gender from parent or "grandparent" for adjectives
//		NLGElement parent = element.getParent();
//		boolean feminine = false;
//		if (parent != null ) {
//			if (!parent.hasFeature(LexicalFeature.GENDER) && parent.getParent() != null) {
//				parent = parent.getParent();
//			}
//			feminine = Gender.FEMININE.equals( parent.getFeature(LexicalFeature.GENDER) );
//		}
//		return feminine;
//	}

	
	/**
	 *  Builds the plural form of a noun following regular rules.
	 * Reference : pages 42-55 Patota
	 * 
	 * @param form form being realised on which to apply the plural morphology
	 * @return the plural form
	 */
	protected String buildRegularPlural(String form, Object object) {
		Gender gender = (Gender) object;
		//System.out.println("form " + form);
		
		if(form.endsWith("ca")){
			if(gender != null){
				//System.out.println("gender " + gender.toString());
				if(gender.equals(Gender.MASCULINE))
					form = form.substring(0, form.length()-2) + "chi";
				else if (gender.equals(Gender.FEMININE))
					form = form.substring(0, form.length()-2) + "che";
			}
		}
		else if(form.endsWith("ga")){
			if(gender != null){
				//System.out.println("gender " + gender.toString());
				if(gender.equals(Gender.MASCULINE))
					form = form.substring(0, form.length()-2) + "ghi";
				else if (gender.equals(Gender.FEMININE))
					form = form.substring(0, form.length()-2) + "ghe";
			}
		}
		else if(form.endsWith("scia")){
			if(gender != null){
				//System.out.println("gender " + gender.toString());
				 if (gender.equals(Gender.FEMININE))
					form = form.substring(0, form.length()-2) + "e";
			}
		}
		else if(form.endsWith("io")){
			form = form.substring(0, form.length()-1) + "i";
		}
		else if(form.endsWith("cio") || form.endsWith("gio") || form.endsWith("glio")){
			form = form.substring(0, form.length()-1);
		}
		else if(form.endsWith("a")){
			if(gender != null){
				//System.out.println("gender " + gender.toString());
				if(gender.equals(Gender.MASCULINE))
					form = form.substring(0, form.length()-1) + "i";
				else if (gender.equals(Gender.FEMININE))
					form = form.substring(0, form.length()-1) + "e";
			}
		}
		else if(form.endsWith("o")){
			if(gender != null){
				if(!gender.equals(Gender.FEMININE))
					form = form.substring(0, form.length()-1) + "i";
			}
			else{
				form = form.substring(0, form.length()-1) + "i";
			}
		}
		else if(form.endsWith("e")){
			form = form.substring(0, form.length()-1) + "i";
		}
		return form;
	}

	/**
	 * This method performs the morphology for nouns.
	 * Based in part on the same method in the english rules
	 * Reference : Noun chapter Patota
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @param baseWord
	 *            the <code>WordElement</code> as created from the lexicon
	 *            entry.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	@Override
	public StringElement doNounMorphology(
			InflectedWordElement element, WordElement baseWord) {
		String realised = "";

		// Check if the noun's gender needs to be changed
		// and change base form and baseWord accordingly
		if (baseWord != null) {
			Object elementGender = element.getFeature(LexicalFeature.GENDER);
			Object baseWordGender = baseWord.getFeature(LexicalFeature.GENDER);
			// The gender of the inflected word is opposite to the base word 
			if ((Gender.MASCULINE.equals(baseWordGender) &&	Gender.FEMININE.equals(elementGender))
				|| (Gender.FEMININE.equals(baseWordGender) && Gender.MASCULINE.equals(elementGender))) {
				
				String oppositeGenderForm = baseWord.getFeatureAsString(ItalianLexicalFeature.OPPOSITE_GENDER);
				
				if (oppositeGenderForm == null) {
					// build opposite gender form if possible
					if (Gender.MASCULINE.equals(baseWordGender)) {
						// the base word is masculine and the feminine must be build
						// (to be completed if necessary)
					}
					else {
						// the base word is feminine and the masculine must be build
						// (to be completed if necessary)
					}
				}
				// if oppositeGenderForm is specified or has been built
				if (oppositeGenderForm != null) {
					// change base form and base word
					element.setFeature(LexicalFeature.BASE_FORM, oppositeGenderForm);
					baseWord = baseWord.getLexicon().lookupWord(oppositeGenderForm, LexicalCategory.NOUN);
					element.setBaseWord(baseWord);
				}
			}
		}
		
		// base form from element if it exists, otherwise from baseWord 
		String baseForm = getBaseForm(element, baseWord);
		
		if (element.isPlural()
				&& !element.getFeatureAsBoolean(LexicalFeature.PROPER)) {

			String pluralForm = null;

			pluralForm = element.getFeatureAsString(LexicalFeature.PLURAL);

			if (pluralForm == null && baseWord != null) {
				pluralForm = baseWord.getFeatureAsString(LexicalFeature.PLURAL);
			}
			
			if (pluralForm == null) {
				pluralForm = buildRegularPlural(baseForm, element.getFeature(LexicalFeature.GENDER));
			}
			realised = pluralForm;
		} else {
			realised = baseForm;
		}
		
		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised.toString(), element);
		return realisedElement;
	}


	/**
	 * This method performs the morphology for verbs.
	 * Based in part on the same method in the english rules
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @param baseWord
	 *            the <code>WordElement</code> as created from the lexicon
	 *            entry.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	@Override
	public NLGElement doVerbMorphology(InflectedWordElement element,
			WordElement baseWord) {

		String realised = null;
		
		
		
		Object numberValue = element.getFeature(Feature.NUMBER);
		// default number is SINGULAR
		NumberAgreement number = NumberAgreement.SINGULAR;
		if (numberValue instanceof NumberAgreement) {
			number = (NumberAgreement) numberValue;
		}
		
		Object personValue = element.getFeature(Feature.PERSON);
		// default person is THIRD
		Person person = Person.THIRD;
		if (personValue instanceof Person) {
			person = (Person) personValue;
		}
		
		Object genderValue = element.getFeature(LexicalFeature.GENDER);
		// default gender is MASCULINE
		Gender gender = Gender.MASCULINE;
		if (genderValue instanceof Gender) {
			gender = (Gender) genderValue;
		}
		
		Object tenseValue = element.getFeature(Feature.TENSE);
		// default tense is PRESENT
		Tense tense = Tense.PRESENT;
		if (tenseValue instanceof Tense) {
			tense = (Tense) tenseValue;
		}
		
		Object formValue = element.getFeature(Feature.FORM);
		
		boolean incoativo = element.getFeatureAsBoolean(ItalianLexicalFeature.INCOATIVO);

		// participles that are not directly in a verb phrase
		// get their gender and number like adjectives
		if (formValue == Form.PRESENT_PARTICIPLE || formValue == Form.PAST_PARTICIPLE) {
			// Get gender and number from parent or "grandparent" or self, in that order
			NLGElement parent = element.getParent();
			if ( parent != null) {
				boolean aggreement = false;
				Object function = element.getFeature(InternalFeature.DISCOURSE_FUNCTION);
				// used as epithet or as attribute of the subject
				if (!parent.isA(PhraseCategory.VERB_PHRASE) || function == DiscourseFunction.OBJECT) {
					if (!parent.hasFeature(LexicalFeature.GENDER) && parent.getParent() != null) {
						parent = parent.getParent();
					}
					aggreement = true;
				} else {
					// used as attribute of the direct object
					if (function == DiscourseFunction.FRONT_MODIFIER
							|| function == DiscourseFunction.PRE_MODIFIER
							|| function == DiscourseFunction.POST_MODIFIER) {
						List<NLGElement> complements =
							parent.getFeatureAsElementList(InternalFeature.COMPLEMENTS);
						NLGElement directObject = null;
						for (NLGElement complement: complements) {
							if (complement.getFeature(InternalFeature.DISCOURSE_FUNCTION) ==
									DiscourseFunction.OBJECT) {
								directObject = complement;
							}
						}
						if (directObject != null) parent = directObject;
						aggreement = true;
					}
				}
				
				if (aggreement) {
					Object parentGender = parent.getFeature(LexicalFeature.GENDER);
					if (parentGender instanceof Gender) {
						gender = (Gender) parentGender;
					}
					
					Object parentNumber = parent.getFeature(Feature.NUMBER);
					if (parentNumber instanceof NumberAgreement) {
						number = (NumberAgreement) parentNumber;
					}
				}
			}
		}
			
		// base form from baseWord if it exists, otherwise from element
		String baseForm = getBaseForm(element, baseWord);

		if (Form.BARE_INFINITIVE.equals(formValue) || Form.INFINITIVE.equals(formValue) ) {
			realised = baseForm;
			
		}
		//Cristina B.
		//split PRESENT_PARTICPLE AND GERUND for italian
		else if ( Form.PRESENT_PARTICIPLE.equals(formValue) ) {
			// Reference : PATOTA?
			realised = element
					.getFeatureAsString(LexicalFeature.PRESENT_PARTICIPLE);

			if (realised == null && baseWord != null) {
				realised = baseWord
						.getFeatureAsString(LexicalFeature.PRESENT_PARTICIPLE);
			}
			if (realised == null) {
				realised = buildPresentParticipleVerb(baseForm);
			}
			// Note : The gender and number features must only be
			// passed to the present participle by the syntax when
			// the present participle is used as an adjective.
			// Otherwise it is immutable.
			if (number == NumberAgreement.PLURAL) 
			{
					int length = realised.length();
					realised = realised.substring(0, length-1) + "i";
			}
			
		} else if (Form.GERUND.equals(formValue)){
			realised = element
					.getFeatureAsString(ItalianLexicalFeature.GERUND);

			if (realised == null && baseWord != null) {
				realised = baseWord
						.getFeatureAsString(ItalianLexicalFeature.GERUND);
			}
			if (realised == null) {
				realised = buildGerundVerb(baseForm);
			}
		} else if (Form.PAST_PARTICIPLE.equals(formValue)) {
			
			// get or build masculine form
			realised = element
					.getFeatureAsString(LexicalFeature.PAST_PARTICIPLE);
			
			if (realised == null && baseWord != null) {
				realised = baseWord
						.getFeatureAsString(LexicalFeature.PAST_PARTICIPLE);
			}
			
			if (realised == null) {
				realised = buildPastParticipleVerb(baseForm);
			}
			
			//Cristina B.
			//when we need the agreement of the past participle in italian 
			// we check the number and gender for build it
			
			if (gender == Gender.FEMININE) {
				int lenght = realised.length();
				if(number == NumberAgreement.PLURAL){
					realised = realised.substring(0, lenght-1) + "e";
				}
				else {
					realised = realised.substring(0, lenght-1) + "a";
				}
			}
			
			// build plural form
			if (number == NumberAgreement.PLURAL && !realised.endsWith("e") && !realised.endsWith("a")) {
				int lenght = realised.length();
				realised = realised.substring(0, lenght-1) + "i";
			}

		} else if (formValue == Form.SUBJUNCTIVE) {
			if (//congiuntivo imperfetto forma base
				(!tenseValue.equals(Tense.PRESENT) && !tenseValue.equals(Tense.FUTURE) && !element.getFeatureAsBoolean(Feature.PERFECT)) ||
				//congiuntivo imp aux
				(element.getFeatureAsBoolean(Feature.PERFECT) && tenseValue.equals(Tense.PLUS_PAST)) ||
				 //congiuntivo imperfetto forma base
				(element.getFeatureAsBoolean(Feature.PROGRESSIVE) && tenseValue.equals(Tense.PAST) &&
						!element.getFeatureAsBoolean(Feature.PERFECT))){
				// try to get inflected form from user feature or lexicon
				switch ( number ) {
				case SINGULAR: case BOTH:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP1S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP1S);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP2S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP2S);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP3S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP3S);
						}
						break;
					}
					break;
				case PLURAL:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP1P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP1P);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP2P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP2P);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP3P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVEIMP3P);
						}
						break;
					}
					break;
				}
				// build inflected form if none was specified by the user or lexicon
				if (realised == null) {
					realised = buildSubjunctiveImperfectVerb(baseForm, number, person);
				}
			}
			else{
				// try to get inflected form from user feature or lexicon
				switch ( number ) {
				case SINGULAR: case BOTH:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE1S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE1S);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE2S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE2S);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE3S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE3S);
						}
						break;
					}
					break;
				case PLURAL:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE1P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE1P);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE2P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE2P);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE3P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.SUBJUNCTIVE3P);
						}
						break;
					}
					break;
				}
				// build inflected form if none was specified by the user or lexicon
				if (realised == null) {
					realised = buildSubjunctiveVerb(baseForm, number, person, incoativo);
				}
			}
		} 
		else if ((tense == null || tense == Tense.PRESENT || tense == Tense.REMOTE_PAST || formValue == Form.IMPERATIVE) && formValue != Form.CONDITIONAL) {
			//Imperative has one tense PRESENT and two persons 2S and 2P in italian
			if (formValue == Form.IMPERATIVE) {
				switch (number) {
				case  SINGULAR: case BOTH:
					realised = element.getFeatureAsString(ItalianLexicalFeature.IMPERATIVE2S);
					if (realised == null && baseWord != null) {
						realised = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPERATIVE2S);
					}
					//Cristina B.
					// generally, imperative present 2S = indicative present 2S
					if (realised == null) person = Person.SECOND;
					break;
				case PLURAL:
					//switch (person) {
					//case FIRST:
					realised = element.getFeatureAsString(ItalianLexicalFeature.IMPERATIVE2P);
					if (realised == null && baseWord != null) {
						realised = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPERATIVE2P);
					}
					//Cristina B. 
					// generally, imperative 2P = indicative 1P
					if (realised == null) person = Person.SECOND;
					break;
				}
			}
			
			if(tenseValue == Tense.REMOTE_PAST){
				switch ( number ) {
				case SINGULAR: case BOTH:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST1S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST1S);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST2S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST2S);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST3S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST3S);
						}
						break;
					}
					break;
				case PLURAL:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST1P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST1P);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST2P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST2P);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST3P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.REMOTE_PAST3P);
						}
						break;
					}
					break;
				}
				// build inflected form if none was specified by the user or lexicon
				if (realised == null) {
					realised = buildRemotePastVerb(baseForm, number, person);
				}
			}

			// indicative
			if (realised == null) {
				// try to get inflected form from user feature or lexicon
				switch ( number ) {
				case SINGULAR: case BOTH:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.PRESENT1S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.PRESENT1S);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.PRESENT2S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.PRESENT2S);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.PRESENT3S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.PRESENT3S);
						}
						break;
					}
					break;
				case PLURAL:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.PRESENT1P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.PRESENT1P);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.PRESENT2P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.PRESENT2P);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.PRESENT3P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.PRESENT3P);
						}
						break;
					}
					break;
				}
				// build inflected form if none was specified by the user or lexicon
				if (realised == null) {
					
					realised = buildPresentVerb(baseForm, number, person, incoativo);
				}
			}
		//TO DO FORM CONDITIONAL!!!! 
		} else if ((tense == Tense.FUTURE) || (tense == Tense.CONDITIONAL) || (formValue == Form.CONDITIONAL))  {
			
			String radical = getFutureConditionalRadical(baseForm, baseWord, baseWord);
			
			if (tense == Tense.FUTURE && formValue != Form.CONDITIONAL){
				// try to get inflected form from user feature or lexicon
				switch ( number ) {
				case SINGULAR: case BOTH:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.FUTURE1S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.FUTURE1S);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.FUTURE2S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.FUTURE2S);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.FUTURE3S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.FUTURE3S);
						}
						break;
					}
					break;
				case PLURAL:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.FUTURE1P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.FUTURE1P);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.FUTURE2P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.FUTURE2P);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.FUTURE3P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.FUTURE3P);
						}
						break;
					}
					break;
				}
				// build inflected form if none was specified by the user or lexicon
				//with possible radical in the lexicon
				if (realised == null) {
					realised = buildFutureVerb(radical, number, person);
				}
			}
			else{ 
				switch ( number ) {
				case SINGULAR: case BOTH:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.COND1S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.COND1S);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.COND2S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.COND2S);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.COND3S);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.COND3S);
						}
						break;
					}
					break;
				case PLURAL:
					switch ( person ) {
					case FIRST:
						realised = element.getFeatureAsString(ItalianLexicalFeature.COND1P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.COND1P);
						}
						break;
					case SECOND:
						realised = element.getFeatureAsString(ItalianLexicalFeature.COND2P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.COND2P);
						}
						break;
					case THIRD:
						realised = element.getFeatureAsString(ItalianLexicalFeature.COND3P);
						if (realised == null && baseWord != null) {
							realised = baseWord.getFeatureAsString(ItalianLexicalFeature.COND3P);
						}
						break;
					}
					break;
				}
				// build inflected form if none was specified by the user or lexicon
				//with possible radical in the lexicon
				if (realised == null) {
					realised = buildConditionalVerb(radical, number, person, baseForm);
				}
			}
			// "imparfait" (progressive not perfect past)
		} else if (tense == Tense.PAST)  {
			String radical = getImparfaitRadical(element, baseWord, baseForm);
			
			switch ( number ) {
			case SINGULAR: case BOTH:
				switch ( person ) {
				case FIRST:
					realised = element.getFeatureAsString(ItalianLexicalFeature.IMPF1S);
					if (realised == null && baseWord != null) {
						realised = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPF1S);
					}
					break;
				case SECOND:
					realised = element.getFeatureAsString(ItalianLexicalFeature.IMPF2S);
					if (realised == null && baseWord != null) {
						realised = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPF2S);
					}
					break;
				case THIRD:
					realised = element.getFeatureAsString(ItalianLexicalFeature.IMPF3S);
					if (realised == null && baseWord != null) {
						realised = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPF3S);
					}
					break;
				}
				break;
			case PLURAL:
				switch ( person ) {
				case FIRST:
					realised = element.getFeatureAsString(ItalianLexicalFeature.IMPF1P);
					if (realised == null && baseWord != null) {
						realised = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPF1P);
					}
					break;
				case SECOND:
					realised = element.getFeatureAsString(ItalianLexicalFeature.IMPF2P);
					if (realised == null && baseWord != null) {
						realised = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPF2P);
					}
					break;
				case THIRD:
					realised = element.getFeatureAsString(ItalianLexicalFeature.IMPF3P);
					if (realised == null && baseWord != null) {
						realised = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPF3P);
					}
					break;
				}
				break;
			}
			
			// build inflected form with radical
			if (realised == null) {
				realised = addImparfCondSuffix(radical, number, person);
			}
		} 
		else {
			realised = baseForm;
		}

		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised, element);
		return realisedElement;
	}

	protected String buildRemotePastVerb(String baseForm, NumberAgreement number, Person person) {
		GetPresentRadicalReturn multReturns =
				getPresentRadical(baseForm);
		String radical = multReturns.radical;
		int verbEndingCategory = multReturns.verbEndingCategory;
		
		// Determine suffix with verb ending category.
		String suffix = "";		
		if (verbEndingCategory != 0) {
			switch ( number ) {
			case SINGULAR: case BOTH:
				// verb ending categories for present singular
				switch (verbEndingCategory) {
				// first verb ending category - are
				//1 2 3 person SINGULAR
				case 1:
					switch ( person ) {
					case FIRST: 
						suffix = "ai";
						break;
					case SECOND:
						suffix = "asti";
						break;
					case THIRD:
						suffix = "ò";
						break;
					}
					break;
				// second verb ending category - ere 
				//1 2 3 person SINGULAR
				case 2:
					switch ( person ) {
					case FIRST: 
						suffix = "ei";
						break;
					case SECOND:
						suffix = "esti";
						break;
					case THIRD:
						suffix = "é";
						break;
					}
					break;
				// third verb ending category - ire
				//1 2 3 person SINGULAR
				case 3:
					switch ( person ) {
					case FIRST: 
						suffix = "ii";
						break;
					case SECOND:
						suffix = "isti";
						break;
					case THIRD:
						suffix = "ì";
						break;
					}
					break;
				}
				break;
			case PLURAL:
				switch (verbEndingCategory) {
				// first verb ending category - are
				//1 2 3 person PLURAL
				case 1:
					switch ( person ) {
					case FIRST: 
						suffix = "ammo";
						break;
					case SECOND:
						suffix = "aste";
						break;
					case THIRD:
						suffix = "arono";
						break;
					}
					break;
				// second verb ending category - ere 
				//1 2 3 person SINGULAR
				case 2:
					switch ( person ) {
					case FIRST: 
						suffix = "emmo";
						break;
					case SECOND:
						suffix = "este";
						break;
					case THIRD:
						suffix = "erono";
						break;
					}
					break;
				// third verb ending category - ire
				//1 2 3 person SINGULAR
				case 3:
					switch ( person ) {
					case FIRST: 
						suffix = "immo";
						break;
					case SECOND:
						suffix = "iste";
						break;
					case THIRD:
						suffix = "irono";
						break;
					}
					break;
				}
				break;	
			}
		}
		
		return addSuffix(radical, suffix);
	}

	/**
	 * Builds the subjunctive imerfect form for regular verbs. 
	 * Reference : Patota 
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected String buildSubjunctiveImperfectVerb(String baseForm, NumberAgreement number, Person person) {
		//Cristina B.
		//added method for generating the imperfect subjunctive form for irregular verb
		
		// Get radical.
		GetPresentRadicalReturn multReturns = getPresentRadical(baseForm);
		String radical = multReturns.radical;
		int verbCategory = multReturns.verbEndingCategory;

		// Determine suffix.
		String suffix = "";

		// verbs that end in "are"
		if (verbCategory == 1) {
			switch (number) {
			case SINGULAR:
			case BOTH:
				switch (person) {
				case FIRST:
				case SECOND:
					suffix="assi";
					break;
				case THIRD:
					suffix = "asse";
					break;
				}
				break;
			case PLURAL:
				switch (person) {
				case FIRST:
					suffix = "assimo";
					break;
				case SECOND:
					suffix = "aste";
					break;
				case THIRD:
					suffix = "assero";
					break;
				}
				break;
			}
		}
		// verbs that end in "ere" 
		else if (verbCategory == 2){
			switch (number) {
			case SINGULAR:
			case BOTH:
				switch (person) {
				case FIRST:
				case SECOND:
					suffix= "essi";
					break;
				case THIRD:
					suffix = "esse";
					break;
				}
				break;
			case PLURAL:
				switch (person) {
				case FIRST:
					suffix = "essimo";
					break;
				case SECOND:
					suffix = "este";
					break;
				case THIRD:
					suffix = "essero";
					break;
				}
				break;
			}
		}//verbs that ends in "ire"
		else {
			switch (number) {
			case SINGULAR:
			case BOTH:
				switch (person) {
				case FIRST:
				case SECOND:
					suffix= "issi";
					break;
				case THIRD:
					suffix = "isse";
					break;
				}
				break;
			case PLURAL:
				switch (person) {
				case FIRST:
					suffix = "issimo";
					break;
				case SECOND:
					suffix = "iste";
					break;
				case THIRD:
					suffix = "issero";
					break;
				}
				break;
			}
		}

		return addSuffix(radical, suffix);
	}

	/**
	 * Gets or builds the radical used for "imparfait" and present participle.
	 * Reference : Mansouri (1996)
	 * 
	 * @param element
	 * @param baseWord
	 * @param baseForm
	 * @return the "imparfait" and present participle radical
	 */
	protected String getImparfaitRadical(InflectedWordElement element, WordElement baseWord, String baseForm) {
		// try to get inflected form from user feature or lexicon
		// otherwise take infinitive (base form)
		String radical = element.getFeatureAsString(ItalianLexicalFeature.IMPARFAIT_RADICAL);
		if (radical == null && baseWord != null) {
			radical = baseWord.getFeatureAsString(ItalianLexicalFeature.IMPARFAIT_RADICAL);
		}
		//Cristina B. 
		//uses first person plural present radical --> this doesn't work for italian
		//take the base word infinite
		if (radical == null) {
			//radical = element.getFeatureAsString(ItalianLexicalFeature.PRESENT2S);
			radical = element.getFeatureAsString(InternalFeature.BASE_WORD);
			//System.out.println("check element feature base_word "+ radical);
			if (radical == null && baseWord != null) {
				//radical = baseWord.getFeatureAsString(ItalianLexicalFeature.);
				radical = baseWord.getBaseForm();
				//System.out.println("check baseWord "+ radical);
				
				//Cristina B. 
				//for the italian we use the base form, 
				//so we don't need to build the present
				if (radical == null){
					//radical = buildPresentVerb(baseForm, NumberAgreement.PLURAL, Person.FIRST);
					radical = baseForm;
				} 
			}
			//Cristina B. not for italian
			// removes -ions suffix
			//int radicalLength = radical.length();
			//if (radicalLength > 4) radical = radical.substring(0, radicalLength-3);
			
			//removes -re suffixes, always because we use the radical of the infinite
			int radicalLength = radical.length();
			radical = radical.substring(0, radicalLength-2);
		}
		return radical;
	}
	
	/**
	 * Builds the present form for regular verbs. 
	 * Reference : Mansouri (1996)
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected String buildPresentVerb(String baseForm, NumberAgreement number,
			Person person, boolean incoativo) {
		// Get radical and verbEndingCategory.
		GetPresentRadicalReturn multReturns =
				getPresentRadical(baseForm);
		String radical = multReturns.radical;
		int verbEndingCategory = multReturns.verbEndingCategory;
		
		// Determine suffix with verb ending category.
		String suffix = "";	
		if(incoativo)
			suffix = "isc";
		if (verbEndingCategory != 0) {
			switch ( number ) {
			case SINGULAR: case BOTH:
				// verb ending categories for present singular
				switch (verbEndingCategory) {
				// first verb ending category - are
				//1 2 3 person SINGULAR
				case 1:
					switch ( person ) {
					case FIRST: 
						suffix += "o";
						break;
					case SECOND:
						suffix += "i";
						break;
					case THIRD:
						suffix += "a";
						break;
					}
					break;
				// second verb ending category - ere 
				//1 2 3 person SINGULAR
				case 2:
					switch ( person ) {
					case FIRST: 
						suffix += "o";
						break;
					case SECOND:
						suffix += "i";
						break;
					case THIRD:
						suffix += "e";
						break;
					}
					break;
				// third verb ending category - ire
				//1 2 3 person SINGULAR
				case 3:
					switch ( person ) {
					case FIRST: 
							suffix += "o";
						break;
					case SECOND:
						suffix += "i";
						break;
					case THIRD:
						suffix += "e";
						break;
					}
					break;
				}
				break;
			case PLURAL:
				switch (person) {
				//FIRST PLURAL - iamo
				case FIRST:
						suffix = "iamo";
						break;
				//SECOND PLURAL - ate -ete -ite
				case SECOND:
					switch(verbEndingCategory){
						case 1:
							suffix += "ate";
							break;
						case 2:
							suffix += "ete";
							break;
						case 3:
							suffix = "ite";
							break;
					}
					break;
				case THIRD:
					switch(verbEndingCategory){
					case 1: 
						suffix = "ano";
						break;
					case 2: case 3:
						suffix += "ono";
						break;
					}
					break;
				}
				break;	
			}
		}
		
		return addSuffix(radical, suffix);
	}
	
	/**
	 * Builds the present participle form for regular verbs. 
	 * Reference : Patota
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @return the inflected word.
	 */
	protected String buildPresentParticipleVerb(String baseForm) {
		// Get radical and verbEndingCategory.
		GetPresentRadicalReturn multReturns =
				getPresentRadical(baseForm);
		String radical = multReturns.radical;
		int verbEndingCategory = multReturns.verbEndingCategory;
		
		// Determine suffix with verb ending category.
		//based on PATOTA pg...TO DO!
		String suffix = "";		
		if (verbEndingCategory != 0) {
			switch ( verbEndingCategory ) {
			// 1 coniug. are
				case 1:
					suffix = "ante";
					break;
			//2 coniug ere - ire
			//non sono gestite le forme dormiente, ubbidiente etc.
				case 2: case 3:
					suffix = "ente";
					break;
			}
		}
		return addSuffix(radical, suffix);
	}
	
	/**
	 * Builds the gerund form for regular verbs. 
	 * Reference : Patota
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @return the inflected word.
	 */
	protected String buildGerundVerb(String baseForm) {
		if(baseForm .equals("fare"))
			return "facendo";
		// Get radical and verbEndingCategory.
		GetPresentRadicalReturn multReturns =
				getPresentRadical(baseForm);
		String radical = multReturns.radical;
		int verbEndingCategory = multReturns.verbEndingCategory;
		
		// Determine suffix with verb ending category.
		//based on PATOTA pg...TO DO!
		String suffix = "";		
		if (verbEndingCategory != 0) {
			switch ( verbEndingCategory ) {
			// 1 coniug. are
				case 1:
					suffix = "ando";
					break;
			//2 coniug ere - ire
			//non sono gestite le forme dormiente, ubbidiente etc.
				case 2: case 3:
					suffix = "endo";
					break;
			}
		}
		
		return addSuffix(radical, suffix);
	}
	
	/**
	 * Builds the subjunctive present form for regular verbs. 
	 * Reference : Patota pg 148-149
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected String buildSubjunctiveVerb( String baseForm,
			NumberAgreement number, Person person, boolean incoativo) {
		//Cristina B. 
		
		// Get radical.
		GetPresentRadicalReturn multReturns =
				getPresentRadical(baseForm);
		String radical = multReturns.radical;
		
		int verbCategory = multReturns.verbEndingCategory;
		
		// Determine suffix.
		String suffix = "";	
		if(incoativo)
			suffix = "isc";
		
		//verbs that end in "are"
		if(verbCategory == 1) {
			switch ( number ) {
			case SINGULAR: case BOTH:
				switch ( person ) {
				case FIRST: case SECOND: case THIRD:
					suffix = "i";
					break;
				}
				break;
			case PLURAL:
				switch ( person ) {
				case FIRST:
					suffix = "iamo";
					break;
				case SECOND:
					suffix = "iate";
					break;
				case THIRD:
					suffix = "ino";
					break;
				}
				break;
			}
		}
		//verbs that end in "ere" and "ire"
		else {
			switch ( number ) {
			case SINGULAR: case BOTH:
				switch ( person ) {
				case FIRST: case SECOND: case THIRD:
					suffix += "a";
					break;
				}
				break;
			case PLURAL:
				switch ( person ) {
				case FIRST:
					suffix = "iamo";
					break;
				case SECOND:
					suffix = "iate";
					break;
				case THIRD:
					suffix += "ano";
					break;
				}
				break;
			}
		}
		
		return addSuffix(radical, suffix);
	}
	
	/**
	 * @param baseForm
	 * @return the radical used in indicative present
	 */
	protected GetPresentRadicalReturn getPresentRadical( String baseForm) {
		//Cristina B. adapted for italian language
		int length = baseForm.length();
		String radical = baseForm;
		// verb ending category for present singular
		int verbEndingCategory = 0;
		
		// with verb ending, determine verb category and radical  
		// based on "amare" --> am+suffix
		if (baseForm.endsWith("are") ) {
			radical = baseForm.substring(0, length-3);
			verbEndingCategory = 1;
		// base on "leggere, vedere
		} else if (baseForm.endsWith("ere")) {
			radical = baseForm.substring(0, length-3);
			verbEndingCategory = 2;
		// based on "servire", 
		//first group, regular Patota pg 161
		}else if (baseForm.endsWith("ire")) {
			radical = baseForm.substring(0, length-3);
			verbEndingCategory = 3;
		}
		//third coniugation is complex, for example finire vs servire
		return new GetPresentRadicalReturn(radical, verbEndingCategory);
	}

	/**
	 * Class used to get two return values from the getPresentRadical method
	 * @author vaudrypl
	 */
	protected class GetPresentRadicalReturn {
		public final String radical;
		public final int verbEndingCategory;
		
		public GetPresentRadicalReturn(String radical, int verbEndingCategory) {
			this.radical = radical;
			this.verbEndingCategory = verbEndingCategory;
		}
	}

	/**
	 * Adds a radical and a suffix applying phonological rules
	 * Reference : sections 760-761 of Grevisse (1993)
	 * 
	 * @param radical
	 * @param suffix
	 * @return resultant form
	 */
	public String addSuffix(String radical, String suffix) {
		int length = radical.length();
		/*// change "c" to "ç" and "g" to "ge" before "a" and "o";
		if (suffix.matches(a_o_regex)) {
			if (radical.endsWith("c")) {
				radical = radical.substring(0, length-1) + "ç";
			} else if (radical.endsWith("g")) {
				radical += "e";
			}
		}
		// if suffix begins with mute "e"
		if (!suffix.equals("ez") && suffix.startsWith("e")) {
			// change "y" to "i" if not in front of "e"
			if (!radical.endsWith("ey") && radical.endsWith("y")) {
				radical = radical.substring(0,length-1) + "i";
			}
			// change "e" and "é" to "è" in last sillable of radical
			char penultimate = radical.charAt(length-2);
			if (penultimate == 'e' || penultimate == 'é') {
				radical = radical.substring(0,length-2) + "è"
						+ radical.substring(length-1);
			}
		}*/
		return radical + suffix;
	}

	/**
	 * Builds the simple future form for all verbs. 
	 * Reference : Mansouri (1996)
	 *
	 * @param radical
	 *            the future radical of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected String buildFutureVerb(String radical, NumberAgreement number,
			Person person) {
		
		String suffix = "";
		
		switch ( number ) {
		case SINGULAR: case BOTH:
			switch ( person ) {
			case FIRST:
				suffix = "ò";
				break;
			case SECOND:
				suffix = "ai";
				break;
			case THIRD:
				suffix = "à";
				break;
			}
			break;
		case PLURAL:
			switch ( person ) {
			case FIRST:
				suffix = "emo";
				break;
			case SECOND:
				suffix = "ete";
				break;
			case THIRD:
				suffix = "anno";
				break;
			}
			break;
		}
		
		return radical + suffix;
	}

	/**
	 * Builds the conditional present form for all regular verbs. 
	 * Reference : Patota pg. 145
	 *
	 * @param radical
	 *            the future radical of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected String buildConditionalVerb(String radical, NumberAgreement number,
			Person person, String baseForm) {
		//Cristina B. modified for italian
		String suffix = "";
		
		switch(number){
			case SINGULAR: case BOTH:
				switch(person){
				case FIRST:
					//suffix="rei";
					suffix="ei";
					break;
				case SECOND:
					//suffix= "resti";
					suffix= "esti";
					break;
				case THIRD:
					//suffix= "rebbe";
					suffix="ebbe";
					break;
				}
				break;
			case PLURAL:
				switch(person){
				case FIRST:
					//suffix= "remmo";
					suffix="emmo";
					break;
				case SECOND:
					//suffix= "reste";
					suffix= "este";
					break;
				case THIRD:
					//suffix= "rebbero";
					suffix="ebbero";
					break;
				}
				break;
			}
		
		return radical + suffix;
	}

	/**
	 * @param baseForm
	 * @param element 
	 * @param baseWord 
	 * @return the radical used in indicative simple future
	 *         and conditional present regular verb
	 */
	protected String getFutureConditionalRadical(String baseForm, NLGElement element, NLGElement baseWord) {
		// try to get inflected form from user feature or lexicon
		String radical = element.getFeatureAsString(ItalianLexicalFeature.FUTURE_RADICAL);
		if (radical == null && baseWord != null) {
			radical = baseWord.getFeatureAsString(ItalianLexicalFeature.FUTURE_RADICAL);
			
		}
		//System.out.println("Radice futuro nel lessico: " + radical);
		// otherwise apply general rules based on infinitive (base form)
		if (radical == null) {
			int length = baseForm.length();
			//Cristina B. qui dovremmo proveddere a costruire 
			//il radicale in maniera corretta
			// for now we take a simple solution 
			// and the future radical is the radical from the infinite
			//based on are, ere, ire
			//See Patota 157-163 + 182-226
			
			//1 coniugazione e.g. amare --> am - e- ro e.g., camb i are --> camb i e ro
			//e.g., avv i are --> avv i e ro
			//no per mang i are --> mang e ro,  fare etc.
			if(baseForm.endsWith("are")){
				if(baseForm.endsWith("care") || baseForm.endsWith("gare"))
					radical = baseForm.substring(0, length-3)+"h" + "er";
				else if (baseForm.endsWith("ciare") || baseForm.endsWith("giare")){
					radical = baseForm.substring(0, length-4)+"er";
				}
				else{
					radical = baseForm.substring(0, length-3)+"er";
				}
			}
			//2 coniug. e.g., leggere, vincere, reggere
			//3 coniug. e.g, servire, finire
			//no vedere --> vedrá e non vede - ra
			else if(baseForm.endsWith("ere")){
				radical = baseForm.substring(0, length-1);
			}
			//2 coniug. e.g., condurre, dedurre
			else if(baseForm.endsWith("urre")){
				radical = baseForm.substring(0, length-1);
			}
			//default?
			else{
				radical = baseForm.substring(0, length-1);
			}		
		}
		
		return radical;
	}

	/**
	 * Adds the "imparfait" and "conditionel" suffix to a verb radical. 
	 * Reference : Mansouri (1996)
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @param number
	 * @param person
	 * @return the inflected word.
	 */
	protected String addImparfCondSuffix(String radical, NumberAgreement number,
			Person person) {
		String suffix = "";
		
		//Cristina B.
		//modified suffixes for italian
		
		//special case "essere"
		if(radical.equals("er") || radical.equals("essere")){
			switch ( number ) {
			case SINGULAR: case BOTH:
				switch ( person ) {
				case FIRST:
					//suffix = "ais";
					suffix = "o";
					break;
				case SECOND:
					//suffix = "ais";
					suffix = "i";
					break;
				case THIRD:
					//suffix = "ait";
					suffix = "a";
					break;
				}
				break;
			case PLURAL:
				switch ( person ) {
				case FIRST:
					//suffix = "ions";
					suffix = "avamo";
					break;
				case SECOND:
					//suffix = "iez";
					suffix = "avate";
					break;
				case THIRD:
					//suffix = "aient";
					suffix = "ano";
					break;
				}
				break;
			}
			//radical = "er";
		}
		else{
			switch ( number ) {
			case SINGULAR: case BOTH:
				switch ( person ) {
				case FIRST:
					//suffix = "ais";
					suffix = "vo";
					break;
				case SECOND:
					//suffix = "ais";
					suffix = "vi";
					break;
				case THIRD:
					//suffix = "ait";
					suffix = "va";
					break;
				}
				break;
			case PLURAL:
				switch ( person ) {
				case FIRST:
					//suffix = "ions";
					suffix = "vamo";
					break;
				case SECOND:
					//suffix = "iez";
					suffix = "vate";
					break;
				case THIRD:
					//suffix = "aient";
					suffix = "vano";
					break;
				}
				break;
			}
		}
		return radical + suffix;
	}
	
	

	/**
	 * Builds the past participle form for regular verbs. 
	 * Reference : Patota
	 *
	 * @param baseForm
	 *            the base form of the word.
	 * @return the inflected word.
	 */
	protected String buildPastParticipleVerb(String baseForm) {
		//Cristina B. 
		//changed for italian
		int length = baseForm.length();
		String realised = baseForm;
		
		// based on "amare", no "fare"
		if (baseForm.endsWith("are")) {
			realised = baseForm.substring(0, length-3) + "ato";
		// base on "contenere", second coniugation has a lot of irregular verbs
		} else if (baseForm.endsWith("ere")) {
			realised = baseForm.substring(0, length-3) + "uto";
		// based on "finire", some irregular verbs such as "apparire", "aprire"
		} else if (baseForm.endsWith("ire")) {
			realised = baseForm.substring(0, length-3) + "ito";
		// based on "mettre"
		} 
		
		return realised;
	}

	/**
	 * This method performs the morphology for adverbs.
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @param baseWord
	 *            the <code>WordElement</code> as created from the lexicon
	 *            entry.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	public NLGElement doAdverbMorphology(InflectedWordElement element,
			WordElement baseWord) {

		String realised = null;

		// base form from baseWord if it exists, otherwise from element
		String baseForm = getBaseForm(element, baseWord);

		// Comparatives and superlatives 
		if (element.getFeatureAsBoolean(Feature.IS_COMPARATIVE).booleanValue()) {
			realised = element.getFeatureAsString(LexicalFeature.COMPARATIVE);

			if (realised == null && baseWord != null) {
				realised = baseWord
						.getFeatureAsString(LexicalFeature.COMPARATIVE);
			}
			if (realised == null) realised = baseForm;
		} else {
			realised = baseForm;
		}

		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised, element);
		return realisedElement;
	}

	/**
	 * This method performs the morphology for pronouns.
	 * Reference : sections 633-634 of Grevisse (1993)
	 * 
	 * @param element
	 *            the <code>InflectedWordElement</code>.
	 * @return a <code>StringElement</code> representing the word after
	 *         inflection.
	 */
	public NLGElement doPronounMorphology(InflectedWordElement element) {
		String realised = element.getBaseForm();
		
		Object type = element.getFeature(ItalianLexicalFeature.PRONOUN_TYPE);
		// inflect only personal pronouns, exluding complement pronouns ("ci" and "ne")
		if (type == PronounType.PERSONAL
			&& element.getFeature(InternalFeature.DISCOURSE_FUNCTION)
				!= DiscourseFunction.COMPLEMENT) {
			
			// this will contain the features we want the pronoun to have
			Map<String, Object> pronounFeatures = new HashMap<String, Object>();
	
			pronounFeatures.put(ItalianLexicalFeature.PRONOUN_TYPE, type);
			
			boolean passive = element.getFeatureAsBoolean(Feature.PASSIVE);
			
			//boolean reflexive = element.getFeatureAsBoolean(LexicalFeature.REFLEXIVE);
			//Cristina B. we don't need this in italian
			//boolean detached = isDetachedPronoun(element);
			NLGElement parent = element.getParent();
			
			Object gender = element.getFeature(LexicalFeature.GENDER);
			
			if (!(gender instanceof Gender) || gender == Gender.NEUTER) gender = Gender.MASCULINE;
			
			
			Object person = element.getFeature(Feature.PERSON);
			Object number = element.getFeature(Feature.NUMBER);		
			
			if (!(person instanceof Person)) person = Person.THIRD;
			if (!(number instanceof NumberAgreement)) number = NumberAgreement.SINGULAR;
			
			Object function = element.getFeature(InternalFeature.DISCOURSE_FUNCTION);
			
			// If the pronoun is the head of a noun phrase,
			// take the discourse function of this noun phrase
			if (function == DiscourseFunction.SUBJECT && parent != null
					&& parent.isA(PhraseCategory.NOUN_PHRASE)) {
				function = parent.getFeature(InternalFeature.DISCOURSE_FUNCTION);
				if(!(function instanceof DiscourseFunction)) function = DiscourseFunction.SUBJECT;
				if (passive) {
					if (function == DiscourseFunction.SUBJECT) function = DiscourseFunction.OBJECT;
					else if (function == DiscourseFunction.OBJECT) function = DiscourseFunction.SUBJECT;
				}
			}
			pronounFeatures.put(Feature.PERSON, person);
			
			// select wich features to include in search depending on pronoun features,
			// syntactic function and wether the pronoun is detached from the verb
			if (person == Person.THIRD) {
				pronounFeatures.put(Feature.NUMBER, number);
				pronounFeatures.put(InternalFeature.DISCOURSE_FUNCTION, function);
				//System.out.println("***Discourse function" + element.getBaseForm() + function.toString() );
				//
				if (number != NumberAgreement.PLURAL
					|| function == DiscourseFunction.OBJECT) {
					pronounFeatures.put(LexicalFeature.GENDER, gender);
				}
			}
			//}
			else {
				//pronounFeatures.put(LexicalFeature.GENDER, gender);
				pronounFeatures.put(Feature.NUMBER, number);
				pronounFeatures.put(InternalFeature.DISCOURSE_FUNCTION, function);
				pronounFeatures.put(Feature.PERSON, person);
			}
				
			if(passive){
				pronounFeatures.put(ItalianLexicalFeature.STRONG_FORM, true);
			}
			
			Lexicon lexicon = element.getLexicon();
			// search the lexicon for the right pronoun
			WordElement proElement =
				lexicon.getWord(LexicalCategory.PRONOUN, pronounFeatures);
			
			// if the right pronoun is not found in the lexicon,
			// leave the original pronoun
			if (proElement != null) {
				element = new InflectedWordElement(proElement);
				realised = proElement.getBaseForm();
			}
			//System.out.println(" morfologia di " + realised + "-->" + pronounFeatures.toString());
		// Agreement of relative pronouns with parent noun phrase.
		} else if (type == PronounType.RELATIVE) {
			// Get parent clause.
			NLGElement antecedent = element.getParent();
			while (antecedent != null
					&& !antecedent.isA(PhraseCategory.CLAUSE)) {
				antecedent = antecedent.getParent();
			}
/*			// Skip parent noun phrase if necessary.
			if (antecedent != null && antecedent.isA(PhraseCategory.NOUN_PHRASE)) {
				antecedent = antecedent.getParent();
			}
			
			// Skip parent prepositional phrase if necessary.
			if (antecedent != null && antecedent.isA(PhraseCategory.PREPOSITIONAL_PHRASE)) {
				antecedent = antecedent.getParent();
			}
*/
			
			if (antecedent != null) {
				// Get parent noun phrase of parent clause.
				antecedent = antecedent.getParent();
				if (antecedent != null) {
					boolean feminine = antecedent.getFeature(LexicalFeature.GENDER)
							== Gender.FEMININE;
					boolean plural = antecedent.getFeature(Feature.NUMBER)
							== NumberAgreement.PLURAL;
					
					// Lookup lexical entry for appropriate form.
					// If the corresponding form is not found :
					// Feminine plural defaults to masculine plural.
					// Feminine singular and masculine plural default
					// to masculine singular.
					String feature = null;
					if (feminine && plural) {
						feature = element.getFeatureAsString(
								ItalianLexicalFeature.FEMININE_PLURAL);
					} else if (feminine) {
						feature = element.getFeatureAsString(
								ItalianLexicalFeature.FEMININE_SINGULAR);
					}
					
					if (plural && feature == null ) {
						feature = element.getFeatureAsString(
								LexicalFeature.PLURAL);
					}
					
					if (feature != null) realised = feature;
				}
			}
		}
		realised += getParticle(element);
		StringElement realisedElement = new StringElement(realised, element);
	
		return realisedElement;
	}
}
