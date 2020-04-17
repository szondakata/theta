/*
 * Copyright 2019 Budapest University of Technology and Economics
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hu.bme.mit.theta.xcfa.alt.expl;

import hu.bme.mit.theta.xcfa.XCFA;
import hu.bme.mit.theta.xcfa.dsl.XcfaDslManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ExplStateResultTest {

    @Parameter()
    public String filepath;

    @Parameter(1)
    public Boolean shouldWork;

    @Parameters()
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[]{"/functions-global-local.xcfa", true},
                new Object[]{"/fibonacci.xcfa", true},
                new Object[]{"/havoc-test.xcfa", true},
                new Object[]{"/mutex-test.xcfa", true},
                new Object[]{"/mutex-test2.xcfa", false},
                new Object[]{"/mutex-test3.xcfa", false},
                new Object[]{"/simple-test.xcfa", true},
                new Object[]{"/gcd.xcfa", true}
        );
    }

    @Test
    public void test() throws IOException {
        final InputStream inputStream = getClass().getResourceAsStream(filepath);
        System.out.println("Testing " + filepath);
        XCFA xcfa = XcfaDslManager.createXcfa(inputStream);
        MutableExplState s = MutableExplState.initialState(xcfa);
        List<ExplState> states = new ArrayList<>();
        List<ExecutableTransitionForMutableExplState> transitions = new ArrayList<>();
        states.add(ImmutableExplState.copyOf(s));
        while (!s.getSafety().isFinished()) {
            var nextTransition = s.getEnabledTransitions().iterator().next();
            transitions.add(nextTransition);
            nextTransition.execute();
            states.add(ImmutableExplState.copyOf(s));
        }
        if (!s.getSafety().isSafe() && shouldWork) {
            System.err.println("Trace:");
            for (int i = 0; i < transitions.size(); i++) {
                System.err.println(states.get(i));
                System.err.println(transitions.get(i));
            }
            System.err.println(states.get(transitions.size()));
            throw new RuntimeException("Caught program error: " + s.getSafety());
        } else if (!s.getSafety().isSafe() && !shouldWork){
            System.err.println("[OK] Unsafe: " + s.getSafety());
        } else if (s.getSafety().isSafe() && !shouldWork){
            throw new RuntimeException("Safe, but it should not be!");
        }
    }
}
