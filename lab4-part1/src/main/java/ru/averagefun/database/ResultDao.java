package ru.averagefun.database;

import ru.averagefun.entity.Result;

import java.util.List;

public interface ResultDao {
    void save(Result result);

    void clear();

    List<Result> getAll();
}
