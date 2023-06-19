package ru.averagefun.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Named;
import ru.averagefun.util.MBeanRegistryUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named("timeKeeper")
@ApplicationScoped
public class TimeKeeper implements Serializable {
    private String time;

    private String formatDate(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
    }

    public TimeKeeper() {
        this.time = formatDate(new Date());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void updateTime() {
        time = formatDate(new Date());
    }
}
