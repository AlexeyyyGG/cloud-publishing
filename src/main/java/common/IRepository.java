package common;

public interface IRepository<T,ID> {
    void add(T t);
    void update(T t);
    void delete(ID id);
    T get(ID id);
    boolean exists(ID id);
}
