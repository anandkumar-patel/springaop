package anand.learn.springaop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {

	@Before("execution(* anand.learn.springaop.service.ExampleService.simpleMethod(..))")
	public void beforeExecution(JoinPoint jp) {
		System.out.println("Before method: " + jp.getSignature().getName()
				+ ". Class: " + jp.getTarget().getClass().getSimpleName());
	}

	@AfterReturning(pointcut = "execution(* anand.learn.springaop.service.ExampleService.methodReturnsValue(..))", returning = "result")
	public void afterReturningExecution(JoinPoint jp, Object result) {
		System.out.println("After returning method: "
				+ jp.getSignature().getName() + ". Class: "
				+ jp.getTarget().getClass().getSimpleName());
		System.out.println("Result returned: " + result);
	}

	@AfterThrowing(pointcut = "execution(* anand.learn.springaop.service.ExampleService.methodThrowsException(..))", throwing = "ex")
	public void afterThrowingExecution(JoinPoint jp, Exception ex) {
		System.out.println("After throwing method: "
				+ jp.getSignature().getName() + ". Class: "
				+ jp.getTarget().getClass().getSimpleName());
		System.out.println("Exception: " + ex.getMessage());
	}

	@After("execution(* anand.learn.springaop.service.ExampleService.simpleMethod(..))")
	public void afterExecution(JoinPoint jp) {
		System.out.println("After method: " + jp.getSignature().getName()
				+ ". Class: " + jp.getTarget().getClass().getSimpleName());
	}

	@Around("execution(* anand.learn.springaop.service.ExampleService.testAround*(..))")
	public Object aroundExecution(ProceedingJoinPoint jp) throws Exception {

		System.out.println("Before method: " + jp.getSignature().getName()
				+ ". Class: " + jp.getTarget().getClass().getSimpleName());

		try {
			// Proceed with method invocation
			Object result = jp.proceed();

			System.out.println("Returning: " + result);
			return result;
		} catch (Throwable e) {
			// Log error
			System.out.println("Error: " + e.getMessage());
			// Throw exception to the caller
			throw new Exception("Error", e);
		}
	}
}
