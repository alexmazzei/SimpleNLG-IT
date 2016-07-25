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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import simplenlg.features.ClauseStatus;
import simplenlg.features.DiscourseFunction;
import simplenlg.features.Feature;
import simplenlg.features.Gender;
import simplenlg.features.InternalFeature;
import simplenlg.features.InterrogativeType;
import simplenlg.features.LexicalFeature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Person;
import simplenlg.features.Form;
import simplenlg.features.Tense;
import simplenlg.features.italian.ItalianFeature;
import simplenlg.features.italian.ItalianLexicalFeature;
import simplenlg.features.italian.PronounType;
import simplenlg.features.italian.ItalianInternalFeature;
import simplenlg.framework.CoordinatedPhraseElement;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.PhraseCategory;
import simplenlg.framework.ListElement;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.framework.PhraseElement;
import simplenlg.framework.StringElement;
import simplenlg.framework.WordElement;
import simplenlg.lexicon.Lexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.PPPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;

/**
 * This class contains static methods to help the syntax processor realise verb
 * phrases for Italian. Based on the French Verb Phrase Helper.
 * 
 * @author Cristina B. based on french version by vaudrypl
 */
public class VerbPhraseHelper extends simplenlg.syntax.english.nonstatic.VerbPhraseHelper {
	
	/**
	 * The main method for realising verb phrases.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> to be realised.
	 * @return the realised <code>NLGElement</code>.
	 */
	@Override
	public NLGElement realise(PhraseElement phrase) {
		ListElement realisedElement = null;
		Stack<NLGElement> vgComponents = null;
		Stack<NLGElement> mainVerbRealisation = new Stack<NLGElement>();
		Stack<NLGElement> auxiliaryRealisation = new Stack<NLGElement>();

		if (phrase != null) {
			vgComponents = createVerbGroup(phrase);
			splitVerbGroup(vgComponents, mainVerbRealisation,
					auxiliaryRealisation);

			// vaudrypl added phrase argument to ListElement constructor
			// to copy all features from the PhraseElement
			realisedElement = new ListElement(phrase);

			if ((!phrase.hasFeature(InternalFeature.REALISE_AUXILIARY)
					|| phrase.getFeatureAsBoolean(InternalFeature.REALISE_AUXILIARY))
							&& !auxiliaryRealisation.isEmpty()) {

				realiseAuxiliaries(realisedElement,
						auxiliaryRealisation);

				NLGElement verb = mainVerbRealisation.peek();
				Object verbForm = verb.getFeature(Feature.FORM);
				if (verbForm == Form.INFINITIVE) {
					realiseMainVerb(phrase, mainVerbRealisation,
							realisedElement);
					phrase.getPhraseHelper().realiseList(realisedElement, phrase
							.getPreModifiers(), DiscourseFunction.PRE_MODIFIER);
				} else {
					phrase.getPhraseHelper().realiseList(realisedElement, phrase
							.getPreModifiers(), DiscourseFunction.PRE_MODIFIER);
					realiseMainVerb(phrase, mainVerbRealisation,
							realisedElement);
				}
			} else {
				realiseMainVerb(phrase, mainVerbRealisation,
						realisedElement);
				phrase.getPhraseHelper().realiseList(realisedElement, phrase
						.getPreModifiers(), DiscourseFunction.PRE_MODIFIER);
			
			}
			realiseComplements(phrase, realisedElement);
			phrase.getPhraseHelper().realiseList(realisedElement, phrase
					.getPostModifiers(), DiscourseFunction.POST_MODIFIER);
		}
		
		return realisedElement;
	}
	
	/**
	 * Checks to see if the base form of the word is copular.
	 * 
	 * @param element
	 *            the element to be checked
	 * @return <code>true</code> if the element is copular.
	 */
	@Override
	public boolean isCopular(NLGElement element) {
		if (element != null) {
			return element.getFeatureAsBoolean(ItalianLexicalFeature.COPULAR);
		} else return true;
	}
	
	/**
	 * Splits the stack of verb components into two sections. One being the verb
	 * associated with the main verb group, the other being associated with the
	 * auxiliary verb group.
	 * 
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @param mainVerbRealisation
	 *            the main group of verbs.
	 * @param auxiliaryRealisation
	 *            the auxiliary group of verbs.
	 */
	@Override
	protected void splitVerbGroup(Stack<NLGElement> vgComponents,
			Stack<NLGElement> mainVerbRealisation,
			Stack<NLGElement> auxiliaryRealisation) {

		boolean mainVerbSeen = false;
		boolean cliticsSeen = false;

		for (NLGElement word : vgComponents) {
			if (!mainVerbSeen) {
				mainVerbRealisation.push(word);
//				if (!word.equals("pas") &&
				if (!word.isA(LexicalCategory.ADVERB) &&
						!word.getFeatureAsBoolean(ItalianInternalFeature.CLITIC)) {
					mainVerbSeen = true;
				}
			} else if (!cliticsSeen) {
//				if (!word.equals("ne") &&
				if (!"non".equals(word.getFeatureAsString(LexicalFeature.BASE_FORM)) &&
						!word.getFeatureAsBoolean(ItalianInternalFeature.CLITIC)) {
					cliticsSeen = true;
					auxiliaryRealisation.push(word);
				} else {
					mainVerbRealisation.push(word);
				}
			} else {
				auxiliaryRealisation.push(word);
			}
		}

	}
	
