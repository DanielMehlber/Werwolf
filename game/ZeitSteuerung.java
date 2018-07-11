package game;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import javafx.concurrent.Task;

/**Eine Zeitbombe, die wenn die Zeit abgelaufen ist, eine Methode ausführt*/
public class ZeitSteuerung {

	private Callable action;
	private int sekunden;
	private Timer timer;
	private TimerTask task;
	
	public ZeitSteuerung(int sekunden, Callable action) {
		this.sekunden = sekunden;
		this.action = action;
		this.timer = new Timer();
		task = new TimerTask() {
			
			@Override
			public void run() {
				try {
					action.call();
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				
			}
		};
	}
	
	/**
	 * Startet den Countdown
	 * */
	public void start() {
		timer.schedule(task, sekunden * 1000);
	}
	
	/**
	 * Detonieren lassen, obwohl die Zeit noch nicht abgelaufen ist
	 * */
	public void detonieren() {
		try {
			action.call();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		task.cancel();
		timer.cancel();
	}
	
	/**
	 * Den Countdown stoppen
	 * */
	public void entschaerfen() {
		task.cancel();
		timer.cancel();
	}

}
