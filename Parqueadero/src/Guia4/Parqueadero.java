/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Guia4;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Clase Vehiculo que almacena los detalles del vehículo
class Vehiculo {
    String placa;
    String tipo;
    LocalTime horaIngreso;
    int numeroVehiculo;

    public Vehiculo(String placa, String tipo, LocalTime horaIngreso, int numeroVehiculo) {
        this.placa = placa;
        this.tipo = tipo;
        this.horaIngreso = horaIngreso;
        this.numeroVehiculo = numeroVehiculo;
    }

    public long calcularCosto() {
        LocalTime horaActual = LocalTime.now();
        long minutos = java.time.Duration.between(horaIngreso, horaActual).toMinutes();
        switch (tipo) {
            case "Bicicleta":
            case "Ciclomotor":
                return minutos * 20;
            case "Motocicleta":
                return minutos * 30;
            case "Carro":
                return minutos * 60;
            default:
                return 0;
        }
    }

    public String toString() {
        return "Placa: " + placa + ", Tipo: " + tipo + ", Hora Ingreso: " + horaIngreso;
    }
}

// Clase principal
public class Parqueadero extends JFrame {
    private List<Vehiculo> listaVehiculos;
    private Stack<Vehiculo> vehiculos2Ruedas;
    private Stack<Vehiculo> vehiculos4Ruedas;
    private int numeroVehiculo = 1;
    private JTable tablaVehiculos;
    private DefaultTableModel modeloTabla;

    public Parqueadero() {
        listaVehiculos = new ArrayList<>();
        vehiculos2Ruedas = new Stack<>();
        vehiculos4Ruedas = new Stack<>();

        setTitle("Sistema de Parqueadero Público");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menú principal
        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(7, 1));

        JButton ingresarVehiculoBtn = new JButton("1. Ingreso de Vehículo");
        JButton visualizarTablaBtn = new JButton("2. Visualizar Tabla");
        JButton verVehiculos2RuedasBtn = new JButton("3. Visualizar Vehículos de 2 Ruedas");
        JButton verVehiculos4RuedasBtn = new JButton("4. Visualizar Vehículos de 4 Ruedas");
        JButton cantidadVehiculosBtn = new JButton("5. Cantidad de Vehículos y Total a Pagar");
        JButton eliminarVehiculoBtn = new JButton("6. Eliminar Vehículo");
        JButton salirBtn = new JButton("7. Salir");

        panelMenu.add(ingresarVehiculoBtn);
        panelMenu.add(visualizarTablaBtn);
        panelMenu.add(verVehiculos2RuedasBtn);
        panelMenu.add(verVehiculos4RuedasBtn);
        panelMenu.add(cantidadVehiculosBtn);
        panelMenu.add(eliminarVehiculoBtn);
        panelMenu.add(salirBtn);

        add(panelMenu, BorderLayout.WEST);

        // Tabla para mostrar los vehículos
        String[] columnas = {"Número Vehículo", "Placa", "Tipo", "Hora Ingreso", "Valor a Pagar"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaVehiculos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaVehiculos);
        add(scrollPane, BorderLayout.CENTER);

