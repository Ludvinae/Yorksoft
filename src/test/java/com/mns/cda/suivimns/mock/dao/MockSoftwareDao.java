package com.mns.cda.suivimns.mock.dao;

import com.mns.cda.suivimns.dao.SoftwareDao;
import com.mns.cda.suivimns.model.Software;
import com.mns.cda.suivimns.model.SoftwareType;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockSoftwareDao implements SoftwareDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Software> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Software> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Software> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Software getOne(Integer integer) {
        return null;
    }

    @Override
    public Software getById(Integer integer) {
        return null;
    }

    @Override
    public Software getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Software> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Software> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Software> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Software> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Software> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Software> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Software, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Software> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Software> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Software> findById(Integer id) {
        if (id == 1) return Optional.of(
                new Software(1, "TestSoft", "this is exclusively  for testing purpose",
                        new SoftwareType(11, "mock type"),null));

        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Software> findAll() {
        return List.of();
    }

    @Override
    public List<Software> findAllById(Iterable<Integer> integers) {
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
    public void delete(Software entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Software> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Software> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Software> findAll(Pageable pageable) {
        return null;
    }
}
