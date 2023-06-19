package ru.averagefun.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Named;
import ru.averagefun.util.MBeanRegistryUtil;

import java.util.ArrayList;
import java.util.List;

@Named("graphClicks")
@ApplicationScoped
public class GraphClicks implements GraphClicksMBean {
    private final List<Long> clicksTime = new ArrayList<>();
    private long averageIntervals = 0;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.registerBean(this, "graphClicks");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }

    private void computeAverageIntervals() {
        long amount = 0;
        for (int i = 0; i < clicksTime.size() - 1; i++) {
            amount += clicksTime.get(i+1) - clicksTime.get(i);
        }
        averageIntervals = amount / (clicksTime.size() - 1);
    }

    @Override
    public long getAverageIntervals() {
        return averageIntervals;
    }

    @Override
    public void click() {
        clicksTime.add(System.currentTimeMillis());
        computeAverageIntervals();
    }
}
