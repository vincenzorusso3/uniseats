package it.uniseats.utils;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class DailyRoutine extends TimerTask {

  public static boolean runned = false;
  public static boolean runnedJ = false;

  private final static long ONCE_PER_DAY = 1000 * 60 * 60 * 24;


  @Override
  public void run() {

    if (new Date().compareTo(getToday7Am()) == 0) {
      if (!runnedJ) {

        //MODULO
        try {
          Adapter.todaySchedule();
        } catch (SQLException | CloneNotSupportedException | ParseException throwables) {
          throwables.printStackTrace();
        }

        runned = false;
        runnedJ = true;
        startTask();
      }
    } else {
      runnedJ = false;
    }

  }

  private Date getToday7Am() {
    Calendar c = Calendar.getInstance(Locale.ITALY);

    c.set(Calendar.HOUR_OF_DAY, 7);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);


    return c.getTime();
  }

  private static Date getTomorrowMorning7Am() {

    Calendar c = Calendar.getInstance(Locale.ITALY);

    c.set(Calendar.HOUR_OF_DAY, 7);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);


    return c.getTime();
  }

  public static void startTask() {

    if (!runned) {

      runned = true;

      DailyRoutine task = new DailyRoutine();
      Timer timer = new Timer();

      timer.schedule(task, getTomorrowMorning7Am(),
          ONCE_PER_DAY);

    }

  }

}
