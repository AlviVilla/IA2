package pacman.entries.pacman;

import java.util.ArrayList;

import com.fuzzylite.Engine;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.AlgebraicProduct;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;
import com.fuzzylite.defuzzifier.*;

import pacman.controllers.Controller;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getAction() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., game.entries.pacman.mypackage).
 */
public class MyPacMan extends Controller<MOVE> {
	private MOVE myMove = MOVE.NEUTRAL;
	Engine engine = new Engine();
	Defuzzifier defu;
	/// private Aggregated fuzzyOutput;

	public MyPacMan() {
		engine.setName("Fuzzy-PacMan");
		/*
		 * engine.configure(null, null, activation, accumulation, defuzzifier);
		 * engine.configure(“”, “”, “Minimum”, “Maximum”, “Centroid”, "General");
		 */
		InputVariable inputVariable = new InputVariable();
		inputVariable.setEnabled(true);
		inputVariable.setName("Distance");
		inputVariable.setRange(0.000, 1.000);
		inputVariable.addTerm(new Triangle("NEAR", 0.000, 0.250, 0.500));
		inputVariable.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
		inputVariable.addTerm(new Triangle("FAR", 0.500, 0.750, 1.000));
		engine.addInputVariable(inputVariable);

		InputVariable inputVar1 = new InputVariable();
		inputVar1.setEnabled(true);
		inputVar1.setName("DistPowPill");
		inputVar1.setRange(0.000, 1.000);
		inputVar1.addTerm(new Triangle("NEAR", 0.000, 0.250, 0.500));
		inputVar1.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
		inputVar1.addTerm(new Triangle("FAR", 0.500, 0.750, 1.000));
		engine.addInputVariable(inputVar1);

		InputVariable inputVar2 = new InputVariable();
		inputVar2.setEnabled(true);
		inputVar2.setName("Time");
		inputVar2.setRange(0.0, 1.0);
		inputVar2.addTerm(new Triangle("LOW", 0.000, 0.250, 0.500));
		inputVar2.addTerm(new Triangle("MEDIUM", 0.250, 0.500, 0.750));
		inputVar2.addTerm(new Triangle("HIGH", 0.500, 0.750, 1.000));
		engine.addInputVariable(inputVar2);

		OutputVariable outputVariable = new OutputVariable();
		outputVariable.setEnabled(true);
		outputVariable.setName("Action");
		outputVariable.setRange(0.000, 1.000);
		outputVariable.fuzzyOutput().setAccumulation(new Maximum());
		outputVariable.setDefuzzifier(new Centroid(200));
		outputVariable.setDefaultValue(Double.NaN);
		outputVariable.setLockValidOutput(false);
		outputVariable.setLockOutputRange(false);
		outputVariable.addTerm(new Triangle("RUN", 0.000, 0.100, 0.150));
		outputVariable.addTerm(new Triangle("EAT", 0.150, 0.500, 0.600));
		outputVariable.addTerm(new Triangle("PILL", 0.500, 0.750, 1.000));
		engine.addOutputVariable(outputVariable);

		RuleBlock ruleBlock = new RuleBlock();
		ruleBlock.setEnabled(true);
		ruleBlock.setName("");
		ruleBlock.setConjunction(new Minimum());
		ruleBlock.setDisjunction(new Maximum());
		ruleBlock.setActivation(new Minimum());
		/// algebraicProduct

		
		ruleBlock.addRule(
				Rule.parse("if Time is LOW and DistPowPill is FAR then Action is RUN", engine));

		ruleBlock.addRule(
				Rule.parse("if Distance is NEAR and Time is MEDIUM and DistPowPill is FAR then Action is EAT", engine));
		ruleBlock.addRule(Rule
				.parse("if Distance is MEDIUM and Time is MEDIUM and DistPowPill is FAR then Action is RUN", engine));
		ruleBlock.addRule(
				Rule.parse("if Distance is FAR and Time is MEDIUM and DistPowPill is FAR then Action is RUN", engine));


		ruleBlock.addRule(
				Rule.parse("if  Time is LOW and DistPowPill is MEDIUM then Action is RUN", engine));
		
		
		ruleBlock.addRule(Rule
				.parse("if Distance is NEAR and Time is MEDIUM and DistPowPill is MEDIUM then Action is EAT", engine));
		ruleBlock.addRule(Rule.parse(
				"if Distance is MEDIUM and Time is MEDIUM and DistPowPill is MEDIUM then Action is RUN", engine));
		ruleBlock.addRule(Rule
				.parse("if Distance is FAR and Time is MEDIUM and DistPowPill is MEDIUM then Action is RUN", engine));


		ruleBlock.addRule(
				Rule.parse("if Distance is NEAR and Time is LOW and DistPowPill is NEAR then Action is PILL", engine));
		ruleBlock.addRule(Rule
				.parse("if Distance is MEDIUM and Time is LOW and DistPowPill is NEAR then Action is PILL", engine));
		ruleBlock.addRule(
				Rule.parse("if Distance is FAR and Time is LOW and DistPowPill is NEAR then Action is RUN", engine));

		ruleBlock.addRule(Rule
				.parse("if Distance is NEAR and Time is MEDIUM and DistPowPill is NEAR then Action is EAT", engine));
		ruleBlock.addRule(Rule
				.parse("if Distance is MEDIUM and Time is MEDIUM and DistPowPill is NEAR then Action is RUN", engine));
		ruleBlock.addRule(Rule
				.parse("if Distance is FAR and Time is MEDIUM and DistPowPill is NEAR then Action is RUN", engine));

		ruleBlock.addRule(
				Rule.parse("if Distance is NEAR and Time is HIGH then Action is EAT", engine));
		ruleBlock.addRule(Rule
				.parse("if Distance is MEDIUM and Time is HIGH then Action is EAT", engine));
		ruleBlock.addRule(
				Rule.parse("if Distance is FAR and Time is HIGH  then Action is RUN", engine));
		
		/*
		 * ruleBlock.addRule(Rule.
		 * parse("if Distance  is NEAR and DistPowPill is NEAR then Action is PILL",
		 * engine)); ruleBlock.addRule(Rule.
		 * parse("if Distance is NEAR and DistPowPill is MEDIUM then Action is RUN",
		 * engine)); ruleBlock.addRule(Rule.
		 * parse("if Distance is NEAR and DistPowPill is FAR then Action is RUN",
		 * engine));
		 */
		/*
		 * 
		 * 
		 * ruleBlock.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is NEAR and VictimTime is LOW and DistanceToPowerPill is NEAR then action is PEAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is NEAR and VictimTime is LOW and DistanceToPowerPill is FAR then action is RUN"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is NEAR and VictimTime is LOW and DistanceToPowerPill is MEDIUM then action is RUN"
		 * , engine));
		 * 
		 * 
		 * rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is NEAR and VictimTime is MEDIUM then action is KILL"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is NEAR and VictimTime is HIGH then action is KILL"
		 * , engine));
		 * 
		 * rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is MEDIUM and VictimTime is LOW and DistanceToPowerPill is NEAR then action is PEAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is MEDIUM and VictimTime is LOW and DistanceToPowerPill is MEDIUM then action is EAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is MEDIUM and VictimTime is LOW and DistanceToPowerPill is FAR then action is EAT"
		 * , engine));
		 * 
		 * rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is MEDIUM and VictimTime is MEDIUM then action is EAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is MEDIUM and VictimTime is HIGH then action is EAT"
		 * , engine));
		 * 
		 * rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is FAR and VictimTime is LOW then action is EAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is FAR and VictimTime is MEDIUM then action is EAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is MEDIUM and DistanceToVictim  is FAR and VictimTime is HIGH then action is EAT"
		 * , engine));
		 * 
		 * 
		 * rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is NEAR and VictimTime is LOW then action is RUN"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is NEAR and VictimTime is MEDIUM then action is KILL"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is NEAR and VictimTime is HIGH then action is KILL"
		 * , engine));
		 * 
		 * rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is MEDIUM and VictimTime is LOW then action is EAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is MEDIUM and VictimTime is MEDIUM then action is EAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is MEDIUM and VictimTime is HIGH then action is KILL"
		 * , engine));
		 * 
		 * rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is FAR and VictimTime is LOW then action is EAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is FAR and VictimTime is MEDIUM then action is EAT"
		 * , engine)); rules.addRule(Rule.
		 * parse("if DistanceToEnemy  is FAR and DistanceToVictim  is FAR and VictimTime is HIGH then action is EAT"
		 * , engine));
		 */
		engine.addRuleBlock(ruleBlock);

	}

