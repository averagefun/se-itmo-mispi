import beans.Result;
import database.ResultDao;
import org.junit.Before;
import org.junit.Test;
import util.ResultDaoImplemntation;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PersistenceTest {
    static ResultDao resultDao;

    @Before
    public void initDAO() {
        resultDao = new ResultDaoImplemntation();
    }

    @Test
    public void databaseCheck() {
        Random random = ThreadLocalRandom.current();
        Result result = new Result();
        result.setX(random.nextDouble());
        result.setY(random.nextDouble());
        result.setR(random.nextDouble());
        result.setHit(random.nextBoolean());

        // save result to database
        resultDao.save(result);

        // get results from database
        List<Result> results = resultDao.getAll();

        assertTrue(results.contains(result));
    }
}