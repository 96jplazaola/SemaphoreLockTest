import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by joanes on 2/28/17.
 */
public class Cuenta {
	private Long saldo;
	private AtomicLong saldoAtomico;
	private Semaphore semaphore;
	private Lock lock;
	private Mode mode;

	public enum Mode {
		Lock, Semaphore, Synchronized, AtomicInteger
	}

	public Cuenta(Mode modo) {
		this.mode = modo;
		this.saldo = 0L;
		switch (modo) {
			case Semaphore:
				this.semaphore = new Semaphore(1);
				break;
			case AtomicInteger:
				saldoAtomico = new AtomicLong(0L);
				break;
			case Lock:
				this.lock = new ReentrantLock();
				break;
			default:
				break;
		}
	}


	public void addNumberSemaphore(Long add) {
		try {
			this.semaphore.acquire();
			this.saldo += add;
			this.semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void addNumberLock(Long add) {
		this.lock.lock();
		this.saldo += add;
		this.lock.unlock();
	}

	public synchronized void addNumberSynchronized(Long add) {
		this.saldo += add;
	}

	public synchronized void addNumberAtomic(Long add) {
		this.saldoAtomico.addAndGet(add);
	}


	public Mode getMode() {
		return mode;
	}

}
