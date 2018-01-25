package nl.parkingsimulator.controller;

import nl.parkingsimulator.logic.AbstractModel;
import nl.parkingsimulator.logic.CarParkModel;
import nl.parkingsimulator.logic.Reservation;
import nl.parkingsimulator.logic.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReservationsController extends AbstractController implements ActionListener{

    private JLabel reservationLabel;
    private JButton addReservationButton;
    private JButton removeReservationButton;
    private JTextField reservationAmount;
    private ArrayList<Reservation> reservations;

    CarParkModel parkModel;


    public ReservationsController(AbstractModel model, Dimension size) {
        super(model);

        parkModel = (CarParkModel) model;

        setSize(size);

        reservations = new ArrayList<>();

        addReservation();

        reservationLabel = new JLabel("Reserveringen");
        addReservationButton = new JButton("Voeg toe");
        removeReservationButton = new JButton("Verwijder");
        reservationAmount = new JTextField();

        setLayout(null);
        add(reservationLabel);
        add(addReservationButton);
        add(removeReservationButton);
        add(reservationAmount);

        reservationAmount.setEditable(false);
        addReservationButton.addActionListener(this);
        removeReservationButton.addActionListener(this);

        reservationLabel.setBounds(8, 8, 120, 30);
        addReservationButton.setBounds(8, 46, 100, 24);
        removeReservationButton.setBounds(224, 46, 100, 24);
        reservationAmount.setBounds(116, 46, 100, 24);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addReservationButton) {
            Reservation reservation = new Reservation(reservations.size());
            reservations.add(reservation);
            if(parkModel != null) {
                parkModel.setReservationAt(0,0,0, reservation);
            }
        }
        else if(e.getSource() == removeReservationButton) {
            if(reservations.size() > 0) {
                reservations.remove(reservations.size() - 1);
            }
        }
    }

    private void addReservation() {
        Reservation reservation = new Reservation(0);
        reservations.add(reservation);
        if(parkModel != null) {
            for (int i = 0; i < 30; i++) {
                for (int y = 0; y < 6; y++) {
                    parkModel.setReservationAt(0, y, i, reservation);
                }
            }
        }
    }
}
