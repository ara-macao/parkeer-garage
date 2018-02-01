package nl.parkingsimulator.logic;

import java.util.*;
import nl.parkingsimulator.view.*;

/**
 * Abstract Model
 *
 * @author Hanze
 * @author Jeroen Westers (Refactored to mvc)
 */
public abstract class AbstractModel {
	private List<AbstractView> views;
	
	public AbstractModel() {
		views=new ArrayList<AbstractView>();
	}
	
	public void addView(AbstractView view) {
		views.add(view);
	}
	
	public void notifyViews() {
		for(AbstractView v: views) v.updateView();
	}
}
