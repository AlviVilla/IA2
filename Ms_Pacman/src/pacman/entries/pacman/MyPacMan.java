package pacman.entries.pacman;


import com.fuzzylite.*;
import com.fuzzylite.defuzzifier.*;
import com.fuzzylite.factory.*;
import com.fuzzylite.hedge.*;
import com.fuzzylite.imex.*;
import com.fuzzylite.norm.*;
import com.fuzzylite.norm.s.*;
import com.fuzzylite.norm.t.*;
import com.fuzzylite.rule.*;
import com.fuzzylite.term.*;
import com.fuzzylite.variable.*;
import com.fuzzylite.Engine;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE>
{
	private MOVE myMove=MOVE.NEUTRAL;
	public MyPacMan(){
		Engine engine = new Engine();
		engine.setName("Fuzzy-PacMan");
		
		InputVariable inputVariable = new InputVariable();
		inputVariable.setEnabled(true);
		inputVariable.setName("Distance");
		inputVariable.setRange(0.000, 1.000);
		inputVariable.addTerm(new Triangle("NEAR", 0.000, 0.250, 0.500));
		inputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
		inputVariable.addTerm(new Triangle("FAR", 0.500, 0.750, 1.000));
		engine.addInputVariable(inputVariable);

		InputVariable inputVar2 = new InputVariable("PowPill-Time");
		inputVar2.setRange(0.0, pacman.game.Constants.EDIBLE_TIME);
		
		OutputVariable outputVariable = new OutputVariable();
		outputVariable.setEnabled(true);
		outputVariable.setName("Power-Pill");
		outputVariable.setRange(0.000, 1.000);
		outputVariable.fuzzyOutput().setAccumulation(new Maximum());
		outputVariable.setDefuzzifier(new Centroid(200));
		outputVariable.setDefaultValue(Double.NaN);
		outputVariable.setLockValidOutput(false);
		outputVariable.setLockOutputRange(false);
		outputVariable.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
		outputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
		outputVariable.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
		engine.addOutputVariable(outputVariable);

		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setEnabled(true);
		ruleBlock.setName("");
		ruleBlock.setConjunction(null);
		ruleBlock.setDisjunction(null);
		ruleBlock.setActivation(new Minimum());
		ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine));
		ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
		ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
		engine.addRuleBlock(ruleBlock);	
	}

	public MOVE getMove(Game game, long timeDue) 
	{
		//Place your game logic here to play the game as Ms Pac-Man
		
		return myMove;
	}
}