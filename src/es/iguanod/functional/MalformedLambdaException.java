/*
 * -------------------- DO NOT REMOVE OR MODIFY THIS HEADER --------------------
 * 
 * Copyright (C) 2014 The Iguanod Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License should have been provided along with this file, usually
 * under the name "LICENSE.txt". If that is not the case you may obtain a
 * copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.iguanod.functional;

/**
 * Thrown to indicate that a {@link es.iguanod.functional.Lambda Lambda} has
 * been called for {@link es.iguanod.functional.Lambda#eval evaluation}, but the
 * Lambda was created incorrectly.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 1.0.1
 * @version 1.0.1
 */
public class MalformedLambdaException extends Exception{

	private static final long serialVersionUID=3359729773716169466L;

	/**
	 * Constructs a new exception with {@code null} as its detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call
	 * to {@link java.lang.Throwable#initCause(java.lang.Throwable)
	 * Throwable.initCause(Throwable)}.
	 */
	public MalformedLambdaException(){
		super();
	}

	/**
	 * Constructs a new exception with {@code null} as its detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call
	 * to {@link java.lang.Throwable#initCause(java.lang.Throwable)
	 * Throwable.initCause(Throwable)}.
	 *
	 * @param message the detail message. The detail message is saved for
	 * later retrieval by {@link java.lang.Throwable#getMessage()
	 * Throwable.getMessage()} method.
	 */
	public MalformedLambdaException(String message){
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>
	 * Note that the detail message associated with {@code cause} is
	 * <i>not</i> automatically incorporated in this runtime exception's
	 * detail message.</p>
	 *
	 * @param message the detail message. The detail message is saved for
	 * later retrieval by {@link java.lang.Throwable#getMessage()
	 * Throwable.getMessage()} method
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link java.lang.Throwable#getCause() Throwable.getCause()} method). A
	 * null value is permitted, and indicates that the cause is nonexistent or
	 * unknown.
	 */
	public MalformedLambdaException(String message, Throwable cause){
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail
	 * message of {@code (cause==null ? null : cause.toString())} (which
	 * typically contains the class and detail message of cause). This
	 * constructor is useful for runtime exceptions that are little more than
	 * wrappers for other throwables.
	 *
	 * @param cause the cause (which is saved for later retrieval by the
	 * {@link java.lang.Throwable#getCause() Throwable.getCause()} method). A
	 * null value is permitted, and indicates that the cause is nonexistent or
	 * unknown.
	 */
	public MalformedLambdaException(Throwable cause){
		super(cause);
	}
}
