/*
 * Copyright 2011 the original author or authors.
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

package org.spockframework.smoke

import org.spockframework.EmbeddedSpecification
import org.spockframework.runtime.SpecInfoBuilder

class BlockFilename extends EmbeddedSpecification {
  def "file method call in a block should specify the filename of that block"() {
    def specClass = compiler.compileSpecBody '''
      def m1() {
        expect: "expect"; 'file:./foo/bar.groovy:12'
      }
      def m2() {
        when: "when"
        doIt()
        "file:./foo/bar.groovy:2"

        then: "then"
        "file:./foo/baz.groovy:1"
        check == 'value'
      }
    '''.stripIndent()

    def specInfo = new SpecInfoBuilder(specClass).build()

    expect:
    specInfo.features[0].blocks[0].fileOrigins == ['./foo/bar.groovy:12']

    and:
    specInfo.features[1].blocks[0].fileOrigins == []
    specInfo.features[1].blocks[1].fileOrigins == ['./foo/baz.groovy:1']
  }

  def "when the file method with more than one parameter or wrong parameter is called, it should be ignored"() {
    def specClass = compiler.compileSpecBody '''
      def m1() {
        expect: "expect"; file('/etc/passwd', 'rw+')
      }
      def m2() {
        when: "when"
        then: "then"; file(123)
      }
    '''.stripIndent()

    def specInfo = new SpecInfoBuilder(specClass).build()

    expect:
    specInfo.features[0].blocks[0].fileOrigins == []

    and:
    specInfo.features[1].blocks[0].fileOrigins == []
    specInfo.features[1].blocks[1].fileOrigins == []
  }
}
