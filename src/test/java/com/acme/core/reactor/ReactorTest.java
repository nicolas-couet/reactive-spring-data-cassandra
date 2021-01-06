package com.acme.core.reactor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReactorTest {
    private static Logger logger = LoggerFactory.getLogger(ReactorTest.class);

    @Test
    public void test(){
        Scheduler scheduler = Schedulers.newParallel("thread", 5);
        Flux<ADTO> adtoFlux = entities()
                .collectList().subscribeOn(scheduler).flatMap(entities -> Mono.zip(
                        a().collectList().subscribeOn(scheduler),
                        b().collectList().subscribeOn(scheduler),
                        c().collectList().subscribeOn(scheduler))
                .map(tuple -> {
                    List<String> strings = tuple.getT1();
                    List<Integer> integers = tuple.getT3();
                    strings.addAll(tuple.getT2());
                    logger.info("prepare dto");
                    return entities.stream().map(x -> new ADTO(x, strings, integers)).collect(Collectors.toList());
                })).flatMapMany(Flux::fromIterable);
        adtoFlux.parallel().runOn(scheduler).subscribe(x -> logger.info(x.toString()));
    }

    public Flux<A> entities(){
        return Flux.fromIterable(Arrays.asList(new A("A"), new A("B"), new A("C")));
    }

    public Flux<String> a(){
        logger.info("a()");
        return Flux.fromIterable(Arrays.asList("w", "x"));
    }
    public Flux<String> b(){
        logger.info("b()");
        return Flux.fromIterable(Arrays.asList("y", "z"));
    }
    public Flux<Integer> c(){
        logger.info("c()");
        return Flux.fromIterable(Arrays.asList(1, 2));
    }

}

class A{
    private String value;

    public A(String value){
        this.value=value;
    }

    public String toString(){
        return "AEntity-"+value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

class ADTO{
    private A entity;
    private List<String> strings;
    private List<Integer> integers;
    public ADTO(A entity, List<String> strings, List<Integer> integers){
        this.entity=entity;
        this.integers=integers;
        this.strings = strings;
    }

    public A getEntity() {
        return entity;
    }

    public void setEntity(A entity) {
        this.entity = entity;
    }

    public List<String> getStrings() {
        return strings;
    }

    public void setStrings(List<String> strings) {
        this.strings = strings;
    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(List<Integer> integers) {
        this.integers = integers;
    }

    public String toString(){
        return "ADTO-"+entity.toString()+"#"+strings+"#"+integers;
    }
}