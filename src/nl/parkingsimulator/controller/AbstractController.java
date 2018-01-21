package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.*;
import javax.swing.*;

public abstract class AbstractController extends JPanel {
	protected AbstractModel model;
	
	public AbstractController(AbstractModel model) {
		this.model=model;
	}
}
