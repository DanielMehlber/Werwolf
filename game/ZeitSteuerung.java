package game;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import javafx.concurrent.Task;

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
	}
	
	public void start() {
		timer.schedule(task, sekunden * 1000);
	}
	
	public void detonieren() {
		try {
			action.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		task.cancel();
		timer.cancel();
	}
	
	public void entschaerfen() {
		task.cancel();
		timer.cancel();
	}

}
