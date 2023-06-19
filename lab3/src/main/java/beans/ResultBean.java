package beans;

import database.ResultDao;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Named("results")
@ApplicationScoped
public class ResultBean implements Serializable {
    private static final int[] X_VALUES = {-5, -4, -3, -2, -1, 0, 1, 2, 3};
    private static final double MIN_Y = -5;
    private static final double MAX_Y = 5;

    @Inject
    private ResultDao resultDao;

    private Result currResult;
    private List<Result> resultList;

    @PostConstruct
    private void initialize() {
        currResult = new Result();
        updateLocal();
    }

    private void updateLocal() {
        resultList = resultDao.getAll();
    }

    public void addResult() {
        Result copyResult = new Result(currResult);
        resultDao.save(copyResult);
        updateLocal();
    }

    public void clearResults() {
        resultDao.clear();
        resultList = resultDao.getAll();
        updateLocal();
    }
}
