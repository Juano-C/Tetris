package frontend;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RecordsWindow extends JFrame {

	private JTable table;
	private JLabel iconoXcerrar;
	private JButton botonVolver;
	
	public RecordsWindow() {
		getContentPane().setBackground(new Color(0, 0, 51));

//		fabricaInterfaz.setVentana(this, "Records", 100, 100, 480, 450, new Color(0, 0, 51));
		setFont(new Font("Gill Sans Ultra Bold Condensed", Font.PLAIN, 12));
		setForeground(new Color(0, 128, 64));
		setUndecorated(true);
		initComponentes();		
	}

	private void initComponentes() {


		/* ----------- CREACION DE LA TABLA DE RECORDS ----------- */
		getContentPane().setLayout(null);
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setOpaque(false);
		table.setRowHeight(28);
		table.setRowSelectionAllowed(false);
		table.setFocusable(false);
		table.setFont(new Font("Garamond", Font.BOLD, 15));
		table.setBackground(new Color(0, 0, 51));
		table.setForeground(new Color(255, 255, 255));
		table.setBounds(20, 107, 442, 280);
		getContentPane().add(table);

		/*
		 * --------- Icono cerrar ventana ----------------
		 */
//		iconoXcerrar = fabricaInterfaz.crearBotonCerrar(440, 0, 39, 34);
		getContentPane().add(iconoXcerrar);
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setFocusable(false);
		panel.setBounds(20, 62, 442, 45);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		JLabel puestos = new JLabel("Puesto");
		puestos.setForeground(Color.WHITE);
		puestos.setFont(new Font("Garamond", Font.BOLD, 17));
		puestos.setBackground(new Color(0, 0, 51));
		panel.add(puestos);
		
		JLabel tiempos = new JLabel("Tiempo");
		tiempos.setForeground(Color.WHITE);
		tiempos.setFont(new Font("Garamond", Font.BOLD, 17));
		tiempos.setBackground(new Color(0, 0, 51));
		panel.add(tiempos);
		
		JLabel names = new JLabel("Nombre");
		names.setForeground(Color.WHITE);
		names.setFont(new Font("Garamond", Font.BOLD, 17));
		names.setBackground(new Color(0, 0, 51));
		panel.add(names);
		
		JLabel dificultad = new JLabel("Dificultad");
		dificultad.setForeground(Color.WHITE);
		dificultad.setFont(new Font("Garamond", Font.BOLD, 17));
		dificultad.setBackground(new Color(0, 0, 51));
		panel.add(dificultad);
		
		JLabel puntajes = new JLabel("Puntaje");
		puntajes.setForeground(Color.WHITE);
		puntajes.setFont(new Font("Garamond", Font.BOLD, 17));
		puntajes.setBackground(new Color(0, 0, 51));
		panel.add(puntajes);
		
		
		// --------------boton Volver
		botonVolver = new JButton("Volver");
		botonVolver.setFont(new Font("Gadugi", Font.BOLD, 15));
		botonVolver.setForeground(new Color(255, 255, 255));
		botonVolver.setFocusable(false);
		botonVolver.setBorderPainted(false);
		botonVolver.setBackground(new Color(0, 0, 51));
		botonVolver.setBounds(20, 405, 89, 29);
//		botonVolver.addMouseListener(new DisenioBoton(botonVolver));
		getContentPane().add(botonVolver);
		botonVolver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				Controlador.reproducirClick();
//				Controlador.abrirVentana(Controlador.getVentanaMain(), Controlador.getVentanaRecords());
			}
		});
		
	}

	private void actualizarTabla() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Puestp");
		model.addColumn("Tiempo");
		model.addColumn("Nombre");
		model.addColumn("Dificultad");
		model.addColumn("Puntaje");

//		Map<Integer, String> lineasTXT = ManipuladorArchivos.lineasDeText();
//		for (int i = 1; i <= 10; i++) {
//			try {
//				String tiempo = lineasTXT.get(i).split("-")[0];
//				String nombre = lineasTXT.get(i).split("-")[1];
//				String dificultad = lineasTXT.get(i).split("-")[2];
//				String puntaje = lineasTXT.get(i).split("-")[3];
//				model.addRow(new Object[] { i, tiempo, nombre, dificultad, puntaje });
//			} catch (Exception e) {
//				model.addRow(new Object[] { i, "", "", "", ""});
//			}
//		}

		table.setModel(model);
	}
	

	/**
	 * Cada ves que se muestra esta ventana actualizo la tabla de posiciones.
	 */
	@Override
	public void setVisible(boolean b) {
		actualizarTabla();
		super.setVisible(b);
	}
}