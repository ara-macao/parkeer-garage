package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.*;
import javax.swing.*;

public abstract class AbstractController extends JPanel {
	private static final long serialVersionUID = 4941730006940737729L;
	protected AbstractModel model;
	
	public AbstractController(AbstractModel model) {
		this.model=model;
	}
}
