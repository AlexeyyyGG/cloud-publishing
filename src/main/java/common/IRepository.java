package common;

import java.util.List;

public interface IRepository<T,ID> {
    void add(T t);
    void update(T t);
    void delete(ID id);
    T get(ID id);
    List<T> getAll();
    boolean exists(ID id);
}
