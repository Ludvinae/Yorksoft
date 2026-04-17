package com.mns.cda.suivimns.mock.dao;

import com.mns.cda.suivimns.dao.KnowledgeDao;
import com.mns.cda.suivimns.model.Knowledge;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class MockKnowledgeDao implements KnowledgeDao {
    @Override
    public void flush() {

    }

    @Override
    public <S extends Knowledge> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Knowledge> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Knowledge> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Knowledge getOne(Integer integer) {
        return null;
    }

    @Override
    public Knowledge getById(Integer integer) {
        return null;
    }

    @Override
    public Knowledge getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Knowledge> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Knowledge> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Knowledge> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Knowledge> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Knowledge> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Knowledge> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Knowledge, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Knowledge> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Knowledge> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Knowledge> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Knowledge> findAll() {
        return List.of();
    }

    @Override
    public List<Knowledge> findAllById(Iterable<Integer> integers) {
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
    public void delete(Knowledge entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Knowledge> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Knowledge> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Knowledge> findAll(Pageable pageable) {
        return null;
    }
}
