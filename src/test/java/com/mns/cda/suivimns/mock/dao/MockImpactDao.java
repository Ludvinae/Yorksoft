package com.mns.cda.suivimns.mock.dao;

import com.mns.cda.suivimns.dao.ImpactDao;
import com.mns.cda.suivimns.model.Impact;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockImpactDao implements ImpactDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Impact> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Impact> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Impact> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Impact getOne(Integer integer) {
        return null;
    }

    @Override
    public Impact getById(Integer integer) {
        return null;
    }

    @Override
    public Impact getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Impact> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Impact> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Impact> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Impact> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Impact> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Impact> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Impact, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Impact> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Impact> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Impact> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Impact> findAll() {
        return List.of();
    }

    @Override
    public List<Impact> findAllById(Iterable<Integer> integers) {
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
    public void delete(Impact entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Impact> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Impact> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Impact> findAll(Pageable pageable) {
        return null;
    }
}
