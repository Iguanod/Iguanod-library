/*
 * -------------------- DO NOT REMOVE OR MODIFY THIS HEADER --------------------
 * 
 * Copyright (C) 2014 The Iguanod Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License should accompany this file or the folder or folder
 * hierarchy where this file is located, or should have been provided by whoever
 * or wherever you obtained this file. If that is not the case you may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied;
 * and no Contributor shall be liable for damages, including any direct,
 * indirect, special, incidental, or consequential damages of any character
 * arising as a result of this License or out of the use or inability to use
 * the Work (including but not limited to damages for loss of goodwill, work
 * stoppage, computer failure or malfunction, or any and all other commercial
 * damages or losses), even if such Contributor has been advised of the
 * possibility of such damages.
 * 
 * See the License for further details and the specific language governing
 * permissions and limitations under the License.
 */
package es.iguanod.base;

/**
 * Thrown to indicate that a method has attempted to parse a string that does
 * not have the appropriate format.
 *
 * @author <a href="mailto:rubiof.david@gmail.com">David Rubio Fernández</a>
 * @since 0.0.4.1.a
 * @version 1.0.1.1.b
 */
public class StringFormatException extends IllegalArgumentException{

	private static final long serialVersionUID=795100234989642360L;

	/**
	 * Constructs a new exception with {@code null} as its detail message. The
	 * cause is not initialized, and may subsequently be initialized by a call
	 * to {@link java.lang.Throwable#initCause(java.lang.Throwable)
	 * Throwable.initCause(Throwable)}.
	 */
	public StringFormatException(){
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
	public StringFormatException(String message){
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * <p>Note that the detail message associated with {@code cause} is
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
	public StringFormatException(String message, Throwable cause){
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
	public StringFormatException(Throwable cause){
		super(cause);
	}
}
