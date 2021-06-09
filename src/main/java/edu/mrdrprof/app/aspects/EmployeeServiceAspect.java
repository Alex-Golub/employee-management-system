package edu.mrdrprof.app.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Cross cutting concerns implemented using AOP technique
 * Four examples for targeting a join-point (i.e. method execution):
 * 1. Method execution
 * 2. Annotated with custom annotation
 * 3. By method signature
 * 4. By bean name (first time take longer because bean is processed(definition, inti, DI)
 * <p>
 * JoinPoint - method execution
 * PointCut - specify where to insert code to alter method execution i.e. match the join-point
 * Advice - Altering code that match specified PointCuts
 *
 * @author Alex Golub
 * @since 09-Jun-21, 12:35 PM
 */
@Component
@Aspect
public class EmployeeServiceAspect {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  @Pointcut("execution(* edu.mrdrprof.app.service.impl.EmployeeServiceImpl.createEmployee(..))")
  public void createEmployeePointCut() {
  }

  @Pointcut("@annotation(edu.mrdrprof.app.aspects.annotations.PerformanceLogger)")
  public void updateEmployeePointCut() {
  }

  @Pointcut("args(int, int)")
  public void integerIntegerPointCut() {
  }

  @Pointcut("bean(employeeServiceImpl)")
  public void employeeServiceImplPointCut() {
  }

  @Around("createEmployeePointCut()")
  public Object aroundCreateEmployeeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    try {
      return proceedingJoinPoint.proceed();
    } finally {
      long finishTime = System.currentTimeMillis();

      logger.info(String.format(">> Duration of %s execution was %s millis",
              "createEmployee(EmployeeDto)", finishTime - startTime));
    }
  }

  @Around("updateEmployeePointCut()")
  public Object aroundUpdateEmployeeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    try {
      return proceedingJoinPoint.proceed();
    } finally {
      long finishTime = System.currentTimeMillis();

      logger.info(String.format(">> Duration of %s execution was %s millis",
              "updateEmployee(String, EmployeeDto)", finishTime - startTime));
    }
  }

  @Around("integerIntegerPointCut()")
  public Object aroundGetEmployeesAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    try {
      return proceedingJoinPoint.proceed();
    } finally {
      long finishTime = System.currentTimeMillis();

      logger.info(String.format(">> Duration of %s execution was %s millis",
              "getEmployees(int, int)", finishTime - startTime));
    }
  }

  @Around("employeeServiceImplPointCut()")
  public Object aroundEmployeeServiceImplAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    try {
      return proceedingJoinPoint.proceed();
    } finally {
      long finishTime = System.currentTimeMillis();

      logger.info(String.format(">> Duration of %s execution was %s millis",
              "retrieving employeeServiceImpl bean from application-context", finishTime - startTime));
    }
  }
}
