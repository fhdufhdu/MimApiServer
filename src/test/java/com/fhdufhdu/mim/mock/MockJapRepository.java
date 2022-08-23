package com.fhdufhdu.mim.mock;

import com.fhdufhdu.mim.entity.BaseEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class MockJapRepository<Entity extends BaseEntity<Key>, Key> implements JpaRepository<Entity, Key> {
    private List<Entity> dataList;
    private Integer lastIndex;

    public void setData(List<Entity> dataList){
        this.dataList = dataList;
        this.lastIndex = dataList.size();
    }

    public List<Entity> getDataList() {
        return dataList;
    }

    @Override
    public List<Entity> findAll() {
        return null;
    }

    @Override
    public List<Entity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Entity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Entity> findAllById(Iterable<Key> keys) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Key key) {
        for(int i = 0; i < dataList.size(); i++){
            if(dataList.get(i).getId().equals(key))
                dataList.remove(i);
        }
    }

    @Override
    public void delete(Entity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Key> keys) {

    }

    @Override
    public void deleteAll(Iterable<? extends Entity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Entity> S save(S entity) {
        int i;
        for(i = 0; i < dataList.size(); i++){
            if(entity.getId() != dataList.get(i).getId()) continue;
            dataList.set(i, entity);
            break;
        }
        if (i == dataList.size()){
            lastIndex++;
            if(entity.getId() instanceof Long && entity.getId() == null) entity.setId((Key) Long.valueOf(i));
            dataList.add(entity);
        }
        return (S) dataList.get(i);
    }

    @Override
    public <S extends Entity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Entity> findById(Key key) {
        return dataList.stream().filter(d -> d.getId().equals(key)).findFirst();
    }

    @Override
    public boolean existsById(Key key) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Entity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Entity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Entity> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Key> keys) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Entity getOne(Key key) {
        return null;
    }

    @Override
    public Entity getById(Key key) {
        return null;
    }

    @Override
    public Entity getReferenceById(Key key) {
        return null;
    }

    @Override
    public <S extends Entity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Entity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Entity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Entity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Entity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Entity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Entity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
