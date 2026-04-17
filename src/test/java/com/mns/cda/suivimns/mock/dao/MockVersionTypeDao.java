package com.mns.cda.suivimns.mock.dao;

import com.mns.cda.suivimns.dao.VersionTypeDao;
import com.mns.cda.suivimns.model.VersionType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockVersionTypeDao implements VersionTypeDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends VersionType> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends VersionType> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<VersionType> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public VersionType getOne(Integer integer) {
        return null;
    }

    @Override
    public VersionType getById(Integer integer) {
        return null;
    }

    @Override
    public VersionType getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends VersionType> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends VersionType> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends VersionType> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends VersionType> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends VersionType> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends VersionType> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends VersionType, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends VersionType> S save(S entity) {
        return null;
    }

    @Override
    public <S extends VersionType> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<VersionType> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<VersionType> findAll() {
        return List.of();
    }

    @Override
    public List<VersionType> findAllById(Iterable<Integer> integers) {
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
    public void delete(VersionType entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends VersionType> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<VersionType> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<VersionType> findAll(Pageable pageable) {
        return null;
    }
}
