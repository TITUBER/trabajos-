import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;

public class Ventana extends JFrame {

    JTextField txtNom = new JTextField();
    JTextField txtApe = new JTextField();
    JTextField txtCed = new JTextField();
    JSpinner spHij = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
    JComboBox<String> cbTip = new JComboBox<>(new String[]{"Por hora", "Temporal", "Planta"});
    JSpinner spHor = new JSpinner(new SpinnerNumberModel(0, 0, 300, 1));
    JSpinner spAnt = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
    JSpinner spFecIni = new JSpinner(new SpinnerDateModel());
    JSpinner spFecFin = new JSpinner(new SpinnerDateModel());

    JButton btnGua = new JButton("Guardar");
    JButton btnLim = new JButton("Limpiar");
    JButton btnEli = new JButton("Eliminar");

    DefaultTableModel mod = new DefaultTableModel(
            new String[]{"Nombre", "Apellido", "Cedula", "Hijos", "Tipo", "Sueldo"}, 0) {
        public boolean isCellEditable(int f, int c) {
            return false;
        }
    };
    JTable tabla = new JTable(mod);

    // aqui se guardan los registros mientras el programa esta abierto
    Empleado vec[] = new Empleado[50];
    int can = 0;

    public Ventana() {
        setTitle("Registro de empleados");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pan = new JPanel(new GridLayout(0, 2, 5, 5));
        pan.setBorder(BorderFactory.createTitledBorder("Datos"));
        pan.add(new JLabel("Nombre"));
        pan.add(txtNom);
        pan.add(new JLabel("Apellido"));
        pan.add(txtApe);
        pan.add(new JLabel("Cedula"));
        pan.add(txtCed);
        pan.add(new JLabel("Hijos"));
        pan.add(spHij);
        pan.add(new JLabel("Tipo"));
        pan.add(cbTip);
        pan.add(new JLabel("Horas trabajadas"));
        pan.add(spHor);
        pan.add(new JLabel("Antiguedad"));
        pan.add(spAnt);
        pan.add(new JLabel("Fecha de ingreso"));
        pan.add(spFecIni);
        pan.add(new JLabel("Fecha de fin"));
        pan.add(spFecFin);

        spFecIni.setEditor(new JSpinner.DateEditor(spFecIni, "dd/MM/yyyy"));
        spFecFin.setEditor(new JSpinner.DateEditor(spFecFin, "dd/MM/yyyy"));
        spAnt.setEnabled(false);
        spFecIni.setEnabled(false);
        spFecFin.setEnabled(false);

        JPanel bot = new JPanel();
        bot.add(btnGua);
        bot.add(btnLim);
        bot.add(btnEli);

        add(pan, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(bot, BorderLayout.SOUTH);

        // cambio los campos segun el tipo que se elija
        cbTip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String t = cbTip.getSelectedItem().toString();
                spHor.setEnabled(t.equals("Por hora"));
                spAnt.setEnabled(t.equals("Planta"));
                spFecIni.setEnabled(t.equals("Temporal"));
                spFecFin.setEnabled(t.equals("Temporal"));
            }
        });

        btnGua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });

        btnLim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpiar();
            }
        });

        btnEli.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminar();
            }
        });
    }

    void guardar() {
        int r = JOptionPane.showConfirmDialog(this, "¿Desea guardar la informacion?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) {
            return;
        }

        String nom = txtNom.getText().trim();
        String ape = txtApe.getText().trim();
        String ced = txtCed.getText().trim();
        int hij = (Integer) spHij.getValue();
        String tip = cbTip.getSelectedItem().toString();

        if (nom.isEmpty() || ape.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta el nombre o el apellido");
            return;
        }
        if (!ced.matches("\\d{6,10}")) {
            JOptionPane.showMessageDialog(this, "La cedula debe tener solo numeros");
            return;
        }
        if (can >= 50) {
            JOptionPane.showMessageDialog(this, "Ya no caben mas registros");
            return;
        }

        Empleado emp;
        if (tip.equals("Por hora")) {
            emp = new EmpPorHor(nom, ape, ced, hij, (Integer) spHor.getValue());
        } else if (tip.equals("Temporal")) {
            emp = new EmpTem(nom, ape, ced, hij, leer(spFecIni), leer(spFecFin));
        } else {
            emp = new EmpPer(nom, ape, ced, hij, (Integer) spAnt.getValue());
        }

        vec[can] = emp;
        can++;

        mod.addRow(new Object[]{emp.getNom(), emp.getApe(), emp.getCed(), emp.getHij(), tip, String.format("%.2f", emp.sue())});

        limpiarCampos();
        JOptionPane.showMessageDialog(this, "Datos almacenados correctamente");
    }

    void limpiar() {
        int r = JOptionPane.showConfirmDialog(this, "¿Esta seguro de limpiar los datos?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) {
            return;
        }
        limpiarCampos();
    }

    void limpiarCampos() {
        txtNom.setText("");
        txtApe.setText("");
        txtCed.setText("");
        spHij.setValue(0);
        spHor.setValue(0);
        spAnt.setValue(0);
        spFecIni.setValue(new java.util.Date());
        spFecFin.setValue(new java.util.Date());
        cbTip.setSelectedIndex(0);
        tabla.clearSelection();
        spHor.setEnabled(true);
        spAnt.setEnabled(false);
        spFecIni.setEnabled(false);
        spFecFin.setEnabled(false);
        txtNom.requestFocus();
    }

    void eliminar() {
        int f = tabla.getSelectedRow();
        if (f < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila");
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Eliminar el registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r != JOptionPane.YES_OPTION) {
            return;
        }
        // corro los datos una posicion para atras para no dejar el hueco
        for (int i = f; i < can - 1; i++) {
            vec[i] = vec[i + 1];
        }
        vec[can - 1] = null;
        can--;
        mod.removeRow(f);
    }

    LocalDate leer(JSpinner sp) {
        java.util.Date d = (java.util.Date) sp.getValue();
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}