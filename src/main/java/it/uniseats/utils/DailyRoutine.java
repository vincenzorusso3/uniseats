package it.uniseats.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class DailyRoutine extends TimerTask {

  public static boolean runned = false;
  public static boolean runnedJ = false;

  private final static long ONCE_PER_DAY = 1000 * 60 * 60 * 24;


  @Override
  public void run() {

    if (new Date().compareTo(getToday7AM()) == 0) {
      if (!runnedJ) {

        //MODULO

        runned = false;
        runnedJ = true;
        startTask();
      }
    } else {
      runnedJ = false;
    }

  }

  private Date getToday7AM() {
    Calendar c = Calendar.getInstance(Locale.ITALY);

    c.set(Calendar.HOUR_OF_DAY, 7);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);


    return c.getTime();
  }

  private static Date getTomorrowMorning7AM() {

    Calendar c = Calendar.getInstance(Locale.ITALY);

    c.set(Calendar.HOUR_OF_DAY, 7);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);


    return c.getTime();
  }

  public static void startTask() {

    System.out.println("AVVIO IL TASK");

    if (!runned) {

      runned = true;

      DailyRoutine task = new DailyRoutine();
      Timer timer = new Timer();

      timer.schedule(task, getTomorrowMorning7AM(),
          ONCE_PER_DAY);

    }

  }

}
