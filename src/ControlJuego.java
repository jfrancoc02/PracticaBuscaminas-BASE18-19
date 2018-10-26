import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;

/**
 * Clase gestora del tablero de juego. Guarda una matriz de enteros representado
 * el tablero. Si hay una mina en una posición guarda el número -1 Si no hay
 * una mina, se guarda cuántas minas hay alrededor. Almacena la puntuación de
 * la partida
 * 
 * @author jonathanFrancoClemente
 *
 */
public class ControlJuego {

	private final static int MINA = -1;
	final int MINAS_INICIALES = 20;
	final int LADO_TABLERO = 10;

	private int[][] tablero;
	private int puntuacion;

	public ControlJuego() {
		// Creamos el tablero:
		tablero = new int[LADO_TABLERO][LADO_TABLERO];

		// Inicializamos una nueva partida
		inicializarPartida();
	}

	/**
	 * Método para generar un nuevo tablero de partida:
	 * 
	 * @pre: La estructura tablero debe existir.
	 * @post: Al final el tablero se habrá inicializado con tantas minas como
	 *        marque la variable MINAS_INICIALES. El resto de posiciones que no son
	 *        minas guardan en el entero cuántas minas hay alrededor de la celda
	 */
	public void inicializarPartida() {
		Random rd = new Random();
		// TODO: Repartir minas e inicializar puntaci�n. Si hubiese un tablero anterior,
		// lo pongo todo a cero para inicializarlo.

		// Recorro todo el tablero y lo ponto todo a 0
		for (int i = 0; i < tablero.length; i++) { // Recorro el eje de las x
			for (int j = 0; j < tablero[i].length; j++) { // Recorro el eje de las y
				tablero[i][j] = 0; // Coloco un 0 en la posici�n
			}
		}

		// Ahora voy recorriendo el n�mero de minas y si en la posicion que nos ha
		// generado aleatoriamente no hay una mina ponemos un -1
		// Si hay una mina volvemos a ejecutar
		// Hacerlo con un while

		/*
		 * for (int i = 0; i < MINAS_INICIALES; i++) { int aleatorioX = rd.nextInt(10);
		 * int aleatorioY = rd.nextInt(10); if (tablero[aleatorioX][aleatorioY] != MINA)
		 * { tablero[aleatorioX][aleatorioY] = MINA; } else {
		 * 
		 * } }
		 */

		// Creo un entero que lo igual a MINAS_INICIALES
		// Y le digo que mientras numMinas sea mayor a 0 haga siempre esta condicion
		// Si en la posicion aleatorio no hay un -1 colocamos un -1 si no no hacemos
		// nada
		int numMinas = MINAS_INICIALES;
		while (numMinas > 0) {
			int aleatorioX = rd.nextInt(10);
			int aleatorioY = rd.nextInt(10);
			if (tablero[aleatorioX][aleatorioY] != MINA) {
				tablero[aleatorioX][aleatorioY] = MINA;
				numMinas--;
			}
		}

		// Al final del m�todo hay que guardar el n�mero de minas para las casillas que
		// no son mina:
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				if (tablero[i][j] != MINA) {
					tablero[i][j] = calculoMinasAdjuntas(i, j);
				}
			}
		}
	}

	/**
	 * Cálculo de las minas adjuntas: Para calcular el número de minas tenemos que
	 * tener en cuenta que no nos salimos nunca del tablero. Por lo tanto, como
	 * mucho la i y la j valdrán LADO_TABLERO-1. Por lo tanto, como poco la i y la
	 * j valdrán 0.
	 * 
	 * @param i:
	 *            posición vertical de la casilla a rellenar
	 * @param j:
	 *            posición horizontal de la casilla a rellenar
	 * @return : El número de minas que hay alrededor de la casilla [i][j]
	 **/
	private int calculoMinasAdjuntas(int i, int j) {
		int contador = 0;
		int iInt;
		int jInt;

		for (iInt = i - 1; iInt <= i + 1; iInt++) {
			for (jInt = j - 1; jInt <= j + 1; jInt++) {
				if (iInt >= 0 && jInt >= 0 && iInt < LADO_TABLERO && jInt < LADO_TABLERO) { // Controla las esquinas
					if (tablero[iInt][jInt] == MINA) { // Controla todo el alrededor del boton
						contador++; // Si hay una mina sumamos 1 al contador
					}
				}
			}
		}
		return contador;
	}

	/**
	 * Método que nos permite 
	 * @pre : La casilla nunca debe haber sido abierta antes, no es controlado por el ControlJuego. Por lo tanto siempre sumaremos puntos
	 * @param i: posición verticalmente de la casilla a abrir
	 * @param j: posición horizontalmente de la casilla a abrir
	 * @return : Verdadero si no ha explotado una mina. Falso en caso contrario.
	 */
	public boolean abrirCasilla(int i, int j){
		// B�sicamente lo que hace este metodo es que si la posicion no es una mina sumamos 1 a la puntuacion y devolvemos true
		// Si no, es que hemos encontrado una mina y devolvemos false
		if (tablero[i][j] != MINA) {
			puntuacion++;
			return true;
		}
		return false;
	}

	/**
	 * Método que checkea si se ha terminado el juego porque se han abierto todas
	 * las casillas.
	 * 
	 * @return Devuelve verdadero si se han abierto todas las celdas que no son
	 *         minas.
	 **/
	public boolean esFinJuego() {
		// Cada casilla abierta vale 1 punto
		if (puntuacion == 80) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Método que pinta por pantalla toda la información del tablero, se utiliza
	 * para depurar
	 */
	public void depurarTablero() {
		System.out.println("---------TABLERO--------------");
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("\nPuntuación: " + puntuacion);
	}

	/**
	 * Método que se utiliza para obtener las minas que hay alrededor de una celda
	 * 
	 * @pre : El tablero tiene que estar ya inicializado, por lo tanto no hace falta
	 *      calcularlo, símplemente consultarlo
	 * @param i
	 *            : posición vertical de la celda.
	 * @param j
	 *            : posición horizontal de la cela.
	 * @return Un entero que representa el número de minas alrededor de la celda
	 */
	public int getMinasAlrededor(int i, int j) {
		return calculoMinasAdjuntas(i, j);
	}

	/**
	 * Método que devuelve la puntuación actual
	 * 
	 * @return Un entero con la puntuación actual
	 */
	public int getPuntuacion() {
		return this.puntuacion;
	}
}
