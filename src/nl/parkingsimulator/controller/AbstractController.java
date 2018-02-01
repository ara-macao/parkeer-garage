package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.*;
import javax.swing.*;

/**
 * AbstractController
 *
 * @author Hanze
 * @author Jeroen Westers (Refactored to mvc)
 */
public abstract class AbstractController extends JPanel {
	protected AbstractModel model;
	
	public AbstractController(AbstractModel model) {
		this.model=model;
	}
}