	/**
	 * Creates a stack of verbs for the verb phrase. Additional auxiliary verbs
	 * are added as required based on the features of the verb phrase.
	 * 
	 * Based on English method of the same name.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @return the verb group as a <code>Stack</code> of <code>NLGElement</code>
	 *         s.
	 */
	@Override
	@SuppressWarnings("deprecation")
	protected Stack<NLGElement> createVerbGroup(PhraseElement phrase) {

		String actualModal = null;
		Object formValue = phrase.getFeature(Feature.FORM);
		Tense tenseValue = phrase.getTense();
		String modal = phrase.getFeatureAsString(Feature.MODAL);
		//boolean modalPast = false;
		Stack<NLGElement> vgComponents = new Stack<NLGElement>();
		boolean interrogative = phrase.hasFeature(Feature.INTERROGATIVE_TYPE);
		boolean progressive = phrase.getFeatureAsBoolean(Feature.PROGRESSIVE);
		boolean perfect = phrase.getFeatureAsBoolean(Feature.PERFECT);
		boolean passive = phrase.getFeatureAsBoolean(Feature.PASSIVE);
		boolean negative = phrase.getFeatureAsBoolean(Feature.NEGATED);
		boolean reflexive = phrase.getFeatureAsBoolean(ItalianLexicalFeature.REFLEXIVE);

		NLGFactory factory = phrase.getFactory();
		boolean insertClitics = true;
		
		
		NLGElement parent = phrase.getParent();
		
		WordElement modalWord =	null;
		boolean cliticRising = false;
		if (modal != null) {
			modalWord = phrase.getLexicon().lookupWord(modal, LexicalCategory.VERB);
			//cliticRising = modalWord.getFeatureAsBoolean(ItalianLexicalFeature.CLITIC_RISING);
		}

		if (Form.INFINITIVE.equals(formValue)) {
			actualModal = null;
		
		} else if (formValue == null || formValue == Form.NORMAL
				|| (formValue == Form.IMPERATIVE && cliticRising)) {
			if (modal != null) {
				actualModal = modal;
			}
		}
		
		if (actualModal == null) modalWord = null;
		
		NLGElement frontVG = grabHeadVerb(phrase, tenseValue, modal != null);
		if (frontVG == null) 
			return vgComponents;
		
		frontVG.setFeature(Feature.TENSE, tenseValue);
		
		//REFLEXIVE VERB FORM
		//take from the lexicon italian feature reflexive for verbs for pronominal verbs
		//such as "vergognarsi", "pentirsi"
		if(!reflexive)
			reflexive = frontVG.getFeatureAsBoolean(ItalianLexicalFeature.REFLEXIVE);
		
		//check if the user set the CLAUSE (parent of the VP: CLAUSE-> VP -> frontVG)
		//as reflexive for transitive verbs such as
		//"lavare-lavarsi
		if(!reflexive && parent!= null){
			reflexive = parent.getFeatureAsBoolean(ItalianLexicalFeature.REFLEXIVE);
			//System.out.println("check  ***" + reflexive);
		}
		
		if (passive) {
			frontVG = addPassiveAuxiliary(frontVG, vgComponents, phrase);
			frontVG.setFeature(Feature.TENSE, tenseValue);
		}
		
		//modal for italian
		if(actualModal != null){
			
			//we push the front as INFINITE on the stack and the modal is the new front
			frontVG = pushModal(frontVG, modalWord, phrase, vgComponents);
		}
		//Cristina B. 
		//Analyzing form, tense, progressive and perfect features for italian based on 
		//Patota's Grammar
		
		//Progressive just for present, future and past in NORMAL form
		if (progressive && formValue == Form.NORMAL){
			
			if(tenseValue == Tense.PLUS_PAST || tenseValue == Tense.REMOTE_PAST || tenseValue == Tense.PLUS_REMOTE_PAST){
				tenseValue = Tense.PAST;
				frontVG.setFeature(Feature.TENSE, Tense.PAST);
				//System.out.println("change tense to PAST");
			}
			
			NLGElement newFront =
					addProgressiveAuxiliary(frontVG, vgComponents, factory, phrase, reflexive);
			if (frontVG != newFront) {
				frontVG = newFront;
				frontVG.setFeature(Feature.TENSE, tenseValue);
				//Normalizing tense for not correct set up by the user
				if(perfect){
					if(tenseValue == Tense.FUTURE){
						frontVG.setFeature(Feature.TENSE, Tense.PRESENT);
						tenseValue = Tense.PRESENT;
					}
					else{
						frontVG.setFeature(Feature.TENSE, Tense.PAST);
						tenseValue = Tense.PAST;
					}
				}
				//insertClitics = false;
			}
		}			//normalizing tenses for composed verbs
		else if (!progressive && formValue == Form.NORMAL && !perfect){
			if(tenseValue == Tense.PLUS_PAST || tenseValue == Tense.REMOTE_PAST || tenseValue == Tense.PLUS_REMOTE_PAST){
				tenseValue = Tense.PAST;
				frontVG.setFeature(Feature.TENSE, Tense.PAST);
			}
		}
		
		//COMPOSED VERBS
		//Tense with aux "essere" or "avere"
		AddAuxiliaryReturn auxReturn = null;
		//aux tense
		Tense tense = tenseValue;
		//NORMAL FORM
		//passato prossimo, trapassato prossimo, trapassato remoto. futuro anteriore
		if ( !progressive  && formValue == Form.NORMAL && tenseValue != Tense.REMOTE_PAST && perfect)
		{
			if(formValue == Form.NORMAL){
				if(tenseValue == Tense.PAST){
					tense = Tense.PRESENT;
				}
				else if(tenseValue == Tense.PLUS_PAST){
					//trapassato prossimo --> aux imperfetto
					tense = Tense.PAST;
				}
				else if(tenseValue == Tense.PLUS_REMOTE_PAST){
					tense = Tense.REMOTE_PAST;
				}//for compatibility with french API
				else if (tenseValue == Tense.CONDITIONAL){
					//it's ok 
				}
				auxReturn = addAuxiliary(frontVG, vgComponents, modal, tense, phrase, parent, reflexive);
				frontVG = auxReturn.newFront;
			}
			else if(formValue == Form.CONDITIONAL){
				
			}
		}
		//CONDITIONAL FORM
		//condizionale passato
		else if (formValue == Form.CONDITIONAL){
			
			if( !((tenseValue == Tense.PRESENT || tenseValue == Tense.FUTURE) && !progressive && !perfect) &&
					!((tenseValue == Tense.PRESENT || tenseValue == Tense.FUTURE) && progressive && !perfect)){
				//System.out.println("conditional aux");
				tense = Tense.PRESENT;
				auxReturn = addAuxiliary(frontVG, vgComponents, modal, tense, phrase, parent, reflexive);
				frontVG = auxReturn.newFront;
			}
		}
		//SUBJUNCTIVE FORM
		else if (formValue == Form.SUBJUNCTIVE){
			if(perfect && tenseValue != Tense.FUTURE){
				//System.out.println("congiuntivo aux");
				//tense for aux
				if(tenseValue == Tense.PAST || tenseValue == Tense.REMOTE_PAST)
					tense = Tense.PRESENT;
				else if (tenseValue == Tense.PLUS_REMOTE_PAST)
					tense = Tense.PLUS_PAST;
				
				auxReturn = addAuxiliary(frontVG, vgComponents, modal, tense, phrase, parent, reflexive);
				frontVG = auxReturn.newFront;
			}
		}
		
		
		
		//frontVG = pushIfModal(actualModal != null, phrase, frontVG,	vgComponents);
		// insert clitics here if imperative and not negative
		// or if there is a modal verb without clitic rising 
		NLGElement cliticDirectObject = null;
		if (insertClitics) {
			if (!negative && formValue == Form.IMPERATIVE) {
				cliticDirectObject = insertCliticComplementPronouns(phrase, vgComponents,reflexive);
				insertClitics = false;
			} else if (frontVG == null) {
				if (!cliticRising) {
					cliticDirectObject = insertCliticComplementPronouns(phrase, vgComponents, reflexive);
					insertClitics = false;
				}
			}
		}
		
		createAdvNegation(phrase, vgComponents, frontVG, modal != null);
		
		//pushModal(modalWord, phrase, vgComponents);
		
		if (frontVG != null) {
			pushFrontVerb(phrase, vgComponents, frontVG, formValue,
					interrogative);
			frontVG.setFeature(Feature.FORM, formValue);
			//Cristina B.
			frontVG.setFeature(Feature.PROGRESSIVE, progressive);
			frontVG.setFeature(Feature.PERFECT, perfect);
		}
		// default place for inserting clitic complement pronouns
		if (insertClitics) {
			//Cristina B. 
			//qui sforzare inserimento clitico per arrabbiarsi, guardarsi etc...
			//System.out.println("prove per le...si qui entra sempre");
			cliticDirectObject = insertCliticComplementPronouns(phrase, vgComponents, reflexive);
			insertClitics = false;
		}
		createNon(phrase, vgComponents);
		
		if (auxReturn != null) {
			// Check if verb phrase is part of a relative clause with
			// the relative phrase being a direct object. In that case,
			// Make object agreement with the parent NP of the clause.
			if (!passive && parent != null && parent.hasRelativePhrase(DiscourseFunction.OBJECT)) {
				NLGElement grandParent = parent.getParent();
				if (grandParent instanceof NPPhraseSpec) {
					cliticDirectObject = grandParent;
				}
			}
			makePastParticipleWithAvoirAgreement(auxReturn.pastParticipleAvoir, cliticDirectObject);
		}

		return vgComponents;
	}
	
