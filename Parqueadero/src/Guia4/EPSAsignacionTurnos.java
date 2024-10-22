/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Guia4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Comparator;

// Clase Paciente que almacena la información de cada paciente
class Paciente {
    String nombre;
    int edad;
    boolean planComplementario;
    boolean embarazada;
    boolean limitacionMotriz;
    int turno;

    public Paciente(String nombre, int edad, boolean planComplementario, boolean embarazada, boolean limitacionMotriz, int turno) {
        this.nombre = nombre;
        this.edad = edad;
        this.planComplementario = planComplementario;
        this.embarazada = embarazada;
        this.limitacionMotriz = limitacionMotriz;
        this.turno = turno;
    }

    public String toString() {
        return "Turno #" + turno + ": " + nombre;
    }
}

// Comparador para manejar la prioridad de los pacientes
class PacienteComparator implements Comparator<Paciente> {
    @Override
    public int compare(Paciente p1, Paciente p2) {
        if (p1.edad >= 60 || p1.edad <= 12 || p1.embarazada || p1.limitacionMotriz || p1.planComplementario) {
            return -1;
        } else if (p2.edad >= 60 || p2.edad <= 12 || p2.embarazada || p2.limitacionMotriz || p2.planComplementario) {
            return 1;
        }
        return p1.turno - p2.turno;
    }
}

// Clase principal
public class EPSAsignacionTurnos extends JFrame {
    private PriorityQueue<Paciente> colaTurnos;
    private int numeroTurno = 1;
    private JLabel turnoActualLabel;
    private JTextArea turnosPendientesArea;
    private Timer timer;
    private int tiempoRestante = 5;

    public EPSAsignacionTurnos() {
        colaTurnos = new PriorityQueue<>(new PacienteComparator());
        setTitle("Sistema de Asignación de Turnos - EPS");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para mostrar turno actual y tiempo restante
        turnoActualLabel = new JLabel("Turno actual: Ninguno");
        JLabel tiempoLabel = new JLabel("Tiempo restante: " + tiempoRestante + " segundos");
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(2, 1));
        panelSuperior.add(turnoActualLabel);
        panelSuperior.add(tiempoLabel);
        add(panelSuperior, BorderLayout.NORTH);

        // Área de texto para mostrar turnos pendientes
        turnosPendientesArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(turnosPendientesArea);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para agregar nuevos pacientes
        JButton agregarPacienteBtn = new JButton("Agregar Paciente");
        agregarPacienteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarPaciente();
            }
        });

        // Botón para extender el tiempo del paciente actual
        JButton extenderTiempoBtn = new JButton("Extender tiempo del paciente actual");
        extenderTiempoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tiempoRestante += 5;
                tiempoLabel.setText("Tiempo restante: " + tiempoRestante + " segundos");
            }
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(agregarPacienteBtn);
        panelInferior.add(extenderTiempoBtn);
        add(panelInferior, BorderLayout.SOUTH);

        // Timer para controlar los turnos
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (tiempoRestante > 0) {
                    tiempoRestante--;
                } else {
                    if (!colaTurnos.isEmpty()) {
                        Paciente paciente = colaTurnos.poll();
                        turnoActualLabel.setText("Turno actual: " + paciente.toString());
                        tiempoRestante = 5;
                        actualizarTurnosPendientes();
                    }
                }
                tiempoLabel.setText("Tiempo restante: " + tiempoRestante + " segundos");
            }
        }, 0, 1000); // Actualización cada segundo
    }

    // Método para agregar un nuevo paciente
    private void agregarPaciente() {
        JTextField nombreField = new JTextField();
        JTextField edadField = new JTextField();
        JCheckBox planComplementarioCheck = new JCheckBox("Plan Complementario");
        JCheckBox embarazoCheck = new JCheckBox("Embarazada");
        JCheckBox limitacionMotrizCheck = new JCheckBox("Limitación Motriz");

        Object[] message = {
                "Nombre:", nombreField,
                "Edad:", edadField,
                planComplementarioCheck,
                embarazoCheck,
                limitacionMotrizCheck,
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Agregar nuevo paciente", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            int edad = Integer.parseInt(edadField.getText());
            boolean planComplementario = planComplementarioCheck.isSelected();
            boolean embarazada = embarazoCheck.isSelected();
            boolean limitacionMotriz = limitacionMotrizCheck.isSelected();

            Paciente nuevoPaciente = new Paciente(nombre, edad, planComplementario, embarazada, limitacionMotriz, numeroTurno++);
            colaTurnos.add(nuevoPaciente);
            actualizarTurnosPendientes();
        }
    }

    // Método para actualizar el área de turnos pendientes
    private void actualizarTurnosPendientes() {
        StringBuilder turnosTexto = new StringBuilder();
        for (Paciente paciente : colaTurnos) {
            turnosTexto.append(paciente.toString()).append("\n");
        }
        turnosPendientesArea.setText(turnosTexto.toString());
    }

    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EPSAsignacionTurnos().setVisible(true);
            }
        });
    }
}
