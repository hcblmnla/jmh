package benchmark;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 1, warmups = 1)
@Warmup(iterations = 1, time = 5)
@Measurement(iterations = 1, time = 5)
public class BenchmarkTest {

    private Student student;
    private Method method;
    private MethodHandle mh;
    private InformationInjector injector;

    @Setup
    public void setup() throws Throwable {
        student = new Student(409549, "Daniil", "Serov", Group.M3234);

        method = Student.class.getDeclaredMethod("getInformation");
        method.setAccessible(true);

        var publicLookup = MethodHandles.publicLookup();
        var methodType = MethodType.methodType(String.class);
        mh = publicLookup.findVirtual(Student.class, "getInformation", methodType);

        var lookup = MethodHandles.lookup();
        var injectorType = MethodType.methodType(InformationInjector.class);
        var methodSignature = MethodType.methodType(String.class, Student.class);

        var callSite = LambdaMetafactory.metafactory(
            lookup, "inject", injectorType, methodSignature, mh, methodSignature
        );
        injector = (InformationInjector) callSite.getTarget().invokeExact();
    }

    @Benchmark
    public void directAccess(final Blackhole bh) {
        final String info = student.getInformation();
        bh.consume(info);
    }

    @Benchmark
    public void reflection(final Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        final String info = (String) method.invoke(student);
        bh.consume(info);
    }

    @Benchmark
    public void methodHandle(final Blackhole bh) throws Throwable {
        final String info = (String) mh.invokeExact(student);
        bh.consume(info);
    }

    @Benchmark
    public void lambdaMeta(final Blackhole bh) {
        final String info = injector.inject(student);
        bh.consume(info);
    }
}
