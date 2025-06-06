package hse.kpo.services;

import hse.kpo.interfaces.Sales;
import hse.kpo.interfaces.SalesObserver;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
public class SalesAspect {
    private final SalesObserver salesObserver;

    @Around("@annotation(sales)")
    public Object sales(ProceedingJoinPoint pjp, Sales sales) throws Throwable {

        salesObserver.checkCustomers();

        String operationName = sales.value().isEmpty() ? pjp.getSignature().toShortString() : sales.value();
        try {
            Object result = pjp.proceed();
            salesObserver.checkCustomers();
            return result;
        } catch (Throwable e) {
            throw e;
        }
    }

}