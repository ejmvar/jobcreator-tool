/*
 * Copyright (c) 2011 Henrik Lynggaard Hansen 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Henrik Lynggaard Hansen
 */
package dk.hlyh.hudson.tools.jobcreator.input.xml;

import dk.hlyh.hudson.tools.jobcreator.Arguments;
import dk.hlyh.hudson.tools.jobcreator.model.Environment;
import dk.hlyh.hudson.tools.jobcreator.model.Merge;
import dk.hlyh.hudson.tools.jobcreator.model.Pipeline;
import dk.hlyh.hudson.tools.jobcreator.model.Propagation;
import dk.hlyh.hudson.tools.jobcreator.model.Property;
import java.io.File;
import java.net.URL;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EnvironmentInheritenceTest {

  @Test
  public void loadP1First() {
    URL resource = this.getClass().getResource("/xmlloadertest/inheritence.xml");
    Arguments args = new Arguments();
    args.setInput(new File(resource.getFile()));
    args.setEnvironment("p1-first");
    Loader handler = new Loader(args);
    
    Pipeline pipeline = handler.loadPipeline();
    Assert.assertNotNull(pipeline);
    Environment env = pipeline.getEnvironment();
    
    Assert.assertEquals(env.getOutputPattern(), "${pipeline}_${job}");    
    Assert.assertEquals(env.getPropertySet("global").getProperty("prop_1").getValue(),"valueA");
    Assert.assertEquals(env.getPropertySet("global").getProperty("prop_2"),null);
    Property prop3 = env.getPropertySet("global").getProperty("prop_3");
    Assert.assertNotNull(prop3);
    Assert.assertEquals(prop3.getValue(),"valueB");
    Assert.assertEquals(prop3.getPropagation(), Propagation.Upstream);
    Assert.assertEquals(prop3.getMerging(), Merge.Append);
  }
  
  @Test
  public void loadP2First() {
    URL resource = this.getClass().getResource("/xmlloadertest/inheritence.xml");
    Arguments args = new Arguments();
    args.setInput(new File(resource.getFile()));
    args.setEnvironment("p2-first");
    Loader handler = new Loader(args);
    Pipeline pipeline = handler.loadPipeline();
    Assert.assertNotNull(pipeline);
    Environment env = pipeline.getEnvironment();
    
    Assert.assertEquals(env.getOutputPattern(), "${pipeline}_${environment}_${job}");    
    Assert.assertEquals(env.getPropertySet("global").getProperty("prop_1").getValue(),"value1");
    Assert.assertEquals(env.getPropertySet("global").getProperty("prop_2").getValue(),"value2");
    Property prop3 = env.getPropertySet("global").getProperty("prop_3");
    Assert.assertNotNull(prop3);
    Assert.assertEquals(prop3.getValue(),"value3");
    Assert.assertEquals(prop3.getPropagation(), Propagation.Downstream);
    Assert.assertEquals(prop3.getMerging(), Merge.Prefix);
  }
  
  @Test
  public void loadLocalFirst() {
    URL resource = this.getClass().getResource("/xmlloadertest/inheritence.xml");
    Arguments args = new Arguments();
    args.setInput(new File(resource.getFile()));
    args.setEnvironment("local-first");
    Loader handler = new Loader(args);
    Pipeline pipeline = handler.loadPipeline();
    Assert.assertNotNull(pipeline);
    Assert.assertEquals(pipeline.getEnvironment().getOutputPattern(), "${pipeline}");
  }   
  
}