	/**
	 * Transfers the agreement features from the direct object to
	 * the past participle with auxiliary "avoir" if the direct object
	 * is placed before the past participle. (For now, this only means
	 * if there is a direct object clitic pronoun. Eventually it will
	 * include checks for relative clause, etc.)
	 * 
	 * @param auxReturn
	 * @param cliticDirectObject
	 */
	protected void makePastParticipleWithAvoirAgreement(
			NLGElement pastParticiple, NLGElement cliticDirectObject) {
		
		if (pastParticiple != null && cliticDirectObject != null) {
			Object gender = cliticDirectObject.getFeature(LexicalFeature.GENDER);
			if (gender instanceof Gender) {
				pastParticiple.setFeature(LexicalFeature.GENDER, gender);
			}
			
			Object number = cliticDirectObject.getFeature(Feature.NUMBER);
			if (number instanceof NumberAgreement) {
				pastParticiple.setFeature(Feature.NUMBER, number);
			}
		}
	}
	
	/**
	 * Determine wich pronominal complements are clitics and inserts
	 * them in the verb group components. For italian reflexive verbs, 
	 * the method automatically.adds the clitic.
	 * Reference :
	 * 
	 * @param phrase
	 * @param vgComponents
	 */
	protected NLGElement insertCliticComplementPronouns(PhraseElement phrase,
			Stack<NLGElement> vgComponents, boolean reflexive) {
		List<NLGElement> complements =
			phrase.getFeatureAsElementList(InternalFeature.COMPLEMENTS);
		boolean passive = phrase.getFeatureAsBoolean(Feature.PASSIVE);
		NLGElement pronounEn = null, pronounY = null,
					directObject = null, indirectObject = null;

		// identify clitic candidates
		for (NLGElement complement : complements) {
			if (complement != null && !complement.getFeatureAsBoolean(Feature.ELIDED)) {
				Object discourseValue = complement.getFeature(InternalFeature.DISCOURSE_FUNCTION);
				if (!(discourseValue instanceof DiscourseFunction)) {
					discourseValue = DiscourseFunction.COMPLEMENT;
				}
				// Realise complement only if it is not the relative phrase of
				// the parent clause and not a phrase with the same function in case
				// of a direct or indirect object.
				NLGElement parent = phrase.getParent();
				if ( parent == null ||
						(complement != parent.getFeatureAsElement(ItalianFeature.RELATIVE_PHRASE) &&
							(discourseValue == DiscourseFunction.COMPLEMENT ||
								!parent.hasRelativePhrase((DiscourseFunction) discourseValue)))) {
					NLGElement head = null;
					Object type = null;
					
					// if a complement is or contains a pronoun, or will be pronominalised
					if (complement.isA(LexicalCategory.PRONOUN)) {
						head = complement;
					} else if (complement instanceof NPPhraseSpec
							&& ((NPPhraseSpec)complement).getHead() != null
							&& ((NPPhraseSpec)complement).getHead().isA(LexicalCategory.PRONOUN)) {
						head = ((NPPhraseSpec)complement).getHead();
					}
					else if (complement.getFeatureAsBoolean(Feature.PRONOMINAL)) {
						type = PronounType.PERSONAL;
					}
					
					if (head != null) {
						type = head.getFeature(ItalianLexicalFeature.PRONOUN_TYPE);
					}
					
					if (type != null) {
						complement.setFeature(ItalianInternalFeature.CLITIC, false);
						if (type == PronounType.SPECIAL_PERSONAL) {
							String baseForm = ((WordElement)head).getBaseForm();
							if (baseForm.equals("ne")) {
								pronounEn = complement;
							}
							else if (baseForm.equals("ci")) {
								pronounY = complement;
							}
						} else if (type == PronounType.PERSONAL) {
							Object discourseFunction = complement.getFeature(InternalFeature.DISCOURSE_FUNCTION);
							if (discourseFunction == DiscourseFunction.OBJECT && !passive) {
								directObject = complement;
							} else if (discourseFunction == DiscourseFunction.INDIRECT_OBJECT) {
								indirectObject = complement;
							}
						}
					}
				}
			}
		}
		
		//if(phrase.getFeatureAsBoolean(featureName))
		
		// place clitics in order :
		// (indirect object) (direct object) ci ne
		
		if (pronounEn != null) {
			pronounEn.setFeature(ItalianInternalFeature.CLITIC, true);
			vgComponents.push(pronounEn);
		}
		
		if (pronounY != null) {
			pronounY.setFeature(ItalianInternalFeature.CLITIC, true);
			vgComponents.push(pronounY);
		}
		
		if (directObject != null) {
			directObject.setFeature(ItalianInternalFeature.CLITIC, true);
			//if the clitic is before the verb and it is an indirect object,
			//we need to set the weak form 
			//if(before)
				//directObject.setFeature(ItalianLexicalFeature.WEAK_FORM, true);
			vgComponents.push(directObject);
		}
		// if no direct object can be a reflexive verb
		else if(reflexive){
			//System.out.println("*** reflexive verb - creating pronoun***");
			NLGFactory factory = phrase.getFactory();
			Lexicon lexicon = factory.getLexicon();
				
			WordElement pronoun = null;
				
			Object personValue = phrase.getFeature(Feature.PERSON);
			Person person;
			Object numberValue = phrase.getFeature(Feature.NUMBER);
			NumberAgreement number;
			if (numberValue instanceof NumberAgreement) {
				number = (NumberAgreement) numberValue;
				}//default is singular
			else {
					number = NumberAgreement.SINGULAR;
			}
			if (personValue instanceof Person) {
				person=(Person) personValue;
			}//default is third
			else{
				person = Person.THIRD;
			}
				//for the third person (both singular and plural) the pronoun is "si"
			if(person == Person.THIRD){
				pronoun = lexicon.lookupWord("si", LexicalCategory.PRONOUN);
			}
			else {
				if(number == NumberAgreement.SINGULAR){
					if (person == Person.FIRST)
						pronoun = lexicon.lookupWord("mi", LexicalCategory.PRONOUN);
					else if (person == Person.SECOND)
						pronoun = lexicon.lookupWord("ti", LexicalCategory.PRONOUN);
				}
				else {
					if (person == Person.FIRST)
						pronoun = lexicon.lookupWord("ci", LexicalCategory.PRONOUN);
					else if (person == Person.SECOND)
						pronoun = lexicon.lookupWord("vi", LexicalCategory.PRONOUN);
				}
			}
			
			pronoun.setFeature(InternalFeature.NON_MORPH, true);
			pronoun.setFeature(InternalFeature.DISCOURSE_FUNCTION, DiscourseFunction.COMPLEMENT);
			NPPhraseSpec reflexivePronoun = factory.createNounPhrase(pronoun);
			vgComponents.push(reflexivePronoun);
		}
		
		if(indirectObject != null){
			//do nothing, we use the form "a + indirect object with strong form of the pronoun
		}
		
		// return the direct object for use with past participle agreement with auxiliary "avoir"
		return directObject;
	}
	
