package com.mns.cda.suivimns.mock;

import com.mns.cda.suivimns.dao.LicenseDao;
import com.mns.cda.suivimns.model.License;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockLicenseDao implements LicenseDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends License> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends License> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<License> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public License getOne(Integer integer) {
        return null;
    }

    @Override
    public License getById(Integer integer) {
        return null;
    }

    @Override
    public License getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends License> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends License> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends License> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends License> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends License> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends License> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends License, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends License> S save(S entity) {
        return null;
    }

    @Override
    public <S extends License> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<License> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<License> findAll() {
        return List.of();
    }

    @Override
    public List<License> findAllById(Iterable<Integer> integers) {
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
    public void delete(License entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends License> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<License> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<License> findAll(Pageable pageable) {
        return null;
    }
}
