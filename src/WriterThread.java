/**
 * Created by joanes on 2/28/17.
 */
public class WriterThread implements Runnable {

	private Cuenta cuenta;
	private Long loopNumber;

	public WriterThread(Cuenta cuenta, Long loopNumber) {
		this.cuenta = cuenta;
		this.loopNumber = loopNumber;
	}

	@Override
	public void run() {

		for (Long i = 0L; i < loopNumber; i++) {
			switch (cuenta.getMode()) {
				case AtomicInteger:
					cuenta.addNumberAtomic(1L);
					break;
				case Semaphore:
					cuenta.addNumberSemaphore(1L);
					break;
				case Synchronized:
					cuenta.addNumberSynchronized(1L);
					break;
				case Lock:
					cuenta.addNumberLock(1L);
					break;
				default:
					break;
			}
		}


	}
}