	/**
	 * Checks to see if the phrase is in infinitive form. If it is then
	 * no morphology is done on the main verb.
	 * 
	 * Based on English method checkImperativeInfinitive(...)
	 * 
	 * @param formValue
	 *            the <code>Form</code> of the phrase.
	 * @param frontVG
	 *            the first verb in the verb group.
	 */
	protected void checkInfinitive(Object formValue,
			NLGElement frontVG) {

		if ((Form.INFINITIVE.equals(formValue) || Form.BARE_INFINITIVE.equals(formValue))
				&& frontVG != null) {
			frontVG.setFeature(InternalFeature.NON_MORPH, true);
		}
	}

	/**
	 * Adds the passive auxiliary verb to the front of the group.
	 * 
	 * Based on English method addBe(...)
	 * 
	 * @param frontVG
	 *            the first verb in the verb group.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @return the new element for the front of the group.
	 */
	protected NLGElement addPassiveAuxiliary(NLGElement frontVG,
			Stack<NLGElement> vgComponents, PhraseElement phrase) {

		// adds the current front verb in pas participle form
		// with aggreement with the subject (auxiliary "être")
		if (frontVG != null) {
			frontVG.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
			Object number = phrase.getFeature(Feature.NUMBER);
			frontVG.setFeature(Feature.NUMBER, number);
			Object gender = phrase.getFeature(LexicalFeature.GENDER);
			frontVG.setFeature(LexicalFeature.GENDER, gender);
			vgComponents.push(frontVG);
		}
		// adds auxiliary "être"
		WordElement passiveAuxiliary = (WordElement)
			frontVG.getLexicon().lookupWord("essere", LexicalCategory.VERB); //$NON-NLS-1$
		return new InflectedWordElement(passiveAuxiliary);
	}
	
