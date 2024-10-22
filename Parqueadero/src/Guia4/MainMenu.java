package Guia4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {

    public MainMenu() {
        // Configuración de la ventana principal
        setTitle("Menú Principal");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); // Usamos un layout nulo para personalizar la ubicación de los botones

        // Crear botones
        JButton parqueaderoButton = new JButton("Parqueadero");
        JButton epsButton = new JButton("EPS Asignación de Turnos");
        JButton salirButton = new JButton("Salir");

        // Posición y tamaño de los botones
        parqueaderoButton.setBounds(50, 50, 200, 30);
        epsButton.setBounds(50, 100, 200, 30);
        salirButton.setBounds(50, 150, 200, 30);

        // Agregar acciones a los botones
        parqueaderoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la interfaz gráfica de la clase Parqueadero sin cerrar el menú
                JFrame parqueaderoFrame = new Parqueadero(); // Suponiendo que Parqueadero extiende JFrame
                parqueaderoFrame.setVisible(true); // Mostrar la ventana de Parqueadero
            }
        });

        epsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir la interfaz gráfica de la clase EPSAsignacionTurnos sin cerrar el menú
                JFrame epsFrame = new EPSAsignacionTurnos(); // Suponiendo que EPSAsignacionTurnos extiende JFrame
                epsFrame.setVisible(true); // Mostrar la ventana de EPSAsignacionTurnos
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar el programa al presionar "Salir"
                System.exit(0);
            }
        });

        // Añadir botones a la ventana
        add(parqueaderoButton);
        add(epsButton);
        add(salirButton);

        // Hacer la ventana visible
        setVisible(true);
    }

    public static void main(String[] args) {
        // Iniciar la ventana del menú
        new MainMenu();
    }
}
