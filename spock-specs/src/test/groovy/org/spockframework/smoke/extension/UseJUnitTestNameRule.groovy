/*
 * Copyright 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */  

package org.spockframework.smoke.extension

import spock.lang.*
import org.junit.Rule
import org.junit.rules.TestName

class UseJUnitTestNameRule extends Specification {
  @Rule
  TestName name = new TestName()

  def "a method name"() {
    expect:
    name.methodName == "a method name"
  }

  def "some other method name"() {
    expect:
    name.methodName == "some other method name"  
  }
}
