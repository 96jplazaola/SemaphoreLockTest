import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joanes on 2/28/17.
 */
public class MainClass {
	final static int HOW_BIGGER = 9;
	final static int THREADS = 8;
	final static int HOW_MANY = 200;
	static long loopNumber = 100L;


	public static void main(String[] args) {

		Connection connection = ConnectionPG.connect();
		Map<Cuenta.Mode, Long> timeMap = new HashMap<>();

		for (int c = 0; c < HOW_MANY; c++) {
			loopNumber = 100L;
			for (int cnt = 0; cnt < HOW_BIGGER; cnt++) {
				loopNumber *= 4;
				for (Cuenta.Mode modo : Cuenta.Mode.values()) {
					Thread[] threads = new Thread[THREADS];
					Cuenta cuenta = new Cuenta(Cuenta.Mode.Synchronized);
					long startTime = System.currentTimeMillis();
					for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
						Thread thread = new Thread(new WriterThread(cuenta, loopNumber));
						threads[i] = thread;
						threads[i].start();
					}
					for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
						try {
							threads[i].join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					long endTime = System.currentTimeMillis();
					timeMap.put(modo, endTime - startTime);

				}
				System.out.println("NUMERO DE LOOPS: " + loopNumber);
				timeMap.forEach((k, v) -> {
					System.out.println(k + " : " + v);
					ConnectionPG.insertValue(connection, v, THREADS, HOW_BIGGER, k.toString(), loopNumber);
				});


			}


		}


	}