	/**
	 * Adds the progressive auxiliary verb to the front of the group.
	 * 
	 * @param frontVG
	 *            the first verb in the verb group.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @return the new element for the front of the group.
	 */
	protected NLGElement addProgressiveAuxiliary(NLGElement frontVG,
			Stack<NLGElement> vgComponents, NLGFactory factory, PhraseElement phrase, boolean reflexive) {
		
		
		
		//Cristina B. modified for italian
		// pushes on stack "stare " + clitics + verb in infinitive form
		if (frontVG != null) {
			frontVG.setFeature(Feature.FORM, Form.GERUND);
			vgComponents.push(frontVG);
			// adds auxiliary "stare"
			WordElement passiveAuxiliary = (WordElement)
				frontVG.getLexicon().lookupWord("stare", LexicalCategory.VERB); //$NON-NLS-1$
			frontVG = new InflectedWordElement(passiveAuxiliary);
		}
		return frontVG;
	}

	/**
	 * Adds <em>have</em> to the stack.
	 * 
	 * @param frontVG
	 *            the first verb in the verb group.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @param modal
	 *            the modal to be used.
	 * @param tenseValue
	 *            the <code>Tense</code> of the phrase.
	 * @param parent 
	 * @return the new element for the front of the group.
	 */
	protected AddAuxiliaryReturn addAuxiliary(NLGElement frontVG,
			Stack<NLGElement> vgComponents, String modal, Tense tenseValue,
			PhraseElement phrase, NLGElement parent, boolean reflexive) {
		//Cristina B. modified for the italian:
		//creating 'avere' e 'essere'
		NLGElement newFront = frontVG, pastParticipleAvoir = null;
		WordElement auxiliaryWord = null;
		boolean aux_set_by_user = false;

		if (frontVG != null) {
			frontVG.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
			vgComponents.push(frontVG);
			// choose between "avere" or "essere" as auxiliary
			String auxiliary = "avere"; //$NON-NLS-1$
			if(parent != null){
				aux_set_by_user = parent.getFeatureAsBoolean(ItalianLexicalFeature.AUXILIARY_ESSERE);
			}
			if(aux_set_by_user == false){
				aux_set_by_user = phrase.getFeatureAsBoolean(ItalianLexicalFeature.AUXILIARY_ESSERE);
			}
			if ( frontVG.getFeatureAsBoolean(ItalianLexicalFeature.AUXILIARY_ESSERE)
					|| reflexive || aux_set_by_user) {
				// if auxiliary "essere", the past participle agrees with the subject
				auxiliary = "essere"; //$NON-NLS-1$
				Object number = phrase.getFeature(Feature.NUMBER);
				frontVG.setFeature(Feature.NUMBER, number);
				Object gender = phrase.getFeature(LexicalFeature.GENDER);
				frontVG.setFeature(LexicalFeature.GENDER, gender);
			} else {
				pastParticipleAvoir = frontVG;
			}
			
			auxiliaryWord = (WordElement)
				frontVG.getLexicon().lookupWord(auxiliary, LexicalCategory.VERB); //$NON-NLS-1$
		}
		newFront = new InflectedWordElement(auxiliaryWord);
		newFront.setFeature(Feature.FORM, Form.NORMAL);
		newFront.setFeature(Feature.TENSE, tenseValue);
		newFront.setFeature(Feature.PERFECT, phrase.getFeatureAsBoolean(Feature.PERFECT));
		newFront.setFeature(Feature.PROGRESSIVE, phrase.getFeatureAsBoolean(Feature.PROGRESSIVE));
		//System.out.println("PROVA VERB PHRASE: " + phrase.getFeatureAsBoolean(Feature.PERFECT));
		
		//if (modal != null) {
			//newFront.setFeature(InternalFeature.NON_MORPH, true);
		//}
		return new AddAuxiliaryReturn(newFront, pastParticipleAvoir);
	}
	
	
//	/**
//	 * Says if the verb phrase has a reflexive object (direct or indirect)
//	 * 
//	 * @param phrase	the verb phrase
//	 * @return			true if the verb phrase has a reflexive object (direct or indirect)
//	 */
//	protected boolean hasReflexiveObject(PhraseElement phrase) {
//		boolean reflexiveObjectFound = false;
//		List<NLGElement> complements =
//			phrase.getFeatureAsElementList(InternalFeature.COMPLEMENTS);
//		boolean passive = phrase.getFeatureAsBoolean(Feature.PASSIVE);
//		Object subjectPerson = phrase.getFeature(Feature.PERSON);
//		Object subjectNumber = phrase.getFeature(Feature.NUMBER);
//		if (subjectNumber != NumberAgreement.PLURAL) {
//			subjectNumber = NumberAgreement.SINGULAR;
//		}
//		
//		for (NLGElement complement : complements) {
//			if (complement != null && !complement.getFeatureAsBoolean(Feature.ELIDED)) {
//				
//				Object function = complement.getFeature(InternalFeature.DISCOURSE_FUNCTION);
//				boolean reflexive = complement.getFeatureAsBoolean(LexicalFeature.REFLEXIVE);
//				Object person = complement.getFeature(Feature.PERSON);
//				Object number = complement.getFeature(Feature.NUMBER);
//				if (number != NumberAgreement.PLURAL) {
//					number = NumberAgreement.SINGULAR;
//				}
//				
//				// if the complement is a direct or indirect object
//				if ( (function == DiscourseFunction.INDIRECT_OBJECT
//						|| (!passive && function == DiscourseFunction.OBJECT))
//					// and if it is reflexive, or the same as the subject if not third person
//					&& ( reflexive ||
//						((person == Person.FIRST || person == Person.SECOND)
//								&& person == subjectPerson && number == subjectNumber) )) {
//					reflexiveObjectFound = true;
//					break;
//				}
//			}
//		}
//		
//		return reflexiveObjectFound;
//	}

