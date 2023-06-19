package util;

import beans.Result;
import database.ResultDao;
import jakarta.enterprise.inject.Default;

import java.util.ArrayList;
import java.util.List;

@Default
public class ResultDaoImplemntation implements ResultDao {
    List<Result> results = new ArrayList<>();

    @Override
    public void save(Result result) {
        results.add(result);
    }

    @Override
    public void clear() {

    }

    @Override
    public List<Result> getAll() {
        return results;
    }
}
