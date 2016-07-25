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
 * Extension of the internal features constants for Italian.
 * 
 * @author Cristina Battaglino
 *
 */
public abstract class ItalianFeature {
	/**
	 * <p>
	 * This flag determines if the Adjective should be inflected into
	 * the superlative relative form. The superlative relative form
	 * takes the comparative form. For example, the superlative form for
	 * <em>buono</em> is <em>migliore</em>, as in "Maria è la migliore"
	 * vs "Maria è migliore di Marco" in the comparative form.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>is_superlative_relative</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td>A <code>WordElement</code> or <code>String</code>.
	 * <code>WordElement</code> is prefered.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td><code>The user.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The morphology processor to correctly inflect words.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Adjectives.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String IS_SUPERLATIVE_RELATIVE = "superlative_relative";
	
	/**
	 * <p>
	 * This feature represents the word used as a negation auxiliary.
	 * If this feature is not specified, the adverb "pas" will be used
	 * by default.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>negation_auxiliary</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td>A <code>WordElement</code> or <code>String</code>.
	 * <code>WordElement</code> is prefered.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td><code>The user.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax verb phrase helper.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>clauses and verb phrases.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code></td>
	 * </tr>
	 * </table>
	 */
	public static final String NEGATION_AUXILIARY = "negation_auxiliary";

	/**
	 * <p>
	 * This feature indicates the phrase that should be replaced by
	 * a relative pronoun, if any. If the discourse function of the phrase is
	 * subject, direct object or indirect object, the other phrases of the same
	 * function won't be realised.
	 * </p>
	 * <table border="1">
	 * <tr>
	 * <td><b>Feature name</b></td>
	 * <td><em>relative_phrase</em></td>
	 * </tr>
	 * <tr>
	 * <td><b>Expected type</b></td>
	 * <td><code>PhraseElement</code></td>
	 * </tr>
	 * <tr>
	 * <td><b>Created by</b></td>
	 * <td>The user must set this value.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Used by</b></td>
	 * <td>The syntax processor uses this feature to correctly structure
	 * relative clauses.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Applies to</b></td>
	 * <td>Clauses only.</td>
	 * </tr>
	 * <tr>
	 * <td><b>Default</b></td>
	 * <td><code>null</code>.</td>
	 * </tr>
	 * </table>
	 */
	public static final String RELATIVE_PHRASE = "relative_phrase";

}