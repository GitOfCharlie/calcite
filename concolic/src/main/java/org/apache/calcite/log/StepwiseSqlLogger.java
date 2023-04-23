/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.calcite.log;

import java.util.Collections;

public class StepwiseSqlLogger {
  public static final boolean ENABLE_SQL_LOG = true;

  private static int indent = 0;
  private static final int INDENT_LEN = 2;

  public static void incIndent() {
    indent += INDENT_LEN;
  }

  public static void decIndent() {
    if (indent > 0) {
      indent -= INDENT_LEN;
    }
  }

  public static void log(Object o) {
    if (ENABLE_SQL_LOG) {
      trace();
      System.out.println(indent("Object Type: " + o.getClass()));
      System.out.println(indent(o.toString()));
      System.out.println();
    }
  }

  private static void trace() {
    final StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
    // The 3rd element is exactly the method which invokes StepwiseSqlLogger.log()
    final StackTraceElement e = stacktrace[3];
    final String className = e.getClassName();
    final String methodName = e.getMethodName();
    final String fileName = e.getFileName();
    final int lineNumber = e.getLineNumber();
    System.out.println(
        indent(
            "Trace: " + className + "." + methodName + " (" + fileName + ":" + lineNumber + ")"));
  }

  private static String indent(String s) {
    final String headIndent = String.join("", Collections.nCopies(indent, "-"));
    final String indentStr = String.join("", Collections.nCopies(indent, " "));
    s = headIndent + s.replaceAll(System.lineSeparator(), System.lineSeparator() + indentStr);
    return s;
  }

}