	/**
	 * Class used to get two return values from the addAuxiliary method
	 * @author vaudrypl
	 */
	protected class AddAuxiliaryReturn {
		public final NLGElement newFront, pastParticipleAvoir;
		
		public AddAuxiliaryReturn(NLGElement newFront, NLGElement pastParticipleAvoir) {
			this.newFront = newFront;
			this.pastParticipleAvoir = pastParticipleAvoir;
		}
	}
	
	/**
	 * Adds <em>pas</em> to the stack if the phrase is negated.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @param frontVG
	 *            the first verb in the verb group.
	 * @param hasModal
	 *            the phrase has a modal
	 * @return the new element for the front of the group.
	 */
	 
	protected void createAdvNegation(PhraseElement phrase,
			Stack<NLGElement> vgComponents, NLGElement frontVG, boolean hasModal) {
		//boolean pasForbiddenByArgument = phrase.checkIfNeOnlyNegation();
		if (phrase.getFeatureAsBoolean(Feature.NEGATED)){
			if( phrase.getFeature(ItalianFeature.NEGATION_AUXILIARY) != null){
				// first get negation auxiliary; it has to be specified by the user
				WordElement negation = null;
				Lexicon lexicon = phrase.getLexicon();
				Object negationObject = phrase.getFeature(ItalianFeature.NEGATION_AUXILIARY);
				if (negationObject instanceof WordElement) {
					negation = (WordElement) negationObject;
				} else if (negationObject != null) {
					String negationString;
					if (negationObject instanceof StringElement) {
						negationString = ((StringElement)negationObject).getRealisation();
					} else {
						negationString = negationObject.toString();
					}
				
					negation = lexicon.lookupWord(negationString);
				}
					
				//Cristina B. 
				//In italian the negation auxiliary has to be set by the user and there is 
				//no default
				//if (negation == null) {
					//negation = lexicon.lookupWord("pas", LexicalCategory.ADVERB);
				//}
					
				//Cristina B.
				//for now we suspended ne only negation check
				// push negation auxiliary if it's not forbidden by arguments that provoke
				// "ne" only negation or if the auxiliary is "plus"
				WordElement plus = lexicon.lookupWord("più", LexicalCategory.ADVERB);
				//if (!pasForbiddenByArgument || plus.equals(negation)) {
				if (plus.equals(negation)) {
					InflectedWordElement inflNegation = new InflectedWordElement( negation ); //$NON-NLS-1$
					vgComponents.push(inflNegation);
				}
			}//close if negated auxiliary
		}//close if negated
	}
	
	/**
	 * Adds <em>ne</em> to the stack if the phrase is negated or if
	 * it has a suject or complement that provokes "ne" only negation.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 */
	protected void createNon(PhraseElement phrase, Stack<NLGElement> vgComponents) {
		
		boolean neRequiredByArgument = phrase.checkIfNeOnlyNegation();

		if (phrase.getFeatureAsBoolean(Feature.NEGATED) || neRequiredByArgument) {
			InflectedWordElement ne = new InflectedWordElement( (WordElement)
				phrase.getFactory().createWord("non", LexicalCategory.ADVERB) ); //$NON-NLS-1$
	
			 vgComponents.push(ne);
		}
	}
	
	
	/**
	 * Determines the number agreement for the phrase.
	 * 
	 * @param parent
	 *            the parent element of the phrase.
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @return the <code>NumberAgreement</code> to be used for the phrase.
	 */
	@Override
	protected NumberAgreement determineNumber(NLGElement parent,
			PhraseElement phrase) {
		Object numberValue = phrase.getFeature(Feature.NUMBER);
		NumberAgreement number = null;
		
		if (numberValue instanceof NumberAgreement) {
			number = (NumberAgreement) numberValue;
		} else {
			number = NumberAgreement.SINGULAR;
		}
		
		return number;
	}

	/**
	 * Pushes the front verb onto the stack of verb components.
	 * Sets the front verb features.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 * @param frontVG
	 *            the first verb in the verb group.
	 * @param formValue
	 *            the <code>Form</code> of the phrase.
	 * @param interrogative
	 *            <code>true</code> if the phrase is interrogative.
	 */
	@Override
	protected void pushFrontVerb(PhraseElement phrase,
			Stack<NLGElement> vgComponents, NLGElement frontVG,
			Object formValue, boolean interrogative) {
		
		if (Form.GERUND.equals(formValue)) {
			frontVG.setFeature(Feature.FORM, Form.PRESENT_PARTICIPLE);
			vgComponents.push(frontVG);
		
		} else if (Form.PAST_PARTICIPLE.equals(formValue)) {
			frontVG.setFeature(Feature.FORM, Form.PAST_PARTICIPLE);
			vgComponents.push(frontVG);
		
		} else if (Form.PRESENT_PARTICIPLE.equals(formValue)) {
			frontVG.setFeature(Feature.FORM, Form.PRESENT_PARTICIPLE);
			vgComponents.push(frontVG);
		
		} else if (!(formValue == null || Form.NORMAL.equals(formValue)
						|| formValue == Form.SUBJUNCTIVE
						|| formValue == Form.IMPERATIVE || formValue == Form.CONDITIONAL)
				&& !isCopular(phrase.getHead()) && vgComponents.isEmpty()) {

			vgComponents.push(frontVG);
		
		} else {
			NumberAgreement numToUse = determineNumber(phrase.getParent(),
					phrase);
			frontVG.setFeature(Feature.PERSON, phrase
					.getFeature(Feature.PERSON));
			frontVG.setFeature(Feature.NUMBER, numToUse);
			vgComponents.push(frontVG);
		}
	}

