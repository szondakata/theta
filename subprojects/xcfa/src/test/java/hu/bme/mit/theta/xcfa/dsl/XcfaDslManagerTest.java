/*
 *  Copyright 2017 Budapest University of Technology and Economics
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package hu.bme.mit.theta.xcfa.dsl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import hu.bme.mit.theta.xcfa.XCFA;
import hu.bme.mit.theta.xcfa.dsl.XcfaDslManager;

@RunWith(Parameterized.class)
public final class XcfaDslManagerTest {

	@Parameter(0)
	public String filepath;

	@Parameter(1)
	public int varCount;

	@Parameter(2)
	public int locCount;

	@Parameter(3)
	public int edgeCount;

	@Parameter(4)
	public int stmtCount;

	@Parameters()
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {

				{ "/locking.xcfa", 3, 9, 10, 10 },

				{ "/counter5_true.xcfa", 1, 6, 6, 6 }

		});
	}

	@Test
	public void test() throws FileNotFoundException, IOException, InterruptedException {
		final InputStream inputStream = getClass().getResourceAsStream(filepath);
		final XCFA xcfa = XcfaDslManager.createXcfa(inputStream);
		Assert.assertEquals(varCount, xcfa.getVars().size());
		Assert.assertEquals(locCount, xcfa.getLocs().size());
		Assert.assertEquals(edgeCount, xcfa.getEdges().size());
		Assert.assertEquals(stmtCount, xcfa.getEdges().stream().map(e -> e.getStmt()).count());
	}

}
