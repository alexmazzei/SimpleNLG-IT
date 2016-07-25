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

package simplenlg.morphophonology.italian;

import java.util.HashMap;
import java.util.Map;

import simplenlg.features.*;
import simplenlg.features.italian.ItalianLexicalFeature;
import simplenlg.features.italian.PronounType;
import simplenlg.framework.ElementCategory;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.Language;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;
import simplenlg.framework.StringElement;
import simplenlg.framework.WordElement;
import simplenlg.morphophonology.MorphophonologyRulesInterface;
import simplenlg.phrasespec.NPPhraseSpec;

/**
 * This contains the Italian morphophonology rules.
 * 
 * References :
 * 
 *Giuseppe Patota
 *Grammatica di riferimento dell'italiano contemporaneo
 *Garzanti Linguistica 2006
 * @author Cristina B.
 */
public class MorphophonologyRules implements MorphophonologyRulesInterface {

public static final String vowels_regex =
	"a|A|ä|Ä|à|À|â|Â|e|E|ë|Ë|é|É|è|È|ê|Ê|i|I|ï|Ï|î|Î|o|O|ô|Ô|u|U|û|Û|ü|Ü|ù|Ù|y|Y|ý|Ý|ÿ|Ÿ";

	/**
	 * This method performs the morphophonology on two
	 * StringElements.
	 * 
	 */
	public void doMorphophonology(StringElement leftWord, StringElement rightWord) {
		
		ElementCategory leftCategory = leftWord.getCategory();
		ElementCategory rightCategory = rightWord.getCategory();
		NLGElement leftParent = leftWord.getParent();
		String leftRealisation = leftWord.getRealisation();
		String rightRealisation = rightWord.getRealisation();
		
		if (leftRealisation != null && rightRealisation != null) {
		
			//Preposition + determiner pg. 64 Patota
			if (LexicalCategory.PREPOSITION.equalTo(leftCategory)
					&& (LexicalCategory.DETERMINER.equalTo(rightCategory)
							|| rightWord.getFeature(ItalianLexicalFeature.PRONOUN_TYPE)
								== PronounType.RELATIVE)) {
				//System.out.println("preposizione " + leftRealisation.toString());
				//if preposition is "di" 
				if (leftRealisation.matches("(.+ |)di\\z")) {
					
					if (rightRealisation.matches("il")) {
						leftWord.setRealisation("del");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("lo")) {
						leftWord.setRealisation("dello");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("l'") || rightRealisation.matches("l")) {
						leftWord.setRealisation("dell'");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("la")) {
						leftWord.setRealisation("della");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("i")) {
						leftWord.setRealisation("dei");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("gli")) {
						leftWord.setRealisation("degli");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("le")) {
						leftWord.setRealisation("delle");
						rightWord.setRealisation(null);
					}
				}
				
				// if the preposition is "a" 
				else if (leftRealisation.matches("(.+ |)a\\z")) {
					if (rightRealisation.matches("il")) {
						leftWord.setRealisation("al");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("lo")) {
						leftWord.setRealisation("allo");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("l'") || rightRealisation.matches("l")) {
						leftWord.setRealisation("all'");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("la")) {
						leftWord.setRealisation("alla");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("i")) {
						leftWord.setRealisation("ai");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("gli")) {
						leftWord.setRealisation("agli");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("le")) {
						leftWord.setRealisation("alle");
						rightWord.setRealisation(null);
					} 
				}
				
				//if the preposition is in 
				else if (leftRealisation.matches("(.+ |)in\\z")) {
					if (rightRealisation.matches("il")) {
						leftWord.setRealisation("nel");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("lo")) {
						leftWord.setRealisation("nello");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("l'") || rightRealisation.matches("l")) {
						leftWord.setRealisation("nell'");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("la")) {
						leftWord.setRealisation("nella");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("i")) {
						leftWord.setRealisation("nei");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("gli")) {
						leftWord.setRealisation("negli");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("le")) {
						leftWord.setRealisation("nelle");
						rightWord.setRealisation(null);
					} 
				}
				
				//if the preposition is da
				else if (leftRealisation.matches("(.+ |)da\\z")) {
					if (rightRealisation.matches("il")) {
						leftWord.setRealisation("dal");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("lo")) {
						leftWord.setRealisation("dallo");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("l'") || rightRealisation.matches("l")) {
						leftWord.setRealisation("dall'");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("la")) {
						leftWord.setRealisation("dalla");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("i")) {
						leftWord.setRealisation("dai");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("gli")) {
						leftWord.setRealisation("dagli");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("le")) {
						leftWord.setRealisation("dalle");
						rightWord.setRealisation(null);
					} 
				}
			
				//if the preposition is su
				else if (leftRealisation.matches("(.+ |)su\\z")) {
					if (rightRealisation.matches("il")) {
						leftWord.setRealisation("sul");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("lo")) {
						leftWord.setRealisation("sullo");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("l'") || rightRealisation.matches("l")) {
						leftWord.setRealisation("sull'");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("la")) {
						leftWord.setRealisation("sulla");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("i")) {
						leftWord.setRealisation("sui");
						rightWord.setRealisation(null);
					} else if(rightRealisation.matches("gli")) {
						leftWord.setRealisation("sugli");
						rightWord.setRealisation(null);
					} else if (rightRealisation.matches("le")) {
						leftWord.setRealisation("sulle");
						rightWord.setRealisation(null);
					} 
				}
			}//close preposition + det
			
			//leftword determiner right word noun patota pg.57
			if(LexicalCategory.DETERMINER.equalTo(leftCategory) && 
					(LexicalCategory.NOUN.equalTo(rightCategory) || LexicalCategory.ADJECTIVE.equalTo(rightCategory))){
				//if the noun begins with s + consonant (also sc)
				if(rightRealisation.matches("\\As{1}[bcdfghlmnpqrstvz]{1}.*\\z")){
					if(leftRealisation.matches("il")){
						leftWord.setRealisation("lo");
					}
					else if(leftRealisation.matches("i")){
						leftWord.setRealisation("gli");
					}
					else if(leftRealisation.matches("un")){
						leftWord.setRealisation("uno");
					}
					else if(leftRealisation.matches("quelli")){
						leftWord.setRealisation("quegli");
					}
				}//if the noun begins with gn
				else if (rightRealisation.matches("\\Ag{1}[n]{1}.*\\z")){
					if(leftRealisation.matches("il")){
						leftWord.setRealisation("lo");
					}
					else if(leftRealisation.matches("i")){
						leftWord.setRealisation("gli");
					}
					else if(leftRealisation.matches("un")){
						leftWord.setRealisation("uno");
					}
					else if(leftRealisation.matches("quelli")){
						leftWord.setRealisation("quegli");
					}
				}//if the noun begins with z
				else if(rightRealisation.matches("\\Az{1}.*\\z")){
					if(leftRealisation.matches("il")){
						leftWord.setRealisation("lo");
					}
					else if(leftRealisation.matches("i")){
						leftWord.setRealisation("gli");
					}
					else if(leftRealisation.matches("un")){
						leftWord.setRealisation("uno");
					}
					else if(leftRealisation.matches("quelli")){
						leftWord.setRealisation("quegli");
					}
				}
				else if(rightRealisation.matches("\\Ax{1}.*\\z")){
					if(leftRealisation.matches("il")){
						leftWord.setRealisation("lo");
					}
					else if(leftRealisation.matches("i")){
						leftWord.setRealisation("gli");
					}
					else if(leftRealisation.matches("un")){
						leftWord.setRealisation("uno");
					}
					else if(leftRealisation.matches("quelli")){
						leftWord.setRealisation("quegli");
					}
				}
				else if(rightRealisation.matches("\\Ap{1}[n]{1}.*\\z") || rightRealisation.matches("\\Ap{1}[s]{1}.*\\z")){
					if(leftRealisation.matches("il")){
						leftWord.setRealisation("lo");
					}
					else if(leftRealisation.matches("i")){
						leftWord.setRealisation("gli");
					}
					else if(leftRealisation.matches("un")){
						leftWord.setRealisation("uno");
					}
					else if(leftRealisation.matches("quelli")){
						leftWord.setRealisation("quegli");
					}
				}
				else if(beginsWithVowel(rightWord)){
					if(leftRealisation.matches("il") || leftRealisation.matches("la")){
						leftWord.setRealisation("l'");
					}
					else if(leftRealisation.matches("una")){
						leftWord.setRealisation("un'");
					}
					else if (leftRealisation.matches("quello") || leftRealisation.matches("quella")){
						leftWord.setRealisation("quell'");
					}
					else if (leftRealisation.matches("questo") || leftRealisation.matches("questa")){
						leftWord.setRealisation("quest'");
					}
					else if (leftRealisation.matches("quelli")) {
						leftWord.setRealisation("quegli");
					}
					else if (leftRealisation.matches("i")) {
						leftWord.setRealisation("gli");
					}
				}
				//pg 187 quei and quel
				else {
					if (leftRealisation.matches("quelli")) {
						leftWord.setRealisation("quei");
					} else if (leftRealisation.matches("quello")){
						leftWord.setRealisation("quel");
					}
				}
			}//close det + NOUN
			
			//vowel elision for preposition "di"and "da" before nouns starting with a vowel
			if(LexicalCategory.PREPOSITION.equalTo(leftCategory) && 
					LexicalCategory.NOUN.equalTo(rightCategory) && beginsWithVowel(rightWord)){
				if(leftRealisation.matches("di") || leftRealisation.matches("da")){
					leftWord.setRealisation("d'");
				}
			}
			
			//vowel elision for preposition "di"and "da" before nouns starting with a vowel
			if(LexicalCategory.DETERMINER.equalTo(leftCategory) && 
					LexicalCategory.NOUN.equalTo(rightCategory) && beginsWithVowel(rightWord)){
				if(leftRealisation.matches("di")){
					leftWord.setRealisation("d'");
				}
				if(leftRealisation.matches("dello") || leftRealisation.matches("della")){
					leftWord.setRealisation("dell'");
				}
				if(leftRealisation.matches("dei") && beginsWithVowel(rightWord)){
					leftWord.setRealisation("degli");
				}
			}
			
			//other form for adjs "buono", "bello", "santo", "grande" etc. pg 76 Patota
			//masculine plural form bei, begli
			//vowel elision bell'amico, bell'amica
			if (leftParent != null) {
				if(LexicalCategory.ADJECTIVE.equalTo(leftCategory) && 
				LexicalCategory.NOUN.equalTo(rightCategory)){
					// Get gender from parent or "grand-parent" for adjectives
					boolean masculine = false;
					boolean plural = false;
					if (!leftParent.hasFeature(LexicalFeature.GENDER) && leftParent.getParent() != null) {
						leftParent = leftParent.getParent();
						masculine = Gender.MASCULINE.equals( leftParent.getParent().getFeature(LexicalFeature.GENDER) );
						plural = NumberAgreement.PLURAL.equals(leftParent.getParent().getFeature(LexicalFeature.PLURAL));
					}
					else{
						masculine = Gender.MASCULINE.equals( leftParent.getFeature(LexicalFeature.GENDER) );
						plural = NumberAgreement.PLURAL.equals(leftParent.getFeature(Feature.NUMBER));
					}
					if(masculine){
						if(!beginsWithVowel(rightWord)){
							if(plural){
								if(leftRealisation.matches("belli")){
									//z, 
									if(rightRealisation.matches("\\Az{1}.*\\z") || 
									// s + consonant
									rightRealisation.matches("\\As{1}[bcdfghlmnpqrstvz]{1}.*\\z") ||
									//ng
									rightRealisation.matches("\\Ag{1}[n]{1}.*\\z") ||	
									// pn
									rightRealisation.matches("\\Ap{1}[n]{1}.*\\z") ||
									// and ps
									rightRealisation.matches("\\Ap{1}[s]{1}.*\\z")){
										leftWord.setRealisation("begli");
									}
									else{
										leftWord.setRealisation("bei");
									}
								}
							}
							//singular adj form
							else{
								if(leftRealisation.matches("bello")){
									leftWord.setRealisation("bel");
								}
								else if (leftRealisation.matches("grande")){
									System.out.println("grande --> gran");
									leftWord.setRealisation("gran");
								}
								else if (leftRealisation.matches("buono")){
									leftWord.setRealisation("buon");
								}
								else if (leftRealisation.matches("santo")){
									leftWord.setRealisation("san");
								}
							}
						}
						else if(beginsWithVowel(rightWord)){
							if(leftRealisation.matches("bello")){
								leftWord.setRealisation("bell'");
							}
							if(leftRealisation.matches("santo")){
								leftWord.setRealisation("san'");
							}
							if(leftRealisation.matches("belli")){
								leftWord.setRealisation("bei");
							}
						}
					} //close if masculine
				}//close if adj + noun
			}//close if leftParent
			
			
			// remove duplicate "di" or "che"
			if ("di".equals(leftRealisation) &&
						("di".equals(rightRealisation) 
							|| "d'".equals(rightRealisation))
					|| "che".equals(leftRealisation) &&
						("che".equals(rightRealisation)) ) {
				leftWord.setRealisation(null);
			}
			
			if( "e".equals(leftRealisation) && beginsWithVowel(rightWord)){
				leftWord.setRealisation("ed");
			}
			
		}
	}
	/**
	 * Tells if a word begins with a vowel  or an h
	 * 
	 * @param word
	 * @return true if the words begins with a vowel or an h
	 */
	public boolean beginsWithVowel(StringElement word)
	{
		String realisation = word.getRealisation();
		return ( realisation.matches("\\A(" + vowels_regex + "|h|H).*"));
	}
}