	/**
	 * Add a modifier to a verb phrase. Use heuristics to decide where it goes.
	 * Based on method of the same name in English verb phrase helper.
	 * Reference : section 935 of Grevisse (1993)
	 * 
	 * @param verbPhrase
	 * @param modifier
	 * 
	 * @author vaudrypl
	 */
	@Override
	public void addModifier(VPPhraseSpec verbPhrase, Object modifier) {
		// Everything is postModifier

		if (modifier != null) {
		
			// get modifier as NLGElement if possible
			NLGElement modifierElement = null;
			if (modifier instanceof NLGElement)
				modifierElement = (NLGElement) modifier;
			else if (modifier instanceof String) {
				String modifierString = (String) modifier;
				if (modifierString.length() > 0 && !modifierString.contains(" "))
					modifierElement = verbPhrase.getFactory().createWord(modifier,
							LexicalCategory.ADVERB);
			}
		
			// if no modifier element, must be a complex string
			if (modifierElement == null) {
				verbPhrase.addPostModifier((String) modifier);
			} else {
				// default case
				verbPhrase.addPostModifier(modifierElement);
			}
		}
	}
	
	/**
	 * Realises the complements of this phrase.
	 * Based on English method of the same name.
	 * 
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 */
	@Override
	protected void realiseComplements(PhraseElement phrase,
			ListElement realisedElement) {

		ListElement indirects = new ListElement();
		ListElement directs = new ListElement();
		ListElement unknowns = new ListElement();
		Object discourseValue = null;
		NLGElement currentElement = null;

		for (NLGElement complement : phrase
				.getFeatureAsElementList(InternalFeature.COMPLEMENTS)) {
			if (!complement.getFeatureAsBoolean(ItalianInternalFeature.CLITIC)) {
				
				discourseValue = complement.getFeature(InternalFeature.DISCOURSE_FUNCTION);

				if (!(discourseValue instanceof DiscourseFunction)) {
					discourseValue = DiscourseFunction.COMPLEMENT;
				}

				// Realise complement only if it is not the relative phrase of
				// the parent clause and not a phrase with the same function in case
				// of a direct or indirect object.
				NLGElement parent = phrase.getParent();
				if ( parent == null ||
						(!complement.getFeatureAsBoolean(ItalianInternalFeature.RELATIVISED) &&
							complement != parent.getFeatureAsElement(ItalianFeature.RELATIVE_PHRASE) &&
							(discourseValue == DiscourseFunction.COMPLEMENT ||
								!parent.hasRelativePhrase((DiscourseFunction) discourseValue)))) {
					
					if (DiscourseFunction.INDIRECT_OBJECT.equals(discourseValue)) {
						complement = checkIndirectObject(complement);
						
					}
					//Cristina B.
					//System.out.println("VerbPhrasehelper, realising complement " + complement.printTree(" "));
					currentElement = complement.realiseSyntax();
					//System.out.println("after synta "+ currentElement.printTree(" "));
					
					if (currentElement != null) {
						currentElement.setFeature(InternalFeature.DISCOURSE_FUNCTION,
								discourseValue);
						//Cristina B. 
						//currentElement.setFeature(LexicalFeature.GENDER, phrase.getHead().getFeature(LexicalFeature.GENDER));
						if (DiscourseFunction.INDIRECT_OBJECT.equals(discourseValue)) {
							indirects.addComponent(currentElement);
						} else if (DiscourseFunction.OBJECT.equals(discourseValue)) {
							directs.addComponent(currentElement);
						} else {
							unknowns.addComponent(currentElement);
						}
					}
				} else {
				// Reset relativised feature if the complement was a relative phrase.
					complement.removeFeature(ItalianInternalFeature.RELATIVISED);
				}
			}
			// Reset the clitic selection feature after use.
			complement.removeFeature(ItalianInternalFeature.CLITIC);
		}
		
		
		// Reference : section 657 of Grevisse (1993)
		// normal order, when complements are all of the same length :
		// direct objects + indirect objects + other complements
		// when objects are longer than others, they are placed after them
		int numberOfWordDirects = NLGElement.countWords(directs.getChildren());
		int numberOfWordIndirects = NLGElement.countWords(indirects.getChildren());
		int numberOfWordUnknowns = NLGElement.countWords(unknowns.getChildren());
		// there are 3*2*1 = 6 orders possible
		if (numberOfWordDirects <= numberOfWordIndirects) {
			if (numberOfWordIndirects <= numberOfWordUnknowns) {
				// normal order
				addDirectObjects(directs, phrase, realisedElement);
				addIndirectObjects(indirects, phrase, realisedElement);
				addUnknownComplements(unknowns, phrase, realisedElement);
			} else if (numberOfWordDirects <= numberOfWordUnknowns) {
				addDirectObjects(directs, phrase, realisedElement);
				addUnknownComplements(unknowns, phrase, realisedElement);
				addIndirectObjects(indirects, phrase, realisedElement);
			} else {
				addUnknownComplements(unknowns, phrase, realisedElement);
				addDirectObjects(directs, phrase, realisedElement);
				addIndirectObjects(indirects, phrase, realisedElement);
			}
		} else {
			if (numberOfWordDirects <= numberOfWordUnknowns) {
				addIndirectObjects(indirects, phrase, realisedElement);
				addDirectObjects(directs, phrase, realisedElement);
				addUnknownComplements(unknowns, phrase, realisedElement);
			} else if (numberOfWordIndirects <= numberOfWordUnknowns) {
				addIndirectObjects(indirects, phrase, realisedElement);
				addUnknownComplements(unknowns, phrase, realisedElement);
				addDirectObjects(directs, phrase, realisedElement);
			} else {
				addUnknownComplements(unknowns, phrase, realisedElement);
				addIndirectObjects(indirects, phrase, realisedElement);
				addDirectObjects(directs, phrase, realisedElement);
			}
		}
	}