        // Acciones para cada botón del menú
        ingresarVehiculoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarVehiculo();
            }
        });

        visualizarTablaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTabla();
            }
        });

        verVehiculos2RuedasBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVehiculos2Ruedas();
            }
        });

        verVehiculos4RuedasBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVehiculos4Ruedas();
            }
        });

        cantidadVehiculosBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCantidadVehiculos();
            }
        });

        eliminarVehiculoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarVehiculo();
            }
        });

        salirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // Método para ingresar un vehículo
    private void ingresarVehiculo() {
        JTextField placaField = new JTextField();
        String[] tipos = {"Bicicleta", "Ciclomotor", "Motocicleta", "Carro"};
        JComboBox<String> tipoCombo = new JComboBox<>(tipos);

        Object[] message = {
                "Placa:", placaField,
                "Tipo de Vehículo:", tipoCombo
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Ingreso de Vehículo", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String placa = placaField.getText();
            String tipo = (String) tipoCombo.getSelectedItem();
            LocalTime horaIngreso = LocalTime.now();

            Vehiculo vehiculo = new Vehiculo(placa, tipo, horaIngreso, numeroVehiculo++);
            listaVehiculos.add(vehiculo);

            // Agregar a la pila correspondiente
            if (tipo.equals("Bicicleta") || tipo.equals("Ciclomotor") || tipo.equals("Motocicleta")) {
                vehiculos2Ruedas.push(vehiculo);
            } else if (tipo.equals("Carro")) {
                vehiculos4Ruedas.push(vehiculo);
            }

            actualizarTabla();
        }
    }

    // Método para actualizar la tabla de vehículos
    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // Limpiar la tabla
        for (Vehiculo vehiculo : listaVehiculos) {
            Object[] fila = {vehiculo.numeroVehiculo, vehiculo.placa, vehiculo.tipo, vehiculo.horaIngreso, vehiculo.calcularCosto()};
            modeloTabla.addRow(fila);
        }
    }

    // Mostrar vehículos de 2 ruedas
    private void mostrarVehiculos2Ruedas() {
        StringBuilder lista = new StringBuilder();
        long totalPagar = 0;

        for (Vehiculo vehiculo : vehiculos2Ruedas) {
            lista.append(vehiculo.toString()).append("\n");
            totalPagar += vehiculo.calcularCosto();
        }

        JOptionPane.showMessageDialog(null, "Vehículos de 2 Ruedas:\n" + lista.toString() + "\nTotal a Pagar: $" + totalPagar + " COP");
    }

    // Mostrar vehículos de 4 ruedas
    private void mostrarVehiculos4Ruedas() {
        StringBuilder lista = new StringBuilder();
        long totalPagar = 0;

        for (Vehiculo vehiculo : vehiculos4Ruedas) {
            lista.append(vehiculo.toString()).append("\n");
            totalPagar += vehiculo.calcularCosto();
        }

        JOptionPane.showMessageDialog(null, "Vehículos de 4 Ruedas:\n" + lista.toString() + "\nTotal a Pagar: $" + totalPagar + " COP");
    }

    // Mostrar la cantidad de vehículos y el valor total a pagar
    private void mostrarCantidadVehiculos() {
        long totalPagar = 0;
        for (Vehiculo vehiculo : listaVehiculos) {
            totalPagar += vehiculo.calcularCosto();
        }

        JOptionPane.showMessageDialog(null, "Cantidad de Vehículos: " + listaVehiculos.size() + "\nValor Total a Pagar: $" + totalPagar + " COP");
    }

    // Eliminar un vehículo por número
    private void eliminarVehiculo() {
        String numeroVehiculoStr = JOptionPane.showInputDialog("Ingrese el número del vehículo a eliminar:");
        if (numeroVehiculoStr != null && !numeroVehiculoStr.isEmpty()) {
            int numero = Integer.parseInt(numeroVehiculoStr);
            Vehiculo vehiculoAEliminar = null;

            // Buscar el vehículo por número
            for (Vehiculo vehiculo : listaVehiculos) {
                if (vehiculo.numeroVehiculo == numero) {
                    vehiculoAEliminar = vehiculo;
                    break;
                }
            }

            if (vehiculoAEliminar != null) {
                listaVehiculos.remove(vehiculoAEliminar);
                // Actualizar las pilas
                if (vehiculoAEliminar.tipo.equals("Bicicleta") || vehiculoAEliminar.tipo.equals("Ciclomotor") || vehiculoAEliminar.tipo.equals("Motocicleta")) {
                    vehiculos2Ruedas.remove(vehiculoAEliminar);
                } else if (vehiculoAEliminar.tipo.equals("Carro")) {
                    vehiculos4Ruedas.remove(vehiculoAEliminar);
                }
                actualizarTabla();
                JOptionPane.showMessageDialog(null, "Vehículo eliminado.");
            } else {
                JOptionPane.showMessageDialog(null, "Vehículo no encontrado.");
            }
        }
    }

    // Método principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Parqueadero().setVisible(true);
            }
        });
    }
}
