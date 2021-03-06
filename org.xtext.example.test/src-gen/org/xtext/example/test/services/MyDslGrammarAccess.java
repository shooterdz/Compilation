/*
 * generated by Xtext 2.10.0
 */
package org.xtext.example.test.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.List;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.common.services.TerminalsGrammarAccess;
import org.eclipse.xtext.service.AbstractElementFinder.AbstractGrammarElementFinder;
import org.eclipse.xtext.service.GrammarProvider;

@Singleton
public class MyDslGrammarAccess extends AbstractGrammarElementFinder {
	
	public class ModelElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.xtext.example.test.MyDsl.Model");
		private final Assignment cClassesAssignment = (Assignment)rule.eContents().get(1);
		private final RuleCall cClassesClasseParserRuleCall_0 = (RuleCall)cClassesAssignment.eContents().get(0);
		
		//Model:
		//	classes+=Classe*;
		@Override public ParserRule getRule() { return rule; }
		
		//classes+=Classe*
		public Assignment getClassesAssignment() { return cClassesAssignment; }
		
		//Classe
		public RuleCall getClassesClasseParserRuleCall_0() { return cClassesClasseParserRuleCall_0; }
	}
	public class ClasseElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.xtext.example.test.MyDsl.Classe");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Keyword cClassKeyword_0 = (Keyword)cGroup.eContents().get(0);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cLeftCurlyBracketKeyword_2 = (Keyword)cGroup.eContents().get(2);
		private final Assignment cAttributsAssignment_3 = (Assignment)cGroup.eContents().get(3);
		private final RuleCall cAttributsAttributParserRuleCall_3_0 = (RuleCall)cAttributsAssignment_3.eContents().get(0);
		private final Keyword cRightCurlyBracketKeyword_4 = (Keyword)cGroup.eContents().get(4);
		
		//Classe:
		//	'Class' name=ID '{'
		//	attributs+=Attribut*
		//	'}';
		@Override public ParserRule getRule() { return rule; }
		
		//'Class' name=ID '{' attributs+=Attribut* '}'
		public Group getGroup() { return cGroup; }
		
		//'Class'
		public Keyword getClassKeyword_0() { return cClassKeyword_0; }
		
		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//'{'
		public Keyword getLeftCurlyBracketKeyword_2() { return cLeftCurlyBracketKeyword_2; }
		
		//attributs+=Attribut*
		public Assignment getAttributsAssignment_3() { return cAttributsAssignment_3; }
		
		//Attribut
		public RuleCall getAttributsAttributParserRuleCall_3_0() { return cAttributsAttributParserRuleCall_3_0; }
		
		//'}'
		public Keyword getRightCurlyBracketKeyword_4() { return cRightCurlyBracketKeyword_4; }
	}
	public class AttributElements extends AbstractParserRuleElementFinder {
		private final ParserRule rule = (ParserRule) GrammarUtil.findRuleForName(getGrammar(), "org.xtext.example.test.MyDsl.Attribut");
		private final Group cGroup = (Group)rule.eContents().get(1);
		private final Assignment cTypeAssignment_0 = (Assignment)cGroup.eContents().get(0);
		private final Alternatives cTypeAlternatives_0_0 = (Alternatives)cTypeAssignment_0.eContents().get(0);
		private final Keyword cTypeIntKeyword_0_0_0 = (Keyword)cTypeAlternatives_0_0.eContents().get(0);
		private final Keyword cTypeFloatKeyword_0_0_1 = (Keyword)cTypeAlternatives_0_0.eContents().get(1);
		private final Keyword cTypeBooleanKeyword_0_0_2 = (Keyword)cTypeAlternatives_0_0.eContents().get(2);
		private final Keyword cTypeCharKeyword_0_0_3 = (Keyword)cTypeAlternatives_0_0.eContents().get(3);
		private final Keyword cTypeStringKeyword_0_0_4 = (Keyword)cTypeAlternatives_0_0.eContents().get(4);
		private final Assignment cNameAssignment_1 = (Assignment)cGroup.eContents().get(1);
		private final RuleCall cNameIDTerminalRuleCall_1_0 = (RuleCall)cNameAssignment_1.eContents().get(0);
		private final Keyword cSemicolonKeyword_2 = (Keyword)cGroup.eContents().get(2);
		
		//Attribut:
		//	type=('int' | 'float' | 'boolean' | 'char' | 'String') name=ID ';';
		@Override public ParserRule getRule() { return rule; }
		
		//type=('int' | 'float' | 'boolean' | 'char' | 'String') name=ID ';'
		public Group getGroup() { return cGroup; }
		
		//type=('int' | 'float' | 'boolean' | 'char' | 'String')
		public Assignment getTypeAssignment_0() { return cTypeAssignment_0; }
		
		//('int' | 'float' | 'boolean' | 'char' | 'String')
		public Alternatives getTypeAlternatives_0_0() { return cTypeAlternatives_0_0; }
		
		//'int'
		public Keyword getTypeIntKeyword_0_0_0() { return cTypeIntKeyword_0_0_0; }
		
		//'float'
		public Keyword getTypeFloatKeyword_0_0_1() { return cTypeFloatKeyword_0_0_1; }
		
		//'boolean'
		public Keyword getTypeBooleanKeyword_0_0_2() { return cTypeBooleanKeyword_0_0_2; }
		
		//'char'
		public Keyword getTypeCharKeyword_0_0_3() { return cTypeCharKeyword_0_0_3; }
		
		//'String'
		public Keyword getTypeStringKeyword_0_0_4() { return cTypeStringKeyword_0_0_4; }
		
		//name=ID
		public Assignment getNameAssignment_1() { return cNameAssignment_1; }
		
		//ID
		public RuleCall getNameIDTerminalRuleCall_1_0() { return cNameIDTerminalRuleCall_1_0; }
		
		//';'
		public Keyword getSemicolonKeyword_2() { return cSemicolonKeyword_2; }
	}
	
	
	private final ModelElements pModel;
	private final ClasseElements pClasse;
	private final AttributElements pAttribut;
	
	private final Grammar grammar;
	
	private final TerminalsGrammarAccess gaTerminals;

	@Inject
	public MyDslGrammarAccess(GrammarProvider grammarProvider,
			TerminalsGrammarAccess gaTerminals) {
		this.grammar = internalFindGrammar(grammarProvider);
		this.gaTerminals = gaTerminals;
		this.pModel = new ModelElements();
		this.pClasse = new ClasseElements();
		this.pAttribut = new AttributElements();
	}
	
	protected Grammar internalFindGrammar(GrammarProvider grammarProvider) {
		Grammar grammar = grammarProvider.getGrammar(this);
		while (grammar != null) {
			if ("org.xtext.example.test.MyDsl".equals(grammar.getName())) {
				return grammar;
			}
			List<Grammar> grammars = grammar.getUsedGrammars();
			if (!grammars.isEmpty()) {
				grammar = grammars.iterator().next();
			} else {
				return null;
			}
		}
		return grammar;
	}
	
	@Override
	public Grammar getGrammar() {
		return grammar;
	}
	
	
	public TerminalsGrammarAccess getTerminalsGrammarAccess() {
		return gaTerminals;
	}

	
	//Model:
	//	classes+=Classe*;
	public ModelElements getModelAccess() {
		return pModel;
	}
	
	public ParserRule getModelRule() {
		return getModelAccess().getRule();
	}
	
	//Classe:
	//	'Class' name=ID '{'
	//	attributs+=Attribut*
	//	'}';
	public ClasseElements getClasseAccess() {
		return pClasse;
	}
	
	public ParserRule getClasseRule() {
		return getClasseAccess().getRule();
	}
	
	//Attribut:
	//	type=('int' | 'float' | 'boolean' | 'char' | 'String') name=ID ';';
	public AttributElements getAttributAccess() {
		return pAttribut;
	}
	
	public ParserRule getAttributRule() {
		return getAttributAccess().getRule();
	}
	
	//terminal ID:
	//	'^'? ('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*;
	public TerminalRule getIDRule() {
		return gaTerminals.getIDRule();
	}
	
	//terminal INT returns ecore::EInt:
	//	'0'..'9'+;
	public TerminalRule getINTRule() {
		return gaTerminals.getINTRule();
	}
	
	//terminal STRING:
	//	'"' ('\\' . | !('\\' | '"'))* '"' | "'" ('\\' . | !('\\' | "'"))* "'";
	public TerminalRule getSTRINGRule() {
		return gaTerminals.getSTRINGRule();
	}
	
	//terminal ML_COMMENT:
	//	'/ *'->'* /';
	public TerminalRule getML_COMMENTRule() {
		return gaTerminals.getML_COMMENTRule();
	}
	
	//terminal SL_COMMENT:
	//	'//' !('\n' | '\r')* ('\r'? '\n')?;
	public TerminalRule getSL_COMMENTRule() {
		return gaTerminals.getSL_COMMENTRule();
	}
	
	//terminal WS:
	//	' ' | '\t' | '\r' | '\n'+;
	public TerminalRule getWSRule() {
		return gaTerminals.getWSRule();
	}
	
	//terminal ANY_OTHER:
	//	.;
	public TerminalRule getANY_OTHERRule() {
		return gaTerminals.getANY_OTHERRule();
	}
}