	/**
	 * Adds realised direct objects to the complements realisation
	 * @param directs			realised direct objects
	 * @param phrase			the verb phrase to wich belongs those complements
	 * @param realisedElement	complements realisation
	 */
	protected void addDirectObjects(ListElement directs, PhraseElement phrase,
			ListElement realisedElement) {
		boolean passive = phrase.getFeatureAsBoolean(Feature.PASSIVE);
		if (!passive && !InterrogativeType.isObject(phrase
					.getFeature(Feature.INTERROGATIVE_TYPE))) {
			realisedElement.addComponents(directs.getChildren());
		}
	}

	/**
	 * Adds realised indirect objects to the complements realisation
	 * @param indirects			realised indirect objects
	 * @param phrase			the verb phrase to wich belongs those complements
	 * @param realisedElement	complements realisation
	 */
	protected void addIndirectObjects(ListElement indirects, PhraseElement phrase,
			ListElement realisedElement) {
		if (!InterrogativeType.isIndirectObject(phrase
				.getFeature(Feature.INTERROGATIVE_TYPE))) {
			realisedElement.addComponents(indirects.getChildren());
		}
	}

	/**
	 * Adds unknown complements to the complements realisation
	 * @param unknowns			unknown complements
	 * @param phrase			the verb phrase to wich belongs those complements
	 * @param realisedElement	complements realisation
	 */
	protected void addUnknownComplements(ListElement unknowns, PhraseElement phrase,
			ListElement realisedElement) {
		if (//true || //WOKAROUND
				!phrase.getFeatureAsBoolean(Feature.PASSIVE)) {
			realisedElement.addComponents(unknowns.getChildren());
		}
	}

	/**
	 * Adds a default preposition to all indirect object noun phrases.
	 * Checks also inside coordinated phrases.
	 * 
	 * @param nounPhrase
	 * @return the new complement
	 * 
	 * @vaudrypl
	 */
	@SuppressWarnings("unchecked")
	protected NLGElement checkIndirectObject(NLGElement element) {
		if (element instanceof NPPhraseSpec) {
			NLGFactory factory = element.getFactory();
			NPPhraseSpec elementCopy = new NPPhraseSpec((NPPhraseSpec) element);
			PPPhraseSpec newElement = factory.createPrepositionPhrase("a", elementCopy);
			newElement.setFeature(InternalFeature.DISCOURSE_FUNCTION, DiscourseFunction.INDIRECT_OBJECT);
			element = newElement;
		} else if (element instanceof CoordinatedPhraseElement) {
			element = new CoordinatedPhraseElement( (CoordinatedPhraseElement) element );
			Object coordinates = element.getFeature(InternalFeature.COORDINATES);
			if (coordinates instanceof List) {
				List<NLGElement> list = (List<NLGElement>) coordinates;
				for (int index = 0; index < list.size(); ++index) {
					list.set(index, checkIndirectObject(list.get(index)));
				}
			}
		}
		
		return element;
	}

	/**
	 * Pushes the modal onto the stack of verb components.
	 * Sets the modal features.
	 * Based on English VerbPhraseHelper
	 * 
	 * @param actualModal
	 *            the modal to be used.
	 * @param phrase
	 *            the <code>PhraseElement</code> representing this noun phrase.
	 * @param vgComponents
	 *            the stack of verb components in the verb group.
	 */
	protected NLGElement pushModal(NLGElement frontVG, WordElement modalWord, PhraseElement phrase,
			Stack<NLGElement> vgComponents) {
		NLGElement newFront = frontVG;
		if (modalWord != null
				&& !phrase.getFeatureAsBoolean(InternalFeature.IGNORE_MODAL)
						.booleanValue()) {
			if (frontVG != null) {
				frontVG.setFeature(Feature.FORM, Form.INFINITIVE);
				//or frontVG.setFeature(Feature.NON_MORPH, true);
				vgComponents.push(frontVG);
			}
			newFront = new InflectedWordElement(modalWord);
			Object form = phrase.getFeature(Feature.FORM);
			newFront.setFeature(Feature.FORM, form);
			Object tense = phrase.getFeature(Feature.TENSE);
			newFront.setFeature(Feature.TENSE, tense);
			NumberAgreement numToUse = determineNumber(phrase.getParent(), phrase);
			newFront.setFeature(Feature.NUMBER, numToUse);
			newFront.setFeature(Feature.PERSON, phrase.getFeature(Feature.PERSON));
			newFront.setFeature(Feature.PERFECT, phrase.getFeatureAsBoolean(Feature.PERFECT));
			newFront.setFeature(Feature.PROGRESSIVE, phrase.getFeatureAsBoolean(Feature.PROGRESSIVE));
		
		}	
		//I want the modal pushed after, maybe we need to add hte aux first
		//vgComponents.push(inflectedModal);
		return newFront;
	}

	/**
	 * Realises the auxiliary verbs in the verb group.
	 * 
	 * @param realisedElement
	 *            the current realisation of the noun phrase.
	 * @param auxiliaryRealisation
	 *            the stack of auxiliary verbs.
	 */
	@Override
	protected void realiseAuxiliaries(ListElement realisedElement,
			Stack<NLGElement> auxiliaryRealisation) {

		NLGElement aux = null;
		NLGElement currentElement = null;
		while (!auxiliaryRealisation.isEmpty()) {
			aux = auxiliaryRealisation.pop();
			currentElement = aux.realiseSyntax();
			
			if (currentElement != null) {
				realisedElement.addComponent(currentElement);
				
				if (currentElement.isA(LexicalCategory.VERB)
					|| currentElement.isA(LexicalCategory.MODAL)
					|| currentElement.isA(PhraseCategory.VERB_PHRASE)) {
				currentElement.setFeature(InternalFeature.DISCOURSE_FUNCTION,
						DiscourseFunction.AUXILIARY);
				}
			}
		}
	}
}
