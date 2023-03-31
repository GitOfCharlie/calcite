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

public class StepwiseSqlLogger {

  public static final boolean ENABLE_SQL_LOG = true;

  public static void log(Object o) {
    if (ENABLE_SQL_LOG) {
      trace();
      System.out.println(o);
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
        "Trace: " + className + "." + methodName + " (" + fileName + ":" + lineNumber + ")");
  }

}