	public MOVE getMove(Game game, long timeDue) {
		int current = game.getPacmanCurrentNodeIndex();
		int closestGhostD = Integer.MAX_VALUE;
		int TimeEdible = 0;
		String strategy = new String();
		GHOST closestGhost = null;
		GHOST edible = null;
		// fantasma mas cercano
		for (GHOST ghost : GHOST.values()) {
			closestGhost = ghost;
			int dist = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
			if (dist < closestGhostD && dist >0) {
					closestGhost = ghost;
					System.out.println(closestGhost.name() + " ghost  ");
					//TimeEdible = game.getGhostEdibleTime(ghost);
					closestGhostD = dist;
			}
		}
			if (game.getGhostEdibleTime(closestGhost)!=0) {
				edible = closestGhost;
				TimeEdible = game.getGhostEdibleTime(closestGhost);
				System.out.println(closestGhostD + " close  ");
				// System.out.println("ads;kfjasdlkf;asdf;lasdkjf;lajsdf;laskjdf;lasdkjf;laksdjfa;sdf");
				// System.out.println(TimeEdible);
			}

		double PowPillD = 150.00;
		int[] powerPills = game.getPowerPillIndices();
		ArrayList<Integer> targets = new ArrayList<Integer>();

		for (int i = 0; i < powerPills.length; i++) // check with power pills are available
			if (game.isPowerPillStillAvailable(i))
				targets.add(powerPills[i]);

		int[] targetsArray = new int[targets.size()]; // convert from ArrayList to array

		for (int i = 0; i < targetsArray.length; i++)
			targetsArray[i] = targets.get(i);

		int PowPillInd = game.getClosestNodeIndexFromNodeIndex(current, targetsArray, DM.PATH);

		if (PowPillInd > 0) {
			PowPillD = (double) game.getDistance(current, PowPillInd, DM.PATH) / 100;
		} else
			PowPillD = 1.0;
		/// PowPillD =
		/// game.getNextMoveTowardsTarget(current,game.getClosestNodeIndexFromNodeIndex(current,targetsArray,DM.PATH),DM.PATH);
		System.out.println(closestGhostD);
		if (closestGhostD > 0) {

			engine.setInputValue("Distance", (float) closestGhostD / 150.00);

			/// System.out.println((float) closestGhostD / 150.00 + " DISTANCIA");
			engine.setInputValue("DistPowPill", PowPillD);
			// System.out.println(PowPillD + " PP DIST");
			// System.out.println(TimeEdible);
			if (TimeEdible > 0) {
				// System.out.println("asdasdgadlsifuasdf;okjsd;flkjasd;flkjasd;flkjasdf");
				System.out.println(TimeEdible + " TIME");
				engine.setInputValue("Time", TimeEdible / 200.00);

			} else {

				// System.out.println(TimeEdible + " NO TIME");
				engine.setInputValue("Time", 0.0);
			}
		} else {
			System.out.println("   NO  ");
			// System.out.println(closestGhostD);

			// System.out.println(closestGhost);
			engine.setInputValue("Distance", 1.00);
			engine.setInputValue("DistPowPill", 1.00);
			engine.setInputValue("Time", 0.00);

		}

		engine.process();
		current = game.getPacmanCurrentNodeIndex();
		strategy="RUN";
		if (!(engine.getOutputVariable("Action").fuzzyOutput().getTerms().isEmpty())) {
			strategy = engine.getOutputVariable("Action").fuzzyOutput().getTerms().get(0).parameters().split(" ")[3];
		}
		System.out.println("Strategy " + strategy);
		if (strategy.equals("EAT")) {
			System.out.println("comer");
			return game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(edible), DM.PATH);
		}
		else if (strategy.equals("RUN"))
			return game.getNextMoveAwayFromTarget(current,game.getGhostCurrentNodeIndex(closestGhost), DM.PATH);
		else if(game.getActivePowerPillsIndices().length>0 && strategy.equals("PILL"))
			return game.getNextMoveTowardsTarget(current,
					game.getClosestNodeIndexFromNodeIndex(current, targetsArray, DM.PATH), DM.PATH);
		else {			
			System.out.println("EL run ");	

			return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),
				game.getGhostCurrentNodeIndex(closestGhost), DM.PATH);
			}
		/*
		 * Aggregated fuzzyOutput = engine.getOutputVariable(“Action”).fuzzyOutput();
		 * for (Activated activated : fuzzyOutput.getTerms()){
		 * System.out.println(String.format("%f %s", activated.getDegree(),
		 * activated.getTerm().getName())); }
		 */

		// System.out.println(engine.getOutputVariable("Action"));

	}

}