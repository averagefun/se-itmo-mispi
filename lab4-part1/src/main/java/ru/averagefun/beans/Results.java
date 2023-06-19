package ru.averagefun.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import ru.averagefun.database.ResultDao;
import ru.averagefun.entity.Result;
import ru.averagefun.util.MBeanRegistryUtil;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Named("results")
@ApplicationScoped
public class Results extends NotificationBroadcasterSupport implements Serializable, ResultsMBean {
    private static final int[] X_VALUES = {-5, -4, -3, -2, -1, 0, 1, 2, 3};
    private static final double MIN_Y = -5;
    private static final double MAX_Y = 5;
    private int sequenceNumber = 0;

    @Inject
    private ResultDao resultDao;

    private Result currResult;
    private List<Result> resultList;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object unused) {
        currResult = new Result();
        updateLocal();
        MBeanRegistryUtil.registerBean(this, "results");
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object unused) {
        MBeanRegistryUtil.unregisterBean(this);
    }

    private void updateLocal() {
        resultList = resultDao.getAll();
    }

    public void addResult() {
        Result copyResult = new Result(currResult);
        resultDao.save(copyResult);
        updateLocal();

        if (!resultList.get(resultList.size() - 2).getHit() && !resultList.get(resultList.size() - 1).getHit()) {
            Notification notification = new Notification(
                    "Two misses in a row!",
                    getClass().getSimpleName(),
                    sequenceNumber++,
                    "The user missed two times in a row!"
            );
            sendNotification(notification);
        }
    }

    public void clearResults() {
        resultDao.clear();
        resultList = resultDao.getAll();
        updateLocal();
    }

    @Override
    public long getTotalDots() {
        return resultList.size();
    }

    @Override
    public long getHitDots() {
        return resultList.stream().filter(Result::getHit).count();
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE };
        String name = AttributeChangeNotification.class.getName();
        String description = "Miss notification";
        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }
}
