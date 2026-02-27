package repository;

import java.sql.SQLException;

public interface IRepository<T,ID> {
    T add(T t);
    void update(T t) throws SQLException;
    void delete(ID id);
    T get(ID id);
    boolean exists(ID id);
}