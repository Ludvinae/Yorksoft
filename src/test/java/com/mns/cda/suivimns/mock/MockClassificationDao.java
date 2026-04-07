package com.mns.cda.suivimns.mock;

import com.mns.cda.suivimns.dao.ClassificationDao;
import com.mns.cda.suivimns.model.Classification;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockClassificationDao implements ClassificationDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Classification> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Classification> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Classification> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Classification getOne(Integer integer) {
        return null;
    }

    @Override
    public Classification getById(Integer integer) {
        return null;
    }

    @Override
    public Classification getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Classification> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Classification> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Classification> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Classification> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Classification> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Classification> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Classification, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Classification> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Classification> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Classification> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Classification> findAll() {
        return List.of();
    }

    @Override
    public List<Classification> findAllById(Iterable<Integer> integers) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Classification entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Classification> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Classification> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Classification> findAll(Pageable pageable) {
        return null;
    }
}
