package com.mns.cda.suivimns.mock;

import com.mns.cda.suivimns.dao.StatusDao;
import com.mns.cda.suivimns.model.Status;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockStatusDao implements StatusDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Status> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Status> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Status> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Status getOne(Integer integer) {
        return null;
    }

    @Override
    public Status getById(Integer integer) {
        return null;
    }

    @Override
    public Status getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Status> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Status> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Status> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Status> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Status> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Status> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Status, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Status> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Status> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Status> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Status> findAll() {
        return List.of();
    }

    @Override
    public List<Status> findAllById(Iterable<Integer> integers) {
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
    public void delete(Status entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Status> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Status> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Status> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Status> findByDesignation(String designation) {
        return null;
    }
}
