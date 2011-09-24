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
package dk.hlyh.hudson.tools.jobcreator.input.xml.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Environment {

  @XmlID
  @XmlElement(required = true)
  private String name;
  
  @XmlElement
  private String outputPattern;
  
  @XmlElementWrapper(name = "inherit")
  @XmlElement(name = "environment")
  @XmlIDREF
  private List<Environment> parentEnv;
  
  @XmlElementWrapper(name = "include")
  @XmlElement(name = "job")
  @XmlIDREF
  private Set<Job> includedJobs;
  
  @XmlElementWrapper(name = "exclude")
  @XmlElement(name = "job")
  @XmlIDREF
  private Set<Job> excludedJobs;
  
  @XmlElement(name = "properties")
  private Set<PropertySet> properties;
  
  public Environment() {
    super();
  }
  
  public String getName() {
    return name;
  }

  public List<Environment> getParentEnv() {
    if (parentEnv == null) {
      parentEnv = new ArrayList<Environment>();
    }
    return parentEnv;
  }

  public Set<Job> getExcludedJobs() {
    if (excludedJobs == null) {
      excludedJobs = new HashSet<Job>();
    }
    return excludedJobs;
  }

  public Set<Job> getIncludedJobs() {
    if (includedJobs == null) {
      includedJobs = new HashSet<Job>();
    }    
    return includedJobs;
  }

  public Set<PropertySet> getProperties() {
    if (properties == null) {
      properties = new HashSet<PropertySet>();
    }        
    return properties;
  }

  public String getOutputPattern() {
    return outputPattern;
  }

  public void setOutputPattern(String outputPattern) {
    this.outputPattern = outputPattern;
  }
 
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Environment other = (Environment) obj;
    if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 61 * hash + (this.name != null ? this.name.hashCode() : 0);
    return hash;
  }

  @Override
  public String toString() {
    return name;
  }  
}
