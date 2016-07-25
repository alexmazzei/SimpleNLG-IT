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

package simplenlg.features.italian;

/**
 * Extension of the lexical features constants for Italian.
 * 
 * @author Cristina Battaglino
 *
 */
public abstract class ItalianLexicalFeature {

	/**
	 * <p>
	 * This feature gives the noun of the opposite gender corresponding to a noun.
	 * For example, the feminine of <em>chien</em> is <em>chienne</em>.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>opposite_gender</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly form the
	 * noun of the opposite gender corresponding to a noun. This feature
	 * will be looked at first before any reference to lexicons or morphology
	 * rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Nouns.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String OPPOSITE_GENDER = "opposite_gender";
	
	/**
	 * <p>
	 * This feature is used for determining the position of adjectives. Setting
	 * this value to true means that the adjective can occupy the
	 * <em>ordinal</em> position as in "il primo libro/the first book
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>ordinal</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Any lexicon that supports adjective positioning or by the user</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processor to determine the position and ordering of adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives within noun phrases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String ORDINAL = "ordinal";
	
	/**
	 * <p>
	 * This feature is used for determining the position of adjectives. Setting
	 * this value to true means that the adjective can occupy the
	 * <em>possessive</em> position as in "il mio libro/ (the) my book"
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>possessive</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Any lexicon that supports adjective positioning or by the user</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processor to determine the position and ordering of adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives within noun phrases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String POSSESSIVE = "possessive";

	/**
	 * <p>
	 * This feature gives the feminine singular form of a determiner or adjective.
	 * For example, the feminine singular of
	 * <em>le</em> is <em>la</em> and the feminin of <em>beau</em> is
	 * <em>belle</em>.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>feminine_singular</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * determiners and adjectives. This feature will be looked at first before
	 * any reference to lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Determiners and adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String FEMININE_SINGULAR = "feminine_singular";

	/**
	 * <p>
	 * This feature gives the feminin plural form of an adjective.
	 * For example, the feminine plural of
	 *  <em>beau</em> is <em>belles</em>.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>feminine_plural</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * determiners and adjectives. This feature will be looked at first before
	 * any reference to lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String FEMININE_PLURAL = "feminine_plural";
	
	/**
	 * <p>
	 * This feature is used for incoativi verbs of the third coniugation that 
	 * change their form at the present, subjunctive and imperative form with the suffix "isc".
	 * For example the 3ps of the verb <em>colpire</em> is <em>colpisce</em>.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>incoativo</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs of the third coniugation. </td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String INCOATIVO = "incoativo";

//	/**
//	 * <p>
//	 * This feature gives the form a masculine singular adjective takes
//	 * when placed in front of a word beginning with a vowel or a so-called mute 'h'
//	 * (not a so-called aspired 'h') For example the form of <em>beau</em> in front
//	 * of a vowel is <em>bel</em>.
//	 * </p>
//	 * <table border="1">
//	 * <tr>
//	 * <td><b>Feature name</b></td>
//	 * <td><em>liaison</em></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Expected type</b></td>
//	 * <td><code>String</code></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Created by</b></td>
//	 * <td>All supporting lexicons but can be set by the user for irregular
//	 * cases.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Used by</b></td>
//	 * <td>The morphology processor uses this feature to correctly inflect
//	 * adjectives. This feature will be looked at first before
//	 * any reference to lexicons or morphology rules.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Applies to</b></td>
//	 * <td>Adjectives only.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Default</b></td>
//	 * <td><code>null</code>.</td>
//	 * </tr>
//	 * </table>
//	 */
//	public static final String LIAISON = "liaison";

//	/**
//	 * <p>
//	 * This flag determines if a word is subject to elision in front of a vowel
//	 * The elided form of <em>le</em> is <em>l'</em>.
//	 * </p>
//	 * <table border="1">
//	 * <tr>
//	 * <td><b>Feature name</b></td>
//	 * <td><em>vowel_elision</em></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Expected type</b></td>
//	 * <td><code>Boolean</code></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Created by</b></td>
//	 * <td>The information is read from Lexicons that support this feature.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Used by</b></td>
//	 * <td>The morphophonology methods.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Applies to</b></td>
//	 * <td>Many categories.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Default</b></td>
//	 * <td><code>Boolean.FALSE</code>.</td>
//	 * </tr>
//	 * </table>
//	 */
//	
//	public static final String VOWEL_ELISION = "vowel_elision"; 
	
//	/**
//	 * <p>
//	 * This flag determines if a pronoun is a detached (from the verb) form
//	 * ("forme disjointe"). For example, "moi" is a detached form, but not "me".
//	 * </p>
//	 * <table border="1">
//	 * <tr>
//	 * <td><b>Feature name</b></td>
//	 * <td><em>detached</em></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Expected type</b></td>
//	 * <td><code>Boolean</code></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Created by</b></td>
//	 * <td>The information is read from Lexicons that support this feature.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Used by</b></td>
//	 * <td>The syntax and morphology methods.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Applies to</b></td>
//	 * <td>Personal pronouns only.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Default</b></td>
//	 * <td><code>Boolean.FALSE</code>.</td>
//	 * </tr>
//	 * </table>
//	 */
//	public static final String DETACHED = "detached";
	
//	/**
//	 * <p>
//	 * This flag determines if a word begins with a so-called aspired 'h'.
//	 * </p>
//	 * <table border="1">
//	 * <tr>
//	 * <td><b>Feature name</b></td>
//	 * <td><em>aspired_h</em></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Expected type</b></td>
//	 * <td><code>Boolean</code></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Created by</b></td>
//	 * <td>The information is read from Lexicons that support this feature.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Used by</b></td>
//	 * <td>The morphophonology methods.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Applies to</b></td>
//	 * <td>Many categories.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Default</b></td>
//	 * <td><code>Boolean.FALSE</code>.</td>
//	 * </tr>
//	 * </table>
//	 */
//	public static final String ASPIRED_H = "aspired_h";
	
//	/**
//	 * <p>
//	 * This flag determines if a word provokes a negation with only the "ne" negation
//	 * adverb (no "pas" or "plus") when it is the subject or complement of a clause.
//	 * </p>
//	 * <table border="1">
//	 * <tr>
//	 * <td><b>Feature name</b></td>
//	 * <td><em>ne_only_negation</em></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Expected type</b></td>
//	 * <td><code>Boolean</code></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Created by</b></td>
//	 * <td>The information is read the lexicon.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Used by</b></td>
//	 * <td>The syntax methods.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Applies to</b></td>
//	 * <td>Many categories.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Default</b></td>
//	 * <td><code>Boolean.FALSE</code>.</td>
//	 * </tr>
//	 * </table>
//	 */
//	public static final String NE_ONLY_NEGATION = "ne_only_negation";
	
//	/**
//	 * <p>
//	 * This flag determines if a verb provokes verbal complement clitic rising
//	 * when used as a modal.
//	 * </p><p>
//	 * For example : "faire" has clitic rising ("je <bold>le</bold> fait voir") 
//	 * but "vouloir" doesn't have clitic rising ("je veux <bold>le</bold> voir")
//	 * </p>
//	 * <table border="1">
//	 * <tr>
//	 * <td><b>Feature name</b></td>
//	 * <td><em>clitic_rising</em></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Expected type</b></td>
//	 * <td><code>Boolean</code></td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Created by</b></td>
//	 * <td>The information is read from Lexicons that support this feature and
//	 * can be set by the user.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Used by</b></td>
//	 * <td>The syntax processing methods.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Applies to</b></td>
//	 * <td>Many categories.</td>
//	 * </tr>
//	 * <tr>
//	 * <td><b>Default</b></td>
//	 * <td><code>Boolean.FALSE</code>.</td>
//	 * </tr>
//	 * </table>
//	 */
//	public static final String CLITIC_RISING = "clitic_rising";
	
