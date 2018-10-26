import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;
	JPanel panelImagen;
	JPanel panelEmpezar;
	JPanel panelPuntuacion;
	JPanel panelJuego;

	// Todos los botones se meten en un panel independiente.
	// Hacemos esto para que podamos cambiar después los componentes por otros
	JPanel[][] panelesJuego;
	JButton[][] botonesJuego;

	// Correspondencia de colores para las minas:
	Color correspondenciaColores[] = { Color.BLACK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.RED, Color.RED,
			Color.RED, Color.RED, Color.RED, Color.RED };

	JButton botonEmpezar;
	JTextField pantallaPuntuacion;

	int i = 0;
	int j = 0;

	// LA VENTANA GUARDA UN CONTROL DE JUEGO:
	ControlJuego juego;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 100, 700, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		juego = new ControlJuego();
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// Inicializamos componentes
		panelImagen = new JPanel();
		panelEmpezar = new JPanel();
		panelEmpezar.setLayout(new GridLayout(1, 1));
		panelPuntuacion = new JPanel();
		panelPuntuacion.setLayout(new GridLayout(1, 1));
		panelJuego = new JPanel();
		panelJuego.setLayout(new GridLayout(10, 10));

		botonEmpezar = new JButton("Go!");
		pantallaPuntuacion = new JTextField("0");
		pantallaPuntuacion.setEditable(false);
		pantallaPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);

		// Bordes y colores:
		panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelEmpezar.setBorder(BorderFactory.createTitledBorder("Empezar"));
		panelPuntuacion.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		panelJuego.setBorder(BorderFactory.createTitledBorder("Juego"));

		// Colocamos los componentes:
		// AZUL
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelImagen, settings);
		// VERDE
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelEmpezar, settings);
		// AMARILLO
		settings = new GridBagConstraints();
		settings.gridx = 2;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelPuntuacion, settings);
		// ROJO
		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.weightx = 1;
		settings.weighty = 10;
		settings.gridwidth = 3;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelJuego, settings);

		// Meto los botones dentro de un metodo para que a la hora de volver a generar un nuevo buscaminas no tenga que a�adir toooodo el codigo
		/*
		 * // Paneles panelesJuego = new JPanel[10][10]; for (int i = 0; i <
		 * panelesJuego.length; i++) { for (int j = 0; j < panelesJuego[i].length; j++)
		 * { panelesJuego[i][j] = new JPanel(); panelesJuego[i][j].setLayout(new
		 * GridLayout(1, 1)); panelJuego.add(panelesJuego[i][j]); } }
		 * 
		 * // Botones botonesJuego = new JButton[10][10]; for (int i = 0; i <
		 * botonesJuego.length; i++) { for (int j = 0; j < botonesJuego[i].length; j++)
		 * { botonesJuego[i][j] = new JButton("-");
		 * panelesJuego[i][j].add(botonesJuego[i][j]); } }
		 * 
		 * // BotónEmpezar: panelEmpezar.add(botonEmpezar);
		 * panelPuntuacion.add(pantallaPuntuacion);
		 */

		generarBotones();
	}

	public void generarBotones() {
		// Paneles
		panelesJuego = new JPanel[10][10];
		for (int i = 0; i < panelesJuego.length; i++) {
			for (int j = 0; j < panelesJuego[i].length; j++) {
				panelesJuego[i][j] = new JPanel();
				panelesJuego[i][j].setLayout(new GridLayout(1, 1));
				panelJuego.add(panelesJuego[i][j]);
			}
		}

		// Botones
		botonesJuego = new JButton[10][10];
		for (int i = 0; i < botonesJuego.length; i++) {
			for (int j = 0; j < botonesJuego[i].length; j++) {
				botonesJuego[i][j] = new JButton("-");
				panelesJuego[i][j].add(botonesJuego[i][j]);
			}
		}

		// BotónEmpezar:
		panelEmpezar.add(botonEmpezar);
		panelPuntuacion.add(pantallaPuntuacion);

	}

	/**
	 * Método que inicializa todos los lísteners que necesita inicialmente el
	 * programa
	 */
	public void inicializarListeners() {
		for (i = 0; i < botonesJuego.length; i++) {
			int iInt = i;
			for (j = 0; j < botonesJuego[i].length; j++) {
				int jInt = j;
				botonesJuego[i][j].addActionListener(new ActionBoton(this, iInt, jInt));
			}
		}
		
		botonEmpezar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pantallaPuntuacion.setText("0");
				// Me cargo todo el panel y lo inicio todo de nuevo
				panelJuego.removeAll();
				// Genero los botones
				generarBotones();
				refrescarPantalla();
				// Vuelvo a generar las minas y los 0
				juego.inicializarPartida();
				// Inicializo los listeners
				inicializarListeners();
			}
		});
	}

	/**
	 * Pinta en la pantalla el número de minas que hay alrededor de la celda Saca
	 * el botón que haya en la celda determinada y añade un JLabel centrado y no
	 * editable con el número de minas alrededor. Se pinta el color del texto
	 * según la siguiente correspondecia (consultar la variable
	 * correspondeciaColor): - 0 : negro - 1 : cyan - 2 : verde - 3 : naranja - 4 ó
	 * más : rojo
	 * 
	 * @param i:
	 *            posición vertical de la celda.
	 * @param j:
	 *            posición horizontal de la celda.
	 */
	public void mostrarNumMinasAlrededor(int i, int j) {
		// Label que almacenar� el n�mero de bombas que tenemos alrededor de una casilla
		// en concreto
		JLabel texto;
		// Si cuando abrimos la casilla no es una bomba
		if (juego.abrirCasilla(i, j) == true) {
			// Eliminamos el boton
			panelesJuego[i][j].removeAll();
			// A�adimos al texto el n�mero
			texto = new JLabel(juego.getMinasAlrededor(i, j) + "");
			// Lo alineamos al centro
			texto.setHorizontalAlignment(SwingConstants.CENTER);
			// Al ser un JLabel ya de por si no puede ser editable
			// Le asignamos al texto un color dependiendo del numero de minas que tengamos
			// en esa posicion
			// Este numero ya hace referencia a la posicion de correspondenciaColores
			texto.setForeground(correspondenciaColores[juego.getMinasAlrededor(i, j)]);
			panelesJuego[i][j].add(texto);
			refrescarPantalla();
		} else {
			mostrarFinJuego(true);
		}

	}

	/**
	 * Muestra una ventana que indica el fin del juego
	 * 
	 * @param porExplosion
	 *            : Un booleano que indica si es final del juego porque ha explotado
	 *            una mina (true) o bien porque hemos desactivado todas (false)
	 * @post : Todos los botones se desactivan excepto el de volver a iniciar el
	 *       juego.
	 */
	public void mostrarFinJuego(boolean porExplosion) {
		String mensajeAMostrar;
		if (porExplosion == true) {
			mensajeAMostrar = "BOMBA!!. Has perdido.\n�Desea volver a jugar?";
		} else {
			mensajeAMostrar = "Has GANADO. Enhorabuena.!!\n�Desea volver a jugar?";
		}
		int opcion = JOptionPane.showConfirmDialog(ventana, mensajeAMostrar, "Gracias por utilizar el programa!",
				JOptionPane.YES_NO_OPTION);
		// Si el usuario pulsa "SI" se vuelve a generar un nuevo buscaminas
		if (opcion == 0) {
			// Me cargo todo el panel y lo inicio todo de nuevo
			panelJuego.removeAll();
			// Genero los botones
			generarBotones();
			refrescarPantalla();
			// Vuelvo a generar las minas y los 0
			juego.inicializarPartida();
			// Inicializo los listeners
			inicializarListeners();
		}
		// Si el usuario pulsa "NO" se cierra el programa
		if (opcion == 1) {
			System.exit(0);
		}
	}

	/**
	 * Método que muestra la puntuación por pantalla.
	 */
	public void actualizarPuntuacion() {
		pantallaPuntuacion.setText(juego.getPuntuacion() + "");
		if (juego.getPuntuacion() == 80) {
			mostrarFinJuego(false);
		}
	}

	/**
	 * Método para refrescar la pantalla
	 */
	public void refrescarPantalla() {
		ventana.revalidate();
		ventana.repaint();
	}

	/**
	 * Método que devuelve el control del juego de una ventana
	 * 
	 * @return un ControlJuego con el control del juego de la ventana
	 */
	public ControlJuego getJuego() {
		return juego;
	}

	/**
	 * Método para inicializar el programa
	 */
	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		// juego.depurarTablero();
		inicializarListeners();
	}
}
