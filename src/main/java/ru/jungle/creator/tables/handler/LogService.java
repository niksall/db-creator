package ru.jungle.creator.tables.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.jungle.creator.tables.dto.request.TableRequest;

import java.util.*;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogService {

    @Pointcut("execution(public * ru.jungle.creator.tables.service.TableService.create(*))")
    private void logTableService() {
    }

    @Around("logTableService() && target(service)")
    public Object addLogForCreate(ProceedingJoinPoint joinPoint, Object service) throws Throwable {
        try {
            Object resultMethod = joinPoint.proceed();
            Arrays.stream(joinPoint.getArgs())
                    .map(a -> (List<TableRequest>) a)
                    .forEach(t -> {
                        t.forEach(r -> log.info("Success! Table was created - %s".formatted(r.getTableName())));
                    });
            return resultMethod;
        } catch (Throwable ex) {
            log.error("Error! Table wasn't created %s, exception: %s".formatted(((TableRequest) joinPoint.getArgs()[0]).getTableName(), ex.getMessage()));
            throw ex;
        }
    }
}