	/**
	 * <p>
	 * This feature is used for determining the position of adjectives. Setting
	 * this value to true means that the adjective can occupy the
	 * <em>colour</em> position as in "la rosa rossa/the rose red",
	 * "la casa gialla/the home yellow"
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>colour</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Any lexicon that supports adjective positioning or by the user</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processor to determine the position and ordering of adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives within noun phrases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String COLOUR = "colour";
	
	/**
	 * <p>
	 * These features give the conditional form of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>future(person) (number)</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static String COND1P  = "cond1p";
	public static String COND1S = "cond1s";
	public static String COND2P = "cond2p";
	public static String COND2S = "cond2s";
	public static String COND3P = "cond3p";
	public static String COND3S = "cond3s";
	
	/**
	 * <p>
	 * This flag determines if the comma must be omitted before a coordination conjunction
	 * or after a front modifier.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>no_comma</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>The information is read from Lexicons that support this feature
	 * and can be set by the user.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The orthography methods.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Conjunctions and word that are or can be front modifiers.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String NO_COMMA = "no_comma";
	
	/**
	 * <p>
	 * This flag determines if the coordination conjunction must be repeated before each
	 * coordinate.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>repeated_conjunction</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>The information is read from Lexicons that support this feature
	 * and can be set by the user.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The orthography methods.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Conjunctions.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String REPEATED_CONJUNCTION = "repeated_conjunction";
	
	/**
	 * <p>
	 * This flag determines if a verb takes "essere" as auxiliary instead of "avere".
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>auxiliary_essere</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>The information is read from Lexicons that support this feature.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processing methods.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String AUXILIARY_ESSERE = "auxiliary_essere";
	
	/**
	 * <p>
	 * This flag determines if a verb can be used as a copula.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>copular</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>The information is read from Lexicons that support this feature.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processing methods.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String COPULAR = "copular";
	
	/**
	 * <p>
	 * These features give the indicative present form of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>present (person) (number)</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String PRESENT1S = "present1s";
	public static final String PRESENT2S = "present2s";
	public static final String PRESENT3S = "present3s";
	public static final String PRESENT1P = "present1p";
	public static final String PRESENT2P = "present2p";
	public static final String PRESENT3P = "present3p";

	/**
	 * <p>
	 * These features give the imperative present form of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>imperative (person) (number)</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String IMPERATIVE2S = "imperative2s";
	public static final String IMPERATIVE1P = "imperative1p";
	public static final String IMPERATIVE2P = "imperative2p";

	/**
	 * <p>
	 * These features give the indicative simple future radical of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>future_radical</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String FUTURE_RADICAL = "future_radical";
	/**
	 * <p>
	 * These features give the indicative future form of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>future(person) (number)</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static String FUTURE1P  = "future1p";
	public static String FUTURE1S = "future1s";
	public static String FUTURE2P = "future2p";
	public static String FUTURE2S = "future2s";
	public static String FUTURE3P = "future3p";
	public static String FUTURE3S = "future3s";
	
	/**
	 * <p>
	 * This feature is used for determining the position of adjectives. Setting
	 * this value to true means that the adjective can occupy the
	 * <em>geographic</em> position as in "la pizza napoletana/the pizza naple
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>geographic</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Any lexicon that supports adjective positioning or by the user</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processor to determine the position and ordering of adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives within noun phrases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String GEOGRAPHIC = "geographic";
	
	/**
	 * <p>
	 * This feature represents a gerund used in conjunction with a verb. For
	 * example, the verb phrases <em>sto andando</em> and <em>sto mangiando</em> have
	 * the gerund form "andando" and "mangiando".
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>gerund</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons .</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String GERUND = "gerund";

	/**
	 * <p>
	 * These features give the indicative "imparfait" radical of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>imparfait_radical</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons .</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String IMPARFAIT_RADICAL = "imparfait_radical";
	/**
	 * <p>
	 * These features give the indicative imperfect form of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>impf (person) (number)</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static String IMPF1P = "impf1p";
	public static String IMPF1S = "impf1s";
	public static String IMPF2P = "impf2p";
	public static String IMPF2S = "impf2s";
	public static String IMPF3P = "impf3p";
	public static String IMPF3S = "impf3s";

	/**
	 * <p>
	 * This feature gives the feminine past participle tense form of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>femininePastParticiple</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String FEMININE_PAST_PARTICIPLE = "feminine_past_participle";
	
	/**
	 * <p>
	 * This feature determines of what type is a pronoun.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>pronoun_type</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>PronounType</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>The lexicon.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processing methods uses pronoun type to determine the appropriate
	 * form for pronouns.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Pronouns.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String PRONOUN_TYPE = "pronoun_type";
	
	/**
	 * <p>
	 * This feature is used for determining the position of adjectives. Setting
	 * this value to true means that the adjective can occupy the
	 * <em>qualitative</em> position as in "la bella ragazza/the beauty girl
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>qualitative</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Any lexicon that supports adjective positioning or by the user</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processor to determine the position and ordering of adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives within noun phrases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String QUALITATIVE = "qualitative";
	/**
	 * <p>
	 * This flag is set if a verb is reflexive. For
	 * example, "arrabbiarsi", "pentirsi". In this case, the verb is
	 * generated along with the personal pronoun. This feature is changed from the 
	 * REFLEXIVE feature used in French and in English for personal pronouns such as
	 * myself, yourself, etc.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>reflexive</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Lexicon or set by the user.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processor adds the pronouns for reflexive verbs.
	 * The morphology processor will correctly inflect reflexive pronouns.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String REFLEXIVE = "reflexive";
	/**
	 * <p>
	 * This feature is used for determining the position of adjectives. Setting
	 * this value to true means that the adjective can occupy the
	 * <em>relation</em> position as in "la situazione politica/the situation politic"
	 * "il sistema solare"/ "the system solar"
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>relation</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>Boolean</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Any lexicon that supports adjective positioning or by the user</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processor to determine the position and ordering of adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives within noun phrases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>Boolean.FALSE</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String RELATION = "relation";
	
	/**
	 * <p>
	 * These features give the remote past form of an irregular verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>remote_past (person) (number)</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String REMOTE_PAST1S = "remote_past1s";
	public static final String REMOTE_PAST2S = "remote_past2s";
	public static final String REMOTE_PAST3S = "remote_past3s";
	public static final String REMOTE_PAST1P = "remote_past1p";
	public static final String REMOTE_PAST2P = "remote_past2p";
	public static final String REMOTE_PAST3P = "remote_past3p";
	
	/**
	 * <p>
	 * These features give the subjunctive form of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>subjunctive (person) (number)</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String SUBJUNCTIVE1S = "subjunctive1s";
	public static final String SUBJUNCTIVE2S = "subjunctive2s";
	public static final String SUBJUNCTIVE3S = "subjunctive3s";
	public static final String SUBJUNCTIVE1P = "subjunctive1p";
	public static final String SUBJUNCTIVE2P = "subjunctive2p";
	public static final String SUBJUNCTIVE3P = "subjunctive3p";
	/**
	 * <p>
	 * These features give the subjunctive imperfect  form of a verb.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>subjunctive imp (person) (number)</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>All supporting lexicons but can be set by the user for irregular
	 * cases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this feature to correctly inflect
	 * verbs. This feature will be looked at first before any reference to
	 * lexicons or morphology rules.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Verbs only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String SUBJUNCTIVEIMP1S = "subjunctiveimp1s";
	public static final String SUBJUNCTIVEIMP2S = "subjunctiveimp2s";
	public static final String SUBJUNCTIVEIMP3S = "subjunctiveimp3s";
	public static final String SUBJUNCTIVEIMP1P = "subjunctiveimp1p";
	public static final String SUBJUNCTIVEIMP2P = "subjunctiveimp2p";
	public static final String SUBJUNCTIVEIMP3P = "subjunctiveimp3p";
	
	/**
	 * <p>
	 * This feature gives the feminine singular superlative form for adjectives. For
	 * example, <em>ottima</em> is the feminine singular superlative form of <em>ottimo</em>.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>superlative_feminine_singular</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Can be created automatically by the lexicon or added manually by
	 * users.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this information to correctly inflect
	 * words.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String SUPERLATIVE_FEMININE_SINGULAR = "superlative_feminine_singular";
	
	/**
	 * <p>
	 * This feature gives the feminine plural superlative form for adjectives. For
	 * example, <em>ottime</em> is the feminine plural superlative form of <em>ottimo</em>.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>superlative_feminine_plural</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Can be created automatically by the lexicon or added manually by
	 * users.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this information to correctly inflect
	 * words.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String SUPERLATIVE_FEMININE_PLURAL = "superlative_feminine_plural";
	
	/**
	 * <p>
	 * This feature gives the plural superlative form for adjectives. For
	 * example, <em>ottimi</em> is the plural superlative form of <em>ottimo</em>.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>superlative_plural</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>String</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>Can be created automatically by the lexicon or added manually by
	 * users.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor uses this information to correctly inflect
	 * words.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String SUPERLATIVE_PLURAL = "superlative_plural";

	public static final String STRONG_FORM = "strong_form";
}